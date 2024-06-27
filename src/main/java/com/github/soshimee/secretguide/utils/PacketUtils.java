package com.github.soshimee.secretguide.utils;

import net.minecraft.client.Minecraft;

public class PacketUtils {
	public static void sendPacket(Object packet) {
		Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().write(packet);
	}
}
