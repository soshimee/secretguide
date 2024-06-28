package com.github.soshimee.secretguide.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonLocationUtils {
	private static int roomX = -1;
	private static int roomZ = -1;
	private static int currentRoomId = 0;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (LocationUtils.getCurrentLocation() != LocationUtils.LocationType.DUNGEONS) {
			roomX = -1;
			roomZ = -1;
			currentRoomId = 0;
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		int prevRoomX = roomX, prevRoomZ = roomZ;
		roomX = (int) ((player.posX + 200) / 32);
		roomZ = (int) ((player.posZ + 200) / 32);
		if (prevRoomX != roomX || prevRoomZ != roomZ) {
			int cx = -185 + roomX * 32;
			int cz = -185 + roomZ * 32;
			List<Integer> blocks = new ArrayList<>();
			WorldClient world = mc.theWorld;
			for (int y = 140; y > 11; --y) {
				int id = Block.getIdFromBlock(world.getBlockState(new BlockPos(cx, y, cz)).getBlock());
				if (id != 5 && id != 54) blocks.add(id);
			}
			currentRoomId = blocks.stream().map(String::valueOf).collect(Collectors.joining()).hashCode();
		}
	}

	public static int getCurrentRoomId() {
		return currentRoomId;
	}
}
