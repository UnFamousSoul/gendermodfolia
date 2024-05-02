package dbrighthd.wildfiregendermodplugin;

import dbrighthd.wildfiregendermodplugin.gender.GenderData;
import dbrighthd.wildfiregendermodplugin.utilities.Constants;
import dbrighthd.wildfiregendermodplugin.utilities.MCProtoBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GenderPlugin extends JavaPlugin implements PluginMessageListener, Listener {
    private final Map<UUID, GenderData> genderDataMap = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("1.20.6 update with contributions by @stigstille and @winnpixie");

        // Handle syncing for new joins, and removing unused data from non-present players.
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getMessenger().registerIncomingPluginChannel(this, Constants.SEND_GENDER_INFO, this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, Constants.SYNC);

        // Forge
        this.getServer().getMessenger().registerIncomingPluginChannel(this, Constants.FORGE, this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, Constants.FORGE);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        String playerName = player.getName();
        getLogger().info("Received message from %s".formatted(playerName));

        // Save player's gender data to memory.
        if (channel.equals(Constants.SEND_GENDER_INFO) || channel.equals(Constants.FORGE)) {
            getLogger().info("Channel verified for %s".formatted(playerName));

            MCProtoBuf buffer = new MCProtoBuf(Unpooled.copiedBuffer(message));
            if (channel.equals(Constants.FORGE)) {
                getLogger().info("%s is using Forge".formatted(playerName));
                buffer.realBuffer().readByte();
            }

            GenderData data = GenderData.decode(buffer);
            data.shouldSync = true;
            genderDataMap.put(data.uuid, data);

            getLogger().info("Stored gender data for %s(%s) (%s)".formatted(data.uuid, playerName, data.gender.name()));
        }

        // Sync NEW/MODIFIED gender data to ALL online players.
        for (GenderData data : genderDataMap.values()) {
            if (!data.shouldSync) continue;

            data.shouldSync = false;
            MCProtoBuf buffer = new MCProtoBuf(Unpooled.buffer());
            MCProtoBuf forgeBuffer = new MCProtoBuf(Unpooled.buffer());
            forgeBuffer.realBuffer().writeByte(1);

            data.encode(buffer);
            data.encode(forgeBuffer);

            getLogger().info("Sending gender data from %s(%s) to ALL".formatted(data.uuid, data.gender.name()));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendPluginMessage(this, Constants.SYNC, buffer.realBuffer().array());
                onlinePlayer.sendPluginMessage(this, Constants.FORGE, forgeBuffer.realBuffer().array());
            }
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Send ALL existing gender data to a player who has joined. !! IGNORES SYNC STATUS !!
        for (GenderData data : genderDataMap.values()) {
            MCProtoBuf buffer = new MCProtoBuf(Unpooled.buffer());
            MCProtoBuf forgeBuffer = new MCProtoBuf(Unpooled.buffer());
            forgeBuffer.realBuffer().writeByte(1);

            data.encode(buffer);
            data.encode(forgeBuffer);

            getLogger().info("Sending gender data from %s(%s) to %s".formatted(data.uuid, data.gender.name(), player.getName()));
            player.sendPluginMessage(this, Constants.SYNC, buffer.realBuffer().array());
            player.sendPluginMessage(this, Constants.FORGE, forgeBuffer.realBuffer().array());
        }
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        genderDataMap.remove(player.getUniqueId());  // Remove gender data for a player who is no longer online.
        getLogger().info("Removed gender data for %s(%s)".formatted(player.getUniqueId(), player.getName()));
    }
}
