package net.lmvdz.delirium.block.blocks.delinium_crucible;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DeliniumCrucibleContainerScreen extends GenericContainerScreen {
    private final int rows;
    private PlayerEntity player;
    private final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");


    public DeliniumCrucibleContainerScreen(DeliniumCrucibleContainer container, PlayerEntity player, Text title) {
        super(container, player.inventory, title);
        this.player = player;
        this.rows = handler.getRows();
        this.backgroundHeight = 114 + 6 * 18;
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        this.drawTexture(matrices, i, j + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        client.textRenderer.draw(matrices, this.title.asString(), 8.0F, 6.0F, 4210752);
        client.textRenderer.draw(matrices, this.player.getDisplayName().asString() + " " + this.playerInventory.getDisplayName().asString(), 8.0F, (float)(this.height - 96 + 2), 0xeeeeee);
    }
    
}