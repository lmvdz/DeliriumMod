package net.lmvdz.delirium.warp;

import java.util.ArrayList;
import java.util.UUID;

/**
 * WarpManager
 */
public class WarpManager {

    private ArrayList<Warp> warps;

    public WarpManager() {
        this.warps = new ArrayList<>();
    }

    public boolean saveAll() {
        return false;
    }

    public ArrayList<Warp> getWarps() {
        return this.warps;
    }

    public void addWarp(Warp w) {
        this.warps.add(w);
    }

    public ArrayList<Warp> getWarpsByCreator(UUID creator) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.forEach(warp -> {
            if (warp.getCreator().equals(creator)) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }

    public ArrayList<Warp> getWarpsByDimension(int dimension) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.forEach(warp -> {
            if (warp.getDimension() == dimension) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }

    public ArrayList<Warp> getWarpsByName(String name) {
        ArrayList<Warp> subList = new ArrayList<>();
        warps.forEach(warp -> {
            if (warp.getName().contains(name)) {
                subList.add(Warp.copy(warp));
            }
        });
        return subList;
    }
}