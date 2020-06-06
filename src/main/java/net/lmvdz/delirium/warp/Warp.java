package net.lmvdz.delirium.warp;
import net.lmvdz.delirium.DeliriumMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;
import java.util.UUID;

/**
 * Warp
 */
public class Warp {

    private DimensionType dimension;
    private String name;
    private Vec3d location;
    private float yaw;
    private float pitch;
    private UUID warpUuid;
    private UUID creator;
    private HashMap<UUID, Boolean> whitelist;
    private float cost;

    public Warp(ServerPlayerEntity player, String name, float cost, HashMap<UUID, Boolean> whitelist) {
        this.dimension = player.world.getDimension();
        this.warpUuid = UUID.randomUUID();
        this.name = name;
        this.location = player.getPos();
        this.yaw = player.yaw;
        this.pitch = player.pitch;
        this.creator = player.getUuid();
        this.cost = cost;
        this.whitelist = whitelist;
        whitelist.putIfAbsent(this.creator, true);
        do {
            this.warpUuid = UUID.randomUUID();
        } while (DeliriumMod.WarpManager.getWarps().putIfAbsent(this.warpUuid, this) != this);
    }

    public Warp(ServerPlayerEntity player) {
        new Warp(player, player.getName().asString()+"-warp-"+DeliriumMod.WarpManager.getWarpsByCreator(player.getUuid()).size()+1);
    }

    public Warp(ServerPlayerEntity player, String name) {
        new Warp(player, name, 0, new HashMap<>());
    }

    private Warp(Warp warp) {
        this.dimension = warp.getDimension();
        this.name = warp.getName();
        this.location = warp.getLocation();
        this.yaw = warp.getYaw();
        this.pitch = warp.getPitch();
        this.creator = warp.getCreator();
        this.cost = warp.getCost();
        this.whitelist = warp.getWhitelist();
    }

    public static Warp copy(Warp warp) {
        return new Warp(warp);
    }

    public HashMap<UUID, Boolean> getWhitelist() {
        return this.whitelist;
    }

    public DimensionType getDimension() {
        return this.dimension;
    }
    
    public void setDimension(DimensionType id) {
        this.dimension = id;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vec3d getLocation() {
        return this.location;
    }

    public void setLocation(Vec3d location) {
        this.location = location;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getCost() {
        return this.cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setCreatorFromServerPlayerEntity(ServerPlayerEntity player) {
        this.creator = player.getUuid();
    }

    public void setCreator(UUID creator) {
        this.creator = creator;
    }

    public UUID getCreator() {
        return this.creator;
    }

    public boolean save() {
        return true;
    }
}