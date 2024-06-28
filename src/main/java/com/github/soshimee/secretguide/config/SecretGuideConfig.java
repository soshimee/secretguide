package com.github.soshimee.secretguide.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
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
		max = 6.0f
	)
	public static float secretAuraRange = 2.1f;

	@Slider(
		name = "Skull Range",
		min = 2.1f,
		max = 4.5f
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

	@Slider(
		name = "Slot",
		min = 0,
		max = 9
	)
	public static int secretAuraSlot = 0;

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
