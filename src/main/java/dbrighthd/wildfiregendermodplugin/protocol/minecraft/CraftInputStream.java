package dbrighthd.wildfiregendermodplugin.protocol.minecraft;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class CraftInputStream extends DataInputStream implements CraftDataStream {
    public CraftInputStream(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    public CraftInputStream(InputStream in) {
        super(in);
    }

    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = super.readByte();

            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;
            if (position >= 32) throw new IOException("VarInt is too big");
        }

        return value;
    }

    public long readVarLong() throws IOException {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = super.readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;
            if (position >= 64) throw new IOException("VarLong is too big");
        }

        return value;
    }

    public UUID readUUID() throws IOException {
        return new UUID(super.readLong(), super.readLong());
    }

    public <T extends Enum<T>> T readEnum(Class<T> enumClass) throws IOException {
        return enumClass.getEnumConstants()[readVarInt()];
    }
}
