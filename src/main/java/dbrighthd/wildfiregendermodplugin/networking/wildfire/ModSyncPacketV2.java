package dbrighthd.wildfiregendermodplugin.networking.wildfire;

import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftInputStream;
import dbrighthd.wildfiregendermodplugin.networking.minecraft.CraftOutputStream;
import dbrighthd.wildfiregendermodplugin.wildfire.Gender;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConfiguration;
import dbrighthd.wildfiregendermodplugin.wildfire.ModConfigurationBuilder;

import java.io.IOException;

/**
 * @author winnpixie
 */
public class ModSyncPacketV2 implements ModSyncPacket {
    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    public String getModRange() {
        return "2.8.1 - 3.0.1";
    }

    @Override
    public ModConfiguration read(CraftInputStream input) throws IOException {
        return new ModConfigurationBuilder()
                .setUserId(input.readUUID())
                .setGender(input.readEnum(Gender.class))
                .setBustSize(input.readFloat())
                .setHurtSounds(input.readBoolean())
                .setBreastPhysics(input.readBoolean())
                .setArmorPhysics(input.readBoolean())
                .setShowInArmor(input.readBoolean())
                .setBuoyancy(input.readFloat())
                .setFloppiness(input.readFloat())
                .setXOffset(input.readFloat())
                .setYOffset(input.readFloat())
                .setZOffset(input.readFloat())
                .setUniBoob(input.readBoolean())
                .setCleavage(input.readFloat())
                .create();
    }

    @Override
    public void write(ModConfiguration modConfiguration, CraftOutputStream output) throws IOException {
        output.writeUUID(modConfiguration.getUserId());
        output.writeEnum(modConfiguration.getGender());

        output.writeFloat(modConfiguration.getBustSize());
        output.writeBoolean(modConfiguration.hasHurtSounds());
        output.writeBoolean(modConfiguration.hasBreastPhysics());
        output.writeBoolean(modConfiguration.hasArmorPhysics());
        output.writeBoolean(modConfiguration.shouldShowInArmor());
        output.writeFloat(modConfiguration.getBuoyancy());
        output.writeFloat(modConfiguration.getFloppiness());

        output.writeFloat(modConfiguration.getXOffset());
        output.writeFloat(modConfiguration.getYOffset());
        output.writeFloat(modConfiguration.getZOffset());
        output.writeBoolean(modConfiguration.isUniBoob());
        output.writeFloat(modConfiguration.getCleavage());
    }
}
