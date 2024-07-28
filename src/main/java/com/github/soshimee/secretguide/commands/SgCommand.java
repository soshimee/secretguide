package com.github.soshimee.secretguide.commands;

import com.github.soshimee.secretguide.SecretGuide;
import com.github.soshimee.secretguide.config.SecretGuideConfig;
import com.github.soshimee.secretguide.features.SecretAura;
import com.github.soshimee.secretguide.utils.ChatUtils;
import com.github.soshimee.secretguide.utils.DungeonLocationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SgCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "sg";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length < 1) {
			SecretGuide.config.openGui();
			ChatUtils.sendModMessage("/sg t | /sg c");
		} else if (Objects.equals(args[0], "roomid")) {
            ChatUtils.sendModMessage(String.valueOf(DungeonLocationUtils.getCurrentRoomId()));
		} else if (args[0].startsWith("t")) {
			SecretGuideConfig.secretAuraEnabled = !SecretGuideConfig.secretAuraEnabled;
			ChatUtils.sendModMessage(SecretGuideConfig.secretAuraEnabled ? "Secret Aura enabled!" : "Secret Aura disabled!");
		} else if (args[0].startsWith("c")) {
			SecretAura.clearBlocks();
			ChatUtils.sendModMessage("Blocks cleared!");
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getCommandAliases() {
		return Collections.emptyList();
	}
}
