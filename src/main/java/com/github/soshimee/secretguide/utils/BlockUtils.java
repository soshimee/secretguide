package com.github.soshimee.secretguide.utils;

import net.minecraft.util.*;

public class BlockUtils {
	public static MovingObjectPosition collisionRayTrace(BlockPos pos, AxisAlignedBB aabb, Vec3 start, Vec3 end) {
		start = start.subtract(new Vec3(pos));
		end = end.subtract(new Vec3(pos));
		Vec3 vec3 = start.getIntermediateWithXValue(end, aabb.minX);
		Vec3 vec31 = start.getIntermediateWithXValue(end, aabb.maxX);
		Vec3 vec32 = start.getIntermediateWithYValue(end, aabb.minY);
		Vec3 vec33 = start.getIntermediateWithYValue(end, aabb.maxY);
		Vec3 vec34 = start.getIntermediateWithZValue(end, aabb.minZ);
		Vec3 vec35 = start.getIntermediateWithZValue(end, aabb.maxZ);

		if (isVecOutsideYZBounds(vec3, aabb.minY, aabb.minZ, aabb.maxY, aabb.maxZ)) vec3 = null;
		if (isVecOutsideYZBounds(vec31, aabb.minY, aabb.minZ, aabb.maxY, aabb.maxZ)) vec31 = null;
		if (isVecOutsideXZBounds(vec32, aabb.minX, aabb.minZ, aabb.maxX, aabb.maxZ)) vec32 = null;
		if (isVecOutsideXZBounds(vec33, aabb.minX, aabb.minZ, aabb.maxX, aabb.maxZ)) vec33 = null;
		if (isVecOutsideXYBounds(vec34, aabb.minX, aabb.minY, aabb.maxX, aabb.maxY)) vec34 = null;
		if (isVecOutsideXYBounds(vec35, aabb.minX, aabb.minY, aabb.maxX, aabb.maxY)) vec35 = null;

		Vec3 vec36 = null;
		if (vec3 != null) vec36 = vec3;
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

	private static boolean isVecOutsideYZBounds(Vec3 point, double minY, double minZ, double maxY, double maxZ) {
		return point == null || !(point.yCoord >= minY) || !(point.yCoord <= maxY) || !(point.zCoord >= minZ) || !(point.zCoord <= maxZ);
	}

	private static boolean isVecOutsideXZBounds(Vec3 point, double minX, double minZ, double maxX, double maxZ) {
		return point == null || !(point.xCoord >= minX) || !(point.xCoord <= maxX) || !(point.zCoord >= minZ) || !(point.zCoord <= maxZ);
	}

	private static boolean isVecOutsideXYBounds(Vec3 point, double minX, double minY, double maxX, double maxY) {
		return point == null || !(point.xCoord >= minX) || !(point.xCoord <= maxX) || !(point.yCoord >= minY) || !(point.yCoord <= maxY);
	}
}
