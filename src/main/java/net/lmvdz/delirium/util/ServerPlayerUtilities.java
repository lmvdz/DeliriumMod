package net.lmvdz.delirium.util;

import net.lmvdz.delirium.warp.Warp;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

/**
 * ServerPlayerUtilities
 */
public class ServerPlayerUtilities {
    public static void teleport(ServerPlayerEntity plr, Warp warp) {
        ServerWorld world = plr.getServerWorld();
        plr.teleport(world, warp.getLocation().x, warp.getLocation().y, warp.getLocation().z, warp.getYaw(), warp.getPitch());
    }
}