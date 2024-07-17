package com.github.soshimee.secretguide.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtils {
	public static void sendPacket(Packet packet) {
		Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
	}
}
