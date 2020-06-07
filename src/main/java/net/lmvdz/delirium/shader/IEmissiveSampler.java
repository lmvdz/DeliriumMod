package net.lmvdz.delirium.shader;

import net.minecraft.client.util.SpriteIdentifier;

public interface IEmissiveSampler {
    boolean isEmmisive();
    SpriteIdentifier getEmmisiveIdentifier();
}
