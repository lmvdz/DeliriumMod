package net.lmvdz.delirium.util;

import net.lmvdz.delirium.warp.Warp;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;

/**
 * ServerPlayerUtilities
 */
public class ServerPlayerUtilities {
    public static void teleport(ServerPlayerEntity plr, Warp warp) {
        ServerWorld world = plr.getServer().getWorld(DimensionType.byRawId(warp.getDimension()));
        plr.teleport(world, warp.getLocation().x, warp.getLocation().y, warp.getLocation().z, warp.getYaw(), warp.getPitch());
    }
}