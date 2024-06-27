package com.github.soshimee.secretguide.features;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import com.github.soshimee.secretguide.config.SecretGuideConfig;
import com.github.soshimee.secretguide.utils.ChatUtils;
import com.github.soshimee.secretguide.utils.LocationUtils;
import com.github.soshimee.secretguide.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoClose {
	public AutoClose() {
		EventManager.INSTANCE.register(this);
	}

	private static Integer closeId = null;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (closeId != null) {
			PacketUtils.sendPacket(new C0DPacketCloseWindow(closeId));
			closeId = null;
		}
	}

	@Subscribe
	public void onPacketReceive(ReceivePacketEvent event) {
		if (!SecretGuideConfig.secretAuraAutoClose) return;
		if (!SecretGuideConfig.secretAuraEnabled) return;
		if (LocationUtils.getCurrentLocation() != LocationUtils.LocationType.DUNGEONS && !SecretGuideConfig.secretAuraNotDungeon) return;
		if (event.packet instanceof S2DPacketOpenWindow) {
			S2DPacketOpenWindow packet = (S2DPacketOpenWindow) event.packet;
			if (!packet.getGuiId().equals("minecraft:chest")) return;
			if ((packet.getWindowTitle().getFormattedText().equals("Chest§r") && packet.getSlotCount() == 27) || (packet.getWindowTitle().getFormattedText().equals("Large Chest§r") && packet.getSlotCount() == 54)) {
				closeId = packet.getWindowId();
				event.isCancelled = true;
			}
		}
	}
}
