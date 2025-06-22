package dbrighthd.wildfiregendermodplugin.wildfire;

import java.util.UUID;

/**
 * A builder to create a {@link ModConfiguration}.
 *
 * @author winnpixie
 */
public class ModConfigurationBuilder {
    private UUID userId;
    private Gender gender;

    private float bustSize;
    private boolean hurtSounds;
    private float voicePitch;

    // Breast physics variables
    private boolean breastPhysics;
    private boolean armorPhysics;
    private boolean showInArmor;
    private float buoyancy;
    private float floppiness;

    // Breast variables
    private float xOffset;
    private float yOffset;
    private float zOffset;
    private boolean uniBoob;
    private float cleavage;

    public ModConfigurationBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public ModConfigurationBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public ModConfigurationBuilder setBustSize(float bustSize) {
        this.bustSize = bustSize;
        return this;
    }

    public ModConfigurationBuilder setHurtSounds(boolean hurtSounds) {
        this.hurtSounds = hurtSounds;
        return this;
    }

    public ModConfigurationBuilder setVoicePitch(float voicePitch) {
        this.voicePitch = voicePitch;
        return this;
    }

    public ModConfigurationBuilder setBreastPhysics(boolean breastPhysics) {
        this.breastPhysics = breastPhysics;
        return this;
    }

    public ModConfigurationBuilder setArmorPhysics(boolean armorPhysics) {
        this.armorPhysics = armorPhysics;
        return this;
    }

    public ModConfigurationBuilder setShowInArmor(boolean showInArmor) {
        this.showInArmor = showInArmor;
        return this;
    }

    public ModConfigurationBuilder setBuoyancy(float buoyancy) {
        this.buoyancy = buoyancy;
        return this;
    }

    public ModConfigurationBuilder setFloppiness(float floppiness) {
        this.floppiness = floppiness;
        return this;
    }

    public ModConfigurationBuilder setXOffset(float xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public ModConfigurationBuilder setYOffset(float yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public ModConfigurationBuilder setZOffset(float zOffset) {
        this.zOffset = zOffset;
        return this;
    }

    public ModConfigurationBuilder setUniBoob(boolean uniBoob) {
        this.uniBoob = uniBoob;
        return this;
    }

    public ModConfigurationBuilder setCleavage(float cleavage) {
        this.cleavage = cleavage;
        return this;
    }

    public ModConfiguration create() {
        return new ModConfiguration(userId,
                gender,
                hurtSounds,
                voicePitch,
                breastPhysics,
                armorPhysics,
                showInArmor,
                buoyancy,
                floppiness,
                bustSize,
                xOffset,
                yOffset,
                zOffset,
                uniBoob,
                cleavage);
    }
}
