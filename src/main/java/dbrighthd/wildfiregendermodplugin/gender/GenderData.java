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

import dbrighthd.wildfiregendermodplugin.protocol.minecraft.CraftInputStream;
import dbrighthd.wildfiregendermodplugin.protocol.minecraft.CraftOutputStream;

import java.io.IOException;
import java.util.UUID;

public class GenderData {
    public boolean needsSync = false;

    public UUID uuid;
    public Gender gender;

    public float bustSize;
    public boolean hurtSounds;
    public float voicePitch;

    // Breast physics variables
    public boolean breastPhysics;
    public boolean showInArmor;
    public float bounceMultiplier;
    public float floppyMultiplier;

    // Breast variables
    public float xOffset;
    public float yOffset;
    public float zOffset;
    public boolean uniboob;
    public float cleavage;

    public void encode(CraftOutputStream outputStream) throws IOException {
        outputStream.writeUUID(this.uuid);
        outputStream.writeEnum(this.gender);

        outputStream.writeFloat(this.bustSize);
        outputStream.writeBoolean(this.hurtSounds);
        outputStream.writeFloat(this.voicePitch);
        outputStream.writeBoolean(this.breastPhysics);
        outputStream.writeBoolean(this.showInArmor);
        outputStream.writeFloat(this.bounceMultiplier);
        outputStream.writeFloat(this.floppyMultiplier);

        outputStream.writeFloat(this.xOffset);
        outputStream.writeFloat(this.yOffset);
        outputStream.writeFloat(this.zOffset);
        outputStream.writeBoolean(this.uniboob);
        outputStream.writeFloat(this.cleavage);
    }

    public static GenderData decode(CraftInputStream inputStream) throws IOException {
        GenderData data = new GenderData();

        data.uuid = inputStream.readUUID();
        data.gender = inputStream.readEnum(Gender.class);

        data.bustSize = inputStream.readFloat();
        data.hurtSounds = inputStream.readBoolean();
        data.voicePitch = inputStream.readFloat();
        data.breastPhysics = inputStream.readBoolean();
        data.showInArmor = inputStream.readBoolean();
        data.bounceMultiplier = inputStream.readFloat();
        data.floppyMultiplier = inputStream.readFloat();

        data.xOffset = inputStream.readFloat();
        data.yOffset = inputStream.readFloat();
        data.zOffset = inputStream.readFloat();
        data.uniboob = inputStream.readBoolean();
        data.cleavage = inputStream.readFloat();

        return data;
    }
}
