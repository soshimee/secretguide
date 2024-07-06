package com.github.soshimee.secretguide.features;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import com.github.soshimee.secretguide.config.SecretGuideConfig;
import com.github.soshimee.secretguide.utils.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class SecretAura {
	private static final List<BlockPos> blocksDone = new LinkedList<>();
	private static final Map<BlockPos, Long> blocksCooldown = new HashMap<>();
	private static boolean redstoneKey = false;

	public SecretAura() {
		EventManager.INSTANCE.register(this);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (!SecretGuideConfig.secretAuraEnabled) return;
		if (event.phase != TickEvent.Phase.END) return;
		if (LocationUtils.getCurrentLocation() != LocationUtils.LocationType.DUNGEONS && !SecretGuideConfig.secretAuraNotDungeon) return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		if (player == null) return;
		WorldClient world = mc.theWorld;
		if (world == null) return;
		Vec3 eyePos = player.getPositionEyes(0);
		BlockPos blockPos1 = new BlockPos(eyePos.xCoord - SecretGuideConfig.secretAuraRange, eyePos.yCoord - SecretGuideConfig.secretAuraRange, eyePos.zCoord - SecretGuideConfig.secretAuraRange);
		BlockPos blockPos2 = new BlockPos(eyePos.xCoord + SecretGuideConfig.secretAuraRange, eyePos.yCoord + SecretGuideConfig.secretAuraRange, eyePos.zCoord + SecretGuideConfig.secretAuraRange);
		Iterable<BlockPos> positions = BlockPos.getAllInBox(blockPos1, blockPos2);
		long time = new Date().getTime();
		int roomId = DungeonLocationUtils.getCurrentRoomId();
		for (BlockPos position : positions) {
			if (blocksDone.contains(position)) continue;
			if (blocksCooldown.containsKey(position) && blocksCooldown.get(position) + 500 > time) continue;
			IBlockState blockState = world.getBlockState(position);
			if (blockState.getBlock() == Blocks.chest || blockState.getBlock() == Blocks.trapped_chest) {
				if (roomId == 2051424561 || roomId == 884728242 || roomId == -269974565 || roomId == 1262122263 || roomId == 1073658098 || roomId == -476788643) continue;
				Vec3 centerPos = new Vec3(position.getX() + 0.5, position.getY() + 0.4375, position.getZ() + 0.5);
				if (eyePos.distanceTo(centerPos) <= SecretGuideConfig.secretAuraRange) {
					MovingObjectPosition movingObjectPosition = BlockUtils.collisionRayTrace(position, 0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375, eyePos, centerPos);
					if (movingObjectPosition == null) continue;
					PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(position, movingObjectPosition.sideHit.getIndex(), player.getHeldItem(), (float) movingObjectPosition.hitVec.xCoord, (float) movingObjectPosition.hitVec.yCoord, (float) movingObjectPosition.hitVec.zCoord));
					if (!player.isSneaking() && SecretGuideConfig.secretAuraSwingHand) player.swingItem();
					blocksCooldown.put(position, new Date().getTime());
					break;
				}
			} else if (blockState.getBlock() == Blocks.lever) {
				if (roomId == -109725212 || roomId == -353291158 || roomId == 1998063202 || roomId == 660384222 || roomId == -1012522341 || roomId == 660396563 || roomId == 1980639456 || roomId == 43497702 || roomId == 2014437159 || roomId == 1513261276 || roomId == 862140000 || roomId == -364886424 || roomId == -714138899 || roomId == -1489069695 || roomId == -685683836) continue;
				BlockLever.EnumOrientation orientation = (BlockLever.EnumOrientation) blockState.getProperties().get(BlockLever.FACING);
				float minX, minY, minZ, maxX, maxY, maxZ;
				if (orientation == BlockLever.EnumOrientation.EAST) {
					minX = 0.0f;
					minY = 0.2f;
					minZ = 0.3125f;
					maxX = 0.375f;
					maxY = 0.8f;
					maxZ = 0.6875f;
				} else if (orientation == BlockLever.EnumOrientation.WEST) {
					minX = 0.625f;
					minY = 0.2f;
					minZ = 0.3125f;
					maxX = 1.0f;
					maxY = 0.8f;
					maxZ = 0.6875f;
				} else if (orientation == BlockLever.EnumOrientation.SOUTH) {
					minX = 0.3125f;
					minY = 0.2f;
					minZ = 0.0f;
					maxX = 0.6875f;
					maxY = 0.8f;
					maxZ = 0.375f;
				} else if (orientation == BlockLever.EnumOrientation.NORTH) {
					minX = 0.3125f;
					minY = 0.2f;
					minZ = 0.625f;
					maxX = 0.6875f;
					maxY = 0.8f;
					maxZ = 1.0f;
				} else if (orientation == BlockLever.EnumOrientation.UP_Z || orientation == BlockLever.EnumOrientation.UP_X) {
					minX = 0.25f;
					minY = 0.0f;
					minZ = 0.25f;
					maxX = 0.75f;
					maxY = 0.6f;
					maxZ = 0.75f;
				} else if (orientation == BlockLever.EnumOrientation.DOWN_X || orientation == BlockLever.EnumOrientation.DOWN_Z) {
					minX = 0.25f;
					minY = 0.4f;
					minZ = 0.25f;
					maxX = 0.75f;
					maxY = 1.0f;
					maxZ = 0.75f;
				} else continue;
				Vec3 centerPos = new Vec3(position.getX() + (minX + maxX) / 2, position.getY() + (minY + maxY) / 2, position.getZ() + (minZ + maxZ) / 2);
				if (eyePos.distanceTo(centerPos) <= SecretGuideConfig.secretAuraRange) {
					MovingObjectPosition movingObjectPosition = BlockUtils.collisionRayTrace(position, minX, minY, minZ, maxX, maxY, maxZ, eyePos, centerPos);
					if (movingObjectPosition == null) continue;
					PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(position, movingObjectPosition.sideHit.getIndex(), player.getHeldItem(), (float) movingObjectPosition.hitVec.xCoord, (float) movingObjectPosition.hitVec.yCoord, (float) movingObjectPosition.hitVec.zCoord));
					if (!player.isSneaking() && SecretGuideConfig.secretAuraSwingHand) player.swingItem();
					blocksCooldown.put(position, new Date().getTime());
					break;
				}
			} else if (blockState.getBlock() == Blocks.skull) {
				TileEntity tileEntity = world.getTileEntity(position);
				if (!(tileEntity instanceof TileEntitySkull)) continue;
				GameProfile profile = ((TileEntitySkull) tileEntity).getPlayerProfile();
				if (profile == null) continue;
				String profileId = profile.getId().toString();
				if (!Objects.equals(profileId, "26bb1a8d-7c66-31c6-82d5-a9c04c94fb02")) {
					if (!Objects.equals(profileId, "edb0155f-379c-395a-9c7d-1b6005987ac8")) continue;
					else if (world.getBlockState(position.down()).getBlock() == Blocks.redstone_block || world.getBlockState(position.north()).getBlock() == Blocks.redstone_block || world.getBlockState(position.south()).getBlock() == Blocks.redstone_block || world.getBlockState(position.west()).getBlock() == Blocks.redstone_block || world.getBlockState(position.east()).getBlock() == Blocks.redstone_block) {
						redstoneKey = false;
						blocksDone.add(position);
						continue;
					}
				}
				EnumFacing facing = (EnumFacing) blockState.getProperties().get(BlockSkull.FACING);
				float minX, minY, minZ, maxX, maxY, maxZ;
				if (facing == EnumFacing.NORTH) {
					minX = 0.25f;
					minY = 0.25f;
					minZ = 0.5f;
					maxX = 0.75f;
					maxY = 0.75f;
					maxZ = 1.0f;
				} else if (facing == EnumFacing.SOUTH) {
					minX = 0.25f;
					minY = 0.25f;
					minZ = 0.0f;
					maxX = 0.75f;
					maxY = 0.75f;
					maxZ = 0.5f;
				} else if (facing == EnumFacing.WEST) {
					minX = 0.5f;
					minY = 0.25f;
					minZ = 0.25f;
					maxX = 1.0f;
					maxY = 0.75f;
					maxZ = 0.75f;
				} else if (facing == EnumFacing.EAST) {
					minX = 0.0f;
					minY = 0.25f;
					minZ = 0.25f;
					maxX = 0.5f;
					maxY = 0.75f;
					maxZ = 0.75f;
				} else {
					minX = 0.25f;
					minY = 0.0f;
					minZ = 0.25f;
					maxX = 0.75f;
					maxY = 0.5f;
					maxZ = 0.75f;
				}
				Vec3 centerPos = new Vec3(position.getX() + (minX + maxX) / 2, position.getY() + (minY + maxY) / 2, position.getZ() + (minZ + maxZ) / 2);
				if (eyePos.distanceTo(centerPos) <= SecretGuideConfig.secretAuraSkullRange) {
					MovingObjectPosition movingObjectPosition = BlockUtils.collisionRayTrace(position, minX, minY, minZ, maxX, maxY, maxZ, eyePos, centerPos);
					if (movingObjectPosition == null) continue;
					if (SecretGuideConfig.secretAuraSlot > 0 && player.inventory.currentItem != SecretGuideConfig.secretAuraSlot - 1) {
						player.inventory.currentItem = SecretGuideConfig.secretAuraSlot - 1;
						break;
					}
					PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(position, movingObjectPosition.sideHit.getIndex(), player.getHeldItem(), (float) movingObjectPosition.hitVec.xCoord, (float) movingObjectPosition.hitVec.yCoord, (float) movingObjectPosition.hitVec.zCoord));
					blocksCooldown.put(position, new Date().getTime());
					break;
				}
			} else if (blockState.getBlock() == Blocks.redstone_block) {
				if (!redstoneKey) continue;
				if (world.getBlockState(position.up()).getBlock() == Blocks.skull || world.getBlockState(position.north()).getBlock() == Blocks.skull || world.getBlockState(position.south()).getBlock() == Blocks.skull || world.getBlockState(position.west()).getBlock() == Blocks.skull || world.getBlockState(position.east()).getBlock() == Blocks.skull) {
					redstoneKey = false;
					blocksDone.add(position);
					continue;
				}
				Vec3 centerPos = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
				if (eyePos.distanceTo(centerPos) <= SecretGuideConfig.secretAuraRange) {
					MovingObjectPosition movingObjectPosition = BlockUtils.collisionRayTrace(position, 0, 0, 0, 1, 1, 1, eyePos, centerPos);
					if (movingObjectPosition == null) continue;
					if (movingObjectPosition.sideHit == EnumFacing.DOWN) continue;
					if (SecretGuideConfig.secretAuraSlot > 0 && player.inventory.currentItem != SecretGuideConfig.secretAuraSlot - 1) {
						player.inventory.currentItem = SecretGuideConfig.secretAuraSlot - 1;
						break;
					}
					PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(position, movingObjectPosition.sideHit.getIndex(), player.getHeldItem(), (float) movingObjectPosition.hitVec.xCoord, (float) movingObjectPosition.hitVec.yCoord, (float) movingObjectPosition.hitVec.zCoord));
					blocksCooldown.put(position, new Date().getTime());
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		clearBlocks();
	}

	@Subscribe
	public void onPacketReceive(ReceivePacketEvent event) {
		if (event.packet instanceof S24PacketBlockAction) {
			S24PacketBlockAction packet = (S24PacketBlockAction) event.packet;
			if (packet.getBlockType() == Blocks.chest) {
				blocksDone.add(packet.getBlockPosition());
			}
		} else if (event.packet instanceof S23PacketBlockChange) {
			S23PacketBlockChange packet = (S23PacketBlockChange) event.packet;
			Minecraft mc = Minecraft.getMinecraft();
			WorldClient world = mc.theWorld;
			BlockPos blockPos = packet.getBlockPosition();
			IBlockState blockState = world.getBlockState(blockPos);
			if (blockState.getBlock() == Blocks.lever) {
				blocksDone.add(blockPos);
			} else if (blockState.getBlock() == Blocks.skull) {
				if (packet.getBlockState().getBlock() != Blocks.air) return;
				TileEntity tileEntity = world.getTileEntity(blockPos);
				if (!(tileEntity instanceof TileEntitySkull)) return;
				GameProfile profile = ((TileEntitySkull) tileEntity).getPlayerProfile();
				if (profile == null) return;
				String profileId = profile.getId().toString();
				if (profileId.equals("edb0155f-379c-395a-9c7d-1b6005987ac8")) {
					redstoneKey = true;
				}
			}
		} else if (event.packet instanceof S22PacketMultiBlockChange) {
			S22PacketMultiBlockChange packet = (S22PacketMultiBlockChange) event.packet;
			Minecraft mc = Minecraft.getMinecraft();
			WorldClient world = mc.theWorld;
			for (S22PacketMultiBlockChange.BlockUpdateData changedBlock : packet.getChangedBlocks()) {
				BlockPos blockPos = changedBlock.getPos();
				IBlockState blockState = world.getBlockState(blockPos);
				if (blockState.getBlock() == Blocks.lever) {
					blocksDone.add(blockPos);
				} else if (blockState.getBlock() == Blocks.skull) {
					if (changedBlock.getBlockState().getBlock() != Blocks.air) return;
					TileEntity tileEntity = world.getTileEntity(blockPos);
					if (!(tileEntity instanceof TileEntitySkull)) return;
					GameProfile profile = ((TileEntitySkull) tileEntity).getPlayerProfile();
					if (profile == null) return;
					String profileId = profile.getId().toString();
					if (profileId.equals("edb0155f-379c-395a-9c7d-1b6005987ac8")) {
						redstoneKey = true;
					}
				}
			}
		}
	}

	public static void clearBlocks() {
		blocksDone.clear();
		blocksCooldown.clear();
	}
}
