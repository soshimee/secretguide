package com.github.soshimee.secretguide.utils;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.LocrawEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LocationUtils {
	public enum LocationType {
		DUNGEONS,
		OTHER
	}

	private static LocationType currentLocation = LocationType.OTHER;

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		currentLocation = LocationType.OTHER;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.schedule(() -> {
			Minecraft mc = Minecraft.getMinecraft();
			WorldClient world = mc.theWorld;
			Scoreboard scoreboard = world.getScoreboard();
			if (scoreboard == null) return;
			for (String line : ScoreboardUtils.getSidebarScores(scoreboard)) {
				if (line.matches("^ §7⏣ §cThe Catac§combs §7\\((\\w+)\\)$")) {
					currentLocation = LocationType.DUNGEONS;
					return;
				}
			}
			currentLocation = LocationType.OTHER;
		}, 2500, TimeUnit.MILLISECONDS);
	}

	public static LocationType getCurrentLocation() {
		return currentLocation;
	}
}
