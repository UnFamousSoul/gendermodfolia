package dbrighthd.wildfiregendermodplugin.gender;

import dbrighthd.wildfiregendermodplugin.utilities.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

public enum Gender {
    FEMALE(new ComponentBuilder(new TranslatableComponent("wildfire_gender.label.female")).color(ChatColor.LIGHT_PURPLE).create(), true, Constants.FEMALE_HURT),
    MALE(new ComponentBuilder(new TranslatableComponent("wildfire_gender.label.male")).color(ChatColor.BLUE).create(), false, null),
    OTHER(new ComponentBuilder(new TranslatableComponent("wildfire_gender.label.other")).color(ChatColor.GREEN).create(), true, Constants.FEMALE_HURT);

    private final BaseComponent[] label;
    private final boolean canHaveBreasts;
    private final @Nullable NamespacedKey hurtSound;

    Gender(BaseComponent[] label, boolean canHaveBreasts, @Nullable NamespacedKey hurtSound) {
        this.label = label;
        this.canHaveBreasts = canHaveBreasts;
        this.hurtSound = hurtSound;
    }

    public BaseComponent[] getLabel() {
        return label;
    }

    public boolean canHaveBreasts() {
        return canHaveBreasts;
    }

    @Nullable
    public NamespacedKey getHurtSound() {
        return hurtSound;
    }
}
