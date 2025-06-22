package dbrighthd.wildfiregendermodplugin;

import dbrighthd.wildfiregendermodplugin.listeners.ConnectionListener;
import dbrighthd.wildfiregendermodplugin.listeners.ModPayloadListener;
import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftOutputStream;
import dbrighthd.wildfiregendermodplugin.networking.wildfire.ModSyncPacket;
import dbrighthd.wildfiregendermodplugin.networking.wildfire.ModSyncPacketV2;
import dbrighthd.wildfiregendermodplugin.networking.wildfire.ModSyncPacketV3;
import dbrighthd.wildfiregendermodplugin.networking.wildfire.ModSyncPacketV4;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConfiguration;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The entry-point for this plugin
 *
 * @author dbrighthd
 */
public class GenderModPlugin extends JavaPlugin {
    /**
     * A map to link players (by {@link UUID}) to their {@link ModConfiguration}.
     */
    public final Map<UUID, ModConfiguration> modConfigurations = new HashMap<>();

    /**
     * The current packet format to use with mod users.
     */
    public ModSyncPacket syncPacket;

    @Override
    public void onEnable() {
        getLogger().info("By @dbrighthd, with contributions from @stigstille and @winnpixie");

        saveDefaultConfig();

        int protocolVersion = getConfig().getInt("mod.protocol", -1); // "4" is latest as of 20/Jul/2025
        syncPacket = switch (protocolVersion) {
            // case 1 -> new ModSyncPacketV1();
            case 2 -> new ModSyncPacketV2();
            case 3 -> new ModSyncPacketV3();
            case 4, -1 -> new ModSyncPacketV4();
            default -> null;
        };

        if (syncPacket == null) {
            getLogger().severe("Unknown Protocol Version! Disabling self.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Using Protocol V%d for mod version(s) %s".formatted(syncPacket.getVersion(), syncPacket.getModRange()));

        registerEventListeners();
        registerModListeners();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    private void registerEventListeners() {
        this.getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
    }

    private void registerModListeners() {
        ModPayloadListener payloadListener = new ModPayloadListener(this);

        // Fabric
        this.getServer().getMessenger().registerIncomingPluginChannel(this, ModConstants.SEND_GENDER_INFO, payloadListener);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, ModConstants.SYNC);

        // Forge
        this.getServer().getMessenger().registerIncomingPluginChannel(this, ModConstants.FORGE, payloadListener);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, ModConstants.FORGE);
    }

    public void broadcastModData(Player target) {
        modConfigurations.forEach((uuid, data) -> {
            byte[] fabricData = null;
            byte[] forgeData = null;

            try (ByteArrayOutputStream fabricPayload = new ByteArrayOutputStream();
                 CraftOutputStream fabricStream = new CraftOutputStream(fabricPayload)) {
                syncPacket.write(data, fabricStream);
                fabricData = fabricPayload.toByteArray();
            } catch (IOException ex) {
                getLogger().severe("Error encoding configuration");
                ex.printStackTrace();
            }

            try (ByteArrayOutputStream forgePayload = new ByteArrayOutputStream();
                 CraftOutputStream forgeStream = new CraftOutputStream(forgePayload)) {
                forgeStream.writeByte(1);
                syncPacket.write(data, forgeStream);
                forgeData = forgePayload.toByteArray();
            } catch (IOException ex) {
                getLogger().severe("Error encoding configuration[FORGE]");
                ex.printStackTrace();
            }

            if (target != null) {
                if (fabricData != null) sendModData(target, ModConstants.SYNC, fabricData);
                if (forgeData != null) sendModData(target, ModConstants.FORGE, forgeData);
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (fabricData != null) sendModData(player, ModConstants.SYNC, fabricData);
                    if (forgeData != null) sendModData(player, ModConstants.FORGE, forgeData);
                }
            }
        });
    }

    public void sendModData(Player target, String channel, byte[] data) {
        target.sendPluginMessage(this, channel, data);
    }
}
