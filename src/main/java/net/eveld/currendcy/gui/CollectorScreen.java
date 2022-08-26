package net.eveld.currendcy.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.Collector;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CollectorScreen extends HandledScreen<ScreenHandler> {
  private static final Identifier TEXTURE = new Identifier(Currendcy.MODID, "textures/gui/container/collector.png");
  CollectorScreenHandler screenHandler;

  public CollectorScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
    screenHandler = (CollectorScreenHandler) handler;
  }

  @Override
  protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, TEXTURE);
    int x = (width - backgroundWidth) / 2;
    int y = (height - backgroundHeight) / 2;
    drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

    // Draw progress bar.
    int l = screenHandler.getProgress();
    drawTexture(matrices, x + 89, y + 35, 176, 0, l, 16);

    // Draw vortex.
    if(!screenHandler.getCurrentSlot().isEmpty()) {
      int step = (int)screenHandler.getProgress()/5;
      int frame = step % 5;
      drawTexture(matrices, x + 121, y + 14, frame * 33, 166, 33, 61);
    } else {
      drawTexture(matrices, x + 122, y + 16, 176, 16, 33, 61);
    }
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		ItemStack stack = screenHandler.getCurrentSlot();
    textRenderer.draw(
			matrices, 
			stack.getItem().getName().getString() + " (" + stack.getCount() + ") = " + screenHandler.getProgress(), 
			0, 0, 65280
    );

    renderBackground(matrices);
    super.render(matrices, mouseX, mouseY, delta);
    drawMouseoverTooltip(matrices, mouseX, mouseY);
  }

  @Override
  protected void init() {
    super.init();
    titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
  }
}