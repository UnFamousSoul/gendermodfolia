/*
    Wildfire's Female Gender Mod is a female gender mod created for Minecraft.
    Copyright (C) 2023 WildfireRomeo

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package dbrighthd.wildfiregendermodplugin;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Function;

public class GenderData {

	public static final SoundEvent FEMALE_HURT = SoundEvent.createVariableRangeEvent(new ResourceLocation(GenderPlugin.MODID, "female_hurt"));

	protected static void register() {
		Registry.register(BuiltInRegistries.SOUND_EVENT, FEMALE_HURT.getLocation(), FEMALE_HURT);
	}

	public enum Gender implements Function<UUID, Gender> {
		FEMALE(Component.translatable("wildfire_gender.label.female").withStyle(ChatFormatting.LIGHT_PURPLE), true, FEMALE_HURT),
		MALE(Component.translatable("wildfire_gender.label.male").withStyle(ChatFormatting.BLUE), false, null),
		OTHER(Component.translatable("wildfire_gender.label.other").withStyle(ChatFormatting.GREEN), true, FEMALE_HURT);

		private final Component name;
		private final boolean canHaveBreasts;
		private final @Nullable SoundEvent hurtSound;
		public boolean needsSync;
		public  UUID uuid;
		private Gender gender;
		public float bust_size;
		public boolean hurtSounds;

		//physics variablesz
		public boolean breast_physics;
		public boolean show_in_armor;
		public float bounceMultiplier;
		public float floppyMultiplier;

		public float xOffset;
		public float yOffset;
		public float zOffset;
		public boolean uniboob;
		public float cleavage;

		Gender(Component name, boolean canHaveBreasts, @Nullable SoundEvent hurtSound) {
			this.name = name;
			this.canHaveBreasts = canHaveBreasts;
			this.hurtSound = hurtSound;
		}

		public Component getDisplayName() {
			return name;
		}

		public @Nullable SoundEvent getHurtSound() {
			return hurtSound;
		}

		public boolean canHaveBreasts() {
			return canHaveBreasts;
		}

		@Override
		public Gender apply(UUID uuid) {
			return null;
		}
	}

}
