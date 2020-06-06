package net.lmvdz.delirium.warp;

import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * WarpManager
 */
public class WarpManager {

    private HashMap<UUID, Warp> warps;

    public WarpManager() {
        this.warps = new HashMap<>();
    }

    public boolean saveAll() {
        return false;
    }

    public HashMap<UUID, Warp> getWarps() {
        return this.warps;
    }

    public ArrayList<Warp> getWarpsByCreator(UUID creator) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.values().forEach(warp -> {
            if (warp.getCreator().equals(creator)) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }

    public ArrayList<Warp> getWarpsByDimension(DimensionType dimension) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.values().forEach(warp -> {
            if (warp.getDimension() == dimension) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }

    public ArrayList<Warp> getWarpsByName(String name) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.values().forEach(warp -> {
            if (warp.getName().contains(name)) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }
}