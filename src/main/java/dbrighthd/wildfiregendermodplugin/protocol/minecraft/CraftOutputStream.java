package dbrighthd.wildfiregendermodplugin.protocol.minecraft;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class CraftOutputStream extends DataOutputStream implements CraftDataStream {
    public CraftOutputStream(OutputStream out) {
        super(out);
    }

    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                super.writeByte(value);
                break;
            }

            super.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public void writeVarLong(long value) throws IOException {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                super.writeByte((int) value);
                break;
            }

            super.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public void writeUUID(UUID uuid) throws IOException {
        super.writeLong(uuid.getMostSignificantBits());
        super.writeLong(uuid.getLeastSignificantBits());
    }

    public <T extends Enum<T>> void writeEnum(T enumObj) throws IOException {
        writeVarInt(enumObj.ordinal());
    }
}
