package com.github.soshimee.secretguide.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
	public static void sendModMessage(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§9S§2G§7] §d" + message));
	}
}
