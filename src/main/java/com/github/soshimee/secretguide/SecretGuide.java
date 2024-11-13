package com.github.soshimee.secretguide;

import com.github.soshimee.secretguide.commands.SgCommand;
import com.github.soshimee.secretguide.config.SecretGuideConfig;
import com.github.soshimee.secretguide.features.AutoClose;
import com.github.soshimee.secretguide.features.SecretAura;
import com.github.soshimee.secretguide.utils.DungeonLocationUtils;
import com.github.soshimee.secretguide.utils.LocationUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import static com.github.soshimee.secretguide.SecretGuide.MOD_ID;

@Mod(modid = MOD_ID, useMetadata = true)
public class SecretGuide {
	public static final String MOD_ID = "secretguide";
	public static SecretGuideConfig config;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		config = new SecretGuideConfig();
		MinecraftForge.EVENT_BUS.register(new LocationUtils());
		MinecraftForge.EVENT_BUS.register(new DungeonLocationUtils());
		MinecraftForge.EVENT_BUS.register(new SecretAura());
		MinecraftForge.EVENT_BUS.register(new AutoClose());
		ClientCommandHandler.instance.registerCommand(new SgCommand());
	}
}
