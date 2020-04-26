package net.lmvdz.delirium.block.blocks.delinium_crucible;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DeliniumCrucibleScreen extends ContainerScreen<DeliniumCrucibleContainer> {

    PlayerEntity player;
    public DeliniumCrucibleScreen(DeliniumCrucibleContainer container, PlayerEntity player, Text title) {
        super(container, player.inventory, title);
        this.player = player;
        this.containerHeight = 114 + 6 * 18;
    }

    // private static final Identifier TEXTURE = new Identifier(DeliriumMod.MODID, "textures/gui/container/delinium_crucible.png");
    private final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
        this.font.draw(this.player.getDisplayName().asFormattedString() + " " + this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.containerHeight - 96 + 2), 0xeeeeee);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(.1F, .1F, .1F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.containerWidth) / 2;
        int j = (this.height - this.containerHeight) / 2;
        this.blit(i, j, 0, 0, this.containerWidth, 6 * 18 + 17);
        this.blit(i, j + 6 * 18 + 17, 0, 126, this.containerWidth, 96);
        
    }
    
}