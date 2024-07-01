package com.github.soshimee.secretguide.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
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

	public LocationUtils() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(() -> {
			try {
				currentLocation = LocationType.OTHER;
				Minecraft mc = Minecraft.getMinecraft();
				WorldClient world = mc.theWorld;
				if (world == null) return;
				Scoreboard scoreboard = world.getScoreboard();
				if (scoreboard == null) return;
				for (String line : ScoreboardUtils.getSidebarScores(scoreboard)) {
					if (line.matches("^ §7⏣ §cThe Catac§combs §7\\((\\w+)\\)$")) {
						currentLocation = LocationType.DUNGEONS;
						return;
					}
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);
	}

	private static LocationType currentLocation = LocationType.OTHER;

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		currentLocation = LocationType.OTHER;
	}

	public static LocationType getCurrentLocation() {
		return currentLocation;
	}
}
