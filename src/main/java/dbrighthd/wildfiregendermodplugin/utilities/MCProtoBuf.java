package dbrighthd.wildfiregendermodplugin.utilities;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

/**
 * <p>An attempt to de-NMS for (hopefully) easier maintainability.</p>
 * <p>Methods shamelessly stolen from <a href="https://wiki.vg/Data_types">https://wiki.v/Data_types</a>.</p>
 *
 * @param realBuffer The ByteBuf instance this helper class should utilize for its operations
 * @author Hannah (github.com/winnpixie)
 */
public record MCProtoBuf(ByteBuf realBuffer) {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = realBuffer.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public MCProtoBuf writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                realBuffer.writeByte(value);
                break;
            }

            realBuffer.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }

        return this;
    }

    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = realBuffer.readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;
            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public MCProtoBuf writeVarLong(long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                realBuffer.writeByte((int) value);
                break;
            }

            realBuffer.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }

        return this;
    }

    public MCProtoBuf writeUUID(UUID uuid) {
        realBuffer.writeLong(uuid.getMostSignificantBits());
        realBuffer.writeLong(uuid.getLeastSignificantBits());

        return this;
    }

    public UUID readUUID() {
        return new UUID(realBuffer.readLong(), realBuffer.readLong());
    }

    public <T extends Enum<T>> MCProtoBuf writeEnum(T enumObj) {
        writeVarInt(enumObj.ordinal());

        return this;
    }

    public <T extends Enum<T>> T readEnum(Class<T> enumCls) {
        return enumCls.getEnumConstants()[readVarInt()];
    }
}
