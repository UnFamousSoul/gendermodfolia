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

package dbrighthd.wildfiregendermodplugin.utilities;

import org.bukkit.NamespacedKey;

public class Constants {
    public static final String MOD_ID = "wildfire_gender";
    public static final String SEND_GENDER_INFO = MOD_ID + ":send_gender_info";
    public static final String SYNC = MOD_ID + ":sync";
    public static final String FORGE = MOD_ID + ":main_channel";

    public static final NamespacedKey FEMALE_HURT = new NamespacedKey(MOD_ID, "female_hurt");
}
