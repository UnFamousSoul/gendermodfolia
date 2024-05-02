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

package dbrighthd.wildfiregendermodplugin.gender;

import dbrighthd.wildfiregendermodplugin.utilities.MCProtoBuf;

import java.util.UUID;

public class GenderData {
    public boolean shouldSync = false;

    public UUID uuid;
    public Gender gender;

    public float bust_size;
    public boolean hurtSounds;

    // Physics variables
    public boolean breast_physics;
    public boolean show_in_armor;
    public float bounceMultiplier;
    public float floppyMultiplier;

    public float xOffset;
    public float yOffset;
    public float zOffset;
    public boolean uniboob;
    public float cleavage;

    public void encode(MCProtoBuf buffer) {
        buffer.writeUUID(this.uuid);
        buffer.writeEnum(this.gender);

        buffer.realBuffer().writeFloat(this.bust_size);
        buffer.realBuffer().writeBoolean(this.hurtSounds);
        buffer.realBuffer().writeBoolean(this.breast_physics);
        buffer.realBuffer().writeBoolean(this.show_in_armor);
        buffer.realBuffer().writeFloat(this.bounceMultiplier);
        buffer.realBuffer().writeFloat(this.floppyMultiplier);

        buffer.realBuffer().writeFloat(this.xOffset);
        buffer.realBuffer().writeFloat(this.yOffset);
        buffer.realBuffer().writeFloat(this.zOffset);
        buffer.realBuffer().writeBoolean(this.uniboob);
        buffer.realBuffer().writeFloat(this.cleavage);
    }

    public static GenderData decode(MCProtoBuf buffer) {
        GenderData data = new GenderData();

        data.uuid = buffer.readUUID();
        data.gender = buffer.readEnum(Gender.class);

        data.bust_size = buffer.realBuffer().readFloat();
        data.hurtSounds = buffer.realBuffer().readBoolean();
        data.breast_physics = buffer.realBuffer().readBoolean();
        data.show_in_armor = buffer.realBuffer().readBoolean();
        data.bounceMultiplier = buffer.realBuffer().readFloat();
        data.floppyMultiplier = buffer.realBuffer().readFloat();

        data.xOffset = buffer.realBuffer().readFloat();
        data.yOffset = buffer.realBuffer().readFloat();
        data.zOffset = buffer.realBuffer().readFloat();
        data.uniboob = buffer.realBuffer().readBoolean();
        data.cleavage = buffer.realBuffer().readFloat();

        return data;
    }
}
