package dbrighthd.wildfiregendermodplugin.networking.wildfire;

import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftInputStream;
import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftOutputStream;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConfiguration;

import java.io.IOException;

/**
 * Network packet for sending and receiving a user's mod configuration.
 *
 * @author winnpixie
 */
public interface ModSyncPacket {
    /**
     * The protocol version identifier for this packet
     *
     * @return the {@code int} representation of this protocol version identifier
     */
    int getVersion();

    /**
     * The range of mod versions this packet supports.
     *
     * @return a {@code String} in the form of "major<sup>1</sup>.minor<sup>1</sup>.patch<sup>1</sup> - major<sup>2</sup>.minor<sup>2</sup>.patch<sup>2</sup>"
     */
    String getModRange();

    /**
     * Decodes data from a {@link CraftInputStream} to create a {@link ModConfiguration}
     *
     * @param input The input stream to read data from.
     * @return the {@link ModConfiguration} value read.
     * @throws IOException if an I/O error occurs in the supplied {@link CraftInputStream}
     */
    ModConfiguration read(CraftInputStream input) throws IOException;

    /**
     * Encodes modConfiguration from a {@link ModConfiguration} with a {@link CraftOutputStream}
     *
     * @param modConfiguration The {@link ModConfiguration} to send over the network.
     * @param output           The output stream to write {@code modConfiguration} to.
     * @throws IOException if an I/O error occurs in the supplied {@link CraftOutputStream}
     */
    void write(ModConfiguration modConfiguration, CraftOutputStream output) throws IOException;
}
