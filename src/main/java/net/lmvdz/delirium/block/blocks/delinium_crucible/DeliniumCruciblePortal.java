package net.lmvdz.delirium.block.blocks.delinium_crucible;

public class DeliniumCruciblePortal extends DeliniumCrucible {

    public static DeliniumCruciblePortal DELINIUM_CRUCIBLE_PORTAL_BLOCK;

    public DeliniumCruciblePortal() {

        if (DELINIUM_CRUCIBLE_PORTAL_BLOCK == null) {
            DELINIUM_CRUCIBLE_PORTAL_BLOCK = this;
            registerDeliriumBlock(DELINIUM_CRUCIBLE_PORTAL_BLOCK);
        }
    }

}