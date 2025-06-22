package dbrighthd.wildfiregendermodplugin.listeners;

import dbrighthd.wildfiregendermodplugin.GenderModPlugin;
import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftInputStream;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConfiguration;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConstants;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

/**
 * Handles payload packets from mod users.
 *
 * @author winnpixie
 */
public class ModPayloadListener implements PluginMessageListener {
    private final GenderModPlugin plugin;

    public ModPayloadListener(GenderModPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals(ModConstants.SEND_GENDER_INFO) && !channel.equals(ModConstants.FORGE)) return;

        try (CraftInputStream craftInput = new CraftInputStream(message)) {
            String playerName = player.getName();

            if (channel.equals(ModConstants.FORGE)) {
                try {
                    // Forge version contains an extra byte, consume it.
                    craftInput.readByte();
                } catch (IOException ex) {
                    plugin.getLogger().severe("Could not read FORGE header from %s".formatted(playerName));
                    ex.printStackTrace();

                    // Early return, prevent potentially malformed data storage.
                    return;
                }
            }

            try {
                ModConfiguration data = plugin.syncPacket.read(craftInput);

                UUID playerId = player.getUniqueId();
                if (!playerId.equals(data.getUserId())) {
                    plugin.getLogger().severe("Unauthorized set of configuration by %s(%s) for %s".formatted(playerId,
                            playerName, data.getUserId()));

                    // Early return, unauthorized attempt to set another player's data.
                    return;
                }

                plugin.modConfigurations.put(data.getUserId(), data);

                plugin.getLogger().info("Stored mod configuration for %s(%s) (%s)".formatted(data.getUserId(),
                        playerName, data.getGender().name()));
            } catch (IOException ex) {
                plugin.getLogger().severe("Error storing mod configuration for %s".formatted(playerName));
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Sync mod configurations for ALL online players.
        plugin.broadcastModData(null);
    }
}
