package net.eveld.currendcy.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class DisplayEntityRenderer<T extends DisplayEntity> implements BlockEntityRenderer<T> {
  
  public DisplayEntityRenderer(BlockEntityRendererFactory.Context ctx) {
  }

  @Override
  public void render(DisplayEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    Direction direction = blockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);
    matrices.push();

    float xTranslate = 0.0F;
    float yTranslate = 0.7F;
    float zTranslate = 0.0F;

    float scale = 0.4F;
    float speed = 4;

    switch (direction) {
      case UP:
        break;
      case DOWN:
        break;
      case NORTH:
        xTranslate = 0.5F;
        zTranslate = 0.65F;
        break;
      case SOUTH:
        xTranslate = 0.5F;
        zTranslate = 0.4F;
        break;
      case EAST:
        xTranslate = 0.3F;
        zTranslate = 0.5F;
        break;
      case WEST:
        xTranslate = 0.6F;
        zTranslate = 0.5F;
        break;
    }

    matrices.translate(xTranslate, yTranslate, zTranslate);
    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * speed));
    matrices.scale(scale, scale, scale);
    
    MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItem(), ModelTransformation.Mode.GUI, light, overlay, matrices, vertexConsumers, 0);

    matrices.pop();
  }
}