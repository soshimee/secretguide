package com.github.soshimee.secretguide.utils;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class BlockUtils {
	public static MovingObjectPosition collisionRayTrace(BlockPos pos, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Vec3 start, Vec3 end) {
		start = start.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
		end = end.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
		Vec3 vec3 = start.getIntermediateWithXValue(end, minX);
		Vec3 vec31 = start.getIntermediateWithXValue(end, maxX);
		Vec3 vec32 = start.getIntermediateWithYValue(end, minY);
		Vec3 vec33 = start.getIntermediateWithYValue(end, maxY);
		Vec3 vec34 = start.getIntermediateWithZValue(end, minZ);
		Vec3 vec35 = start.getIntermediateWithZValue(end, maxZ);

		if (!isVecInsideYZBounds(vec3, minY, minZ, maxY, maxZ)) vec3 = null;
		if (!isVecInsideYZBounds(vec31, minY, minZ, maxY, maxZ)) vec31 = null;
		if (!isVecInsideXZBounds(vec32, minX, minZ, maxX, maxZ)) vec32 = null;
		if (!isVecInsideXZBounds(vec33, minX, minZ, maxX, maxZ)) vec33 = null;
		if (!isVecInsideXYBounds(vec34, minX, minY, maxX, maxY)) vec34 = null;
		if (!isVecInsideXYBounds(vec35, minX, minY, maxX, maxY)) vec35 = null;

		Vec3 vec36 = null;
		if (vec3 != null && (vec36 == null || start.squareDistanceTo(vec3) < start.squareDistanceTo(vec36))) vec36 = vec3;
		if (vec31 != null && (vec36 == null || start.squareDistanceTo(vec31) < start.squareDistanceTo(vec36))) vec36 = vec31;
		if (vec32 != null && (vec36 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec36))) vec36 = vec32;
		if (vec33 != null && (vec36 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec36))) vec36 = vec33;
		if (vec34 != null && (vec36 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec36))) vec36 = vec34;
		if (vec35 != null && (vec36 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec36))) vec36 = vec35;

		if (vec36 == null) return null;
		else {
			EnumFacing enumfacing = null;
			if (vec36 == vec3) enumfacing = EnumFacing.WEST;
			if (vec36 == vec31) enumfacing = EnumFacing.EAST;
			if (vec36 == vec32) enumfacing = EnumFacing.DOWN;
			if (vec36 == vec33) enumfacing = EnumFacing.UP;
			if (vec36 == vec34) enumfacing = EnumFacing.NORTH;
			if (vec36 == vec35) enumfacing = EnumFacing.SOUTH;
			return new MovingObjectPosition(vec36, enumfacing, pos);
		}
	}

	private static boolean isVecInsideYZBounds(Vec3 point, double minY, double minZ, double maxY, double maxZ) {
		return point != null && point.yCoord >= minY && point.yCoord <= maxY && point.zCoord >= minZ && point.zCoord <= maxZ;
	}

	private static boolean isVecInsideXZBounds(Vec3 point, double minX, double minZ, double maxX, double maxZ) {
		return point != null && point.xCoord >= minX && point.xCoord <= maxX && point.zCoord >= minZ && point.zCoord <= maxZ;
	}

	private static boolean isVecInsideXYBounds(Vec3 point, double minX, double minY, double maxX, double maxY) {
		return point != null && point.xCoord >= minX && point.xCoord <= maxX && point.yCoord >= minY && point.yCoord <= maxY;
	}
}
