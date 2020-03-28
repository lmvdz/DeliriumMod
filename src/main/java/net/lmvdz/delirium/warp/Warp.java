package net.lmvdz.delirium.warp;

import java.util.UUID;
import net.lmvdz.delirium.DeliriumMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

/**
 * Warp
 */
public class Warp {

    private int dimension;
    private String name;
    private Vec3d location;
    private float yaw;
    private float pitch;
    private UUID creator;
    private float cost;

    public Warp(ServerPlayerEntity player, String name, float cost) {
        this.dimension = player.world.getDimension().getType().getRawId();
        this.name = name;
        this.location = player.getPosVector();
        this.yaw = player.yaw;
        this.pitch = player.pitch;
        this.creator = player.getUuid();
        this.cost = cost;
        DeliriumMod.WarpManager.addWarp(this);
    }

    public Warp(ServerPlayerEntity player) {
        new Warp(player, player.getName().asString()+"-warp-"+DeliriumMod.WarpManager.getWarpsByCreator(player.getUuid()).size()+1);
    }

    public Warp(ServerPlayerEntity player, String name) {
        new Warp(player, name, 0);
    }

    public Warp(Warp warp) {
        this.dimension = warp.getDimension();
        this.name = warp.getName();
        this.location = warp.getLocation();
        this.yaw = warp.getYaw();
        this.pitch = warp.getPitch();
        this.creator = warp.getCreator();
        this.cost = warp.getCost();
    }

    public static Warp copy(Warp warp) {
        return new Warp(warp);
    }

    public int getDimension() {
        return this.dimension;
    }
    
    public void setDimension(int id) {
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