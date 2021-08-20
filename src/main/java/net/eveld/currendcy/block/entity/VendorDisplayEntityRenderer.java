package net.eveld.currendcy.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
// import net.minecraft.util.collection.DefaultedList;

@Environment(EnvType.CLIENT)
public class VendorDisplayEntityRenderer<T extends VendorDisplayEntity> implements BlockEntityRenderer<T> {
  // private final DefaultedList<ItemStack> slots = DefaultedList.ofSize(3, ItemStack.EMPTY);
  
  
  public VendorDisplayEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    
  }

  @Override
  public void render(VendorDisplayEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    matrices.push();

    // Calculate the current offset in the y value
    double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 10.0;

    // Move the item
    matrices.translate(0.5, 0.4 + offset, 0.5);

    // Rotate the item
    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));

    int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
    MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItem(), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

    matrices.pop();
  }
}