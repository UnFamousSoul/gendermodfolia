package dbrighthd.wildfiregendermodplugin.wildfire;

import java.util.UUID;

/**
 * An immutable representation of a user's mod configuration.
 *
 * @author winnpixie
 */
public final class ModConfiguration {
    private final UUID userId;
    private final Gender gender;

    private final boolean hurtSounds;
    private final float voicePitch;

    // Breast physics variables
    private final boolean breastPhysics;
    private final boolean armorPhysics;
    private final boolean showInArmor;
    private final float buoyancy;
    private final float floppiness;

    // Breast variables
    private final float bustSize;
    private final float xOffset;
    private final float yOffset;
    private final float zOffset;
    private final boolean uniBoob;
    private final float cleavage;

    ModConfiguration(UUID userId,
                     Gender gender,
                     boolean hurtSounds,
                     float voicePitch,
                     boolean breastPhysics,
                     boolean armorPhysics,
                     boolean showInArmor,
                     float buoyancy,
                     float floppiness,
                     float bustSize,
                     float xOffset, float yOffset, float zOffset,
                     boolean uniBoob,
                     float cleavage) {
        this.userId = userId;
        this.gender = gender;
        this.hurtSounds = hurtSounds;
        this.voicePitch = voicePitch;
        this.breastPhysics = breastPhysics;
        this.armorPhysics = armorPhysics;
        this.showInArmor = showInArmor;
        this.buoyancy = buoyancy;
        this.floppiness = floppiness;
        this.bustSize = bustSize;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.uniBoob = uniBoob;
        this.cleavage = cleavage;
    }

    public UUID getUserId() {
        return userId;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean hasHurtSounds() {
        return hurtSounds;
    }

    public float getVoicePitch() {
        return voicePitch;
    }

    public boolean hasBreastPhysics() {
        return breastPhysics;
    }

    public boolean hasArmorPhysics() {
        return armorPhysics;
    }

    public boolean shouldShowInArmor() {
        return showInArmor;
    }

    public float getBuoyancy() {
        return buoyancy;
    }

    public float getFloppiness() {
        return floppiness;
    }

    public float getBustSize() {
        return bustSize;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public float getZOffset() {
        return zOffset;
    }

    public boolean isUniBoob() {
        return uniBoob;
    }

    public float getCleavage() {
        return cleavage;
    }
}
