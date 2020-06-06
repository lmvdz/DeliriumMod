package net.lmvdz.delirium.portal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// Immersive Portals Placeholder
public class Portal extends Entity {
    public Object specificPlayerId;
    public Vec3d destination;

    public Portal(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {

    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return null;
    }

    protected void setInteractable(boolean b) {

    }

    protected void updateCache() {

    }

    public Vec3d getNormal() {
        return new Vec3d(0, 0,0);
    }
}
