package dbrighthd.wildfiregendermodplugin.listeners;

import dbrighthd.wildfiregendermodplugin.GenderModPlugin;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConstants;
import dbrighthd.wildfiregendermodplugin.wildfire.ModUser;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

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

        ModUser user = plugin.getNetworkManager().deserializeUser(message, channel.equals(ModConstants.FORGE));
        if (user == null) return;

        if (!player.getUniqueId().equals(user.userId())) {
            plugin.getCustomLogger().warning("Unauthorized access attempt by %s for %s",
                    player.getName(), user.userId());

            // Early return, unauthorized attempt to set another player's data.
            return;
        }

        plugin.getUserManager().getUsers().put(user.userId(), user);
        plugin.getCustomLogger().debug("Stored %s as %s",
                player.getName(), user.configuration().generalOptions().genderIdentity().name());

        // Sync mod configurations for ALL online players.
        plugin.getNetworkManager().sync(plugin.getServer().getOnlinePlayers());
    }
}
