package com.github.soshimee.secretguide.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.github.soshimee.secretguide.features.SecretAura;
import com.github.soshimee.secretguide.utils.ChatUtils;

public class SecretGuideConfig extends Config {
	@Switch(
		name = "Secret Aura"
	)
	public static boolean secretAuraEnabled = false;

	@Switch(
		name = "Enable Outside of Dungeon"
	)
	public static boolean secretAuraNotDungeon = false;

	@Slider(
		name = "Range",
		min = 2.1f,
		max = 6.5f
	)
	public static float secretAuraRange = 2.1f;

	@Slider(
		name = "Skull Range",
		min = 2.1f,
		max = 5.0f
	)
	public static float secretAuraSkullRange = 2.1f;

	@Switch(
		name = "Swing Hand"
	)
	public static boolean secretAuraSwingHand = true;

	@Switch(
		name = "Auto Close"
	)
	public static boolean secretAuraAutoClose = true;

	@KeyBind(
		name = "Toggle Secret Aura"
	)
	public static OneKeyBind secretAuraToggleKeyBind = new OneKeyBind();

	@KeyBind(
		name = "Clear Blocks"
	)
	public static OneKeyBind secretAuraClearKeyBind = new OneKeyBind();

	@Dropdown(
		name = "Swap On",
		options = {"None", "Skulls", "All"}
	)
	public static int secretAuraSwapOn = 0;

	@Switch(
		name = "Swap Back"
	)
	public static boolean secretAuraSwapBack = true;

	@Slider(
		name = "Slot",
		min = 1,
		max = 9
	)
	public static int secretAuraSlot = 1;

	@Checkbox(
		name = "Blaze",
		subcategory = "Rooms"
	)
	public static boolean roomBlaze = false;

	@Checkbox(
		name = "Boulder",
		subcategory = "Rooms"
	)
	public static boolean roomBoulder = true;

	@Checkbox(
		name = "Creeper Beams",
		subcategory = "Rooms"
	)
	public static boolean roomCreeperBeams = true;

	@Checkbox(
		name = "Ice Fill",
		subcategory = "Rooms"
	)
	public static boolean roomIceFill = false;

	@Checkbox(
		name = "Ice Path",
		subcategory = "Rooms"
	)
	public static boolean roomIcePath = false;

	@Checkbox(
		name = "Teleport Maze",
		subcategory = "Rooms"
	)
	public static boolean roomTeleportMaze = false;

	@Checkbox(
		name = "Three Weirdos",
		subcategory = "Rooms"
	)
	public static boolean roomThreeWeirdos = false;

	@Checkbox(
		name = "Tic Tac Toe",
		subcategory = "Rooms"
	)
	public static boolean roomTicTacToe = true;

	@Checkbox(
		name = "Water Board",
		subcategory = "Rooms"
	)
	public static boolean roomWaterBoard = false;

	public SecretGuideConfig() {
		super(new Mod("secretguide", ModType.SKYBLOCK), "config.json");
		initialize();
		registerKeyBind(secretAuraToggleKeyBind, () -> {
			secretAuraEnabled = !secretAuraEnabled;
			ChatUtils.sendModMessage(secretAuraEnabled ? "Secret Aura enabled!" : "Secret Aura disabled!");
		});
		registerKeyBind(secretAuraClearKeyBind, () -> {
			SecretAura.clearBlocks();
			ChatUtils.sendModMessage("Blocks cleared!");
		});
	}
}
