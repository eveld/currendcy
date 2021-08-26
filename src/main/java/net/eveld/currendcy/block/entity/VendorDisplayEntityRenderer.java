package net.eveld.currendcy.block.entity;

import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.patchy.BlockedServers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.TextureHelper;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class VendorDisplayEntityRenderer<T extends VendorDisplayEntity> implements BlockEntityRenderer<T> {
  // private final DefaultedList<ItemStack> slots = DefaultedList.ofSize(3, ItemStack.EMPTY);

  
  public VendorDisplayEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    
  }

  @Override
  public void render(VendorDisplayEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    // RenderSystem.enableDepthTest();
    // RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

    // Identifier texture = blockEntity.getTexture();
    // RenderSystem.setShaderTexture(0, texture);
    
    // Tessellator tessellator = Tessellator.getInstance();
    // BufferBuilder bufferBuilder = tessellator.getBuffer();
    Direction direction = blockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);
    
    matrices.push();
    
    // too high
    // WallStandingBlockItem
    // TallBlockItem
    // AliasedBlockItem
    // BannerItem

    // small
    // SkullItem

    // Item item = blockEntity.getItemStack().getItem();
    // String type = item.getClass().getSimpleName().toString();

    // switch(type) {
    //   case "BlockItem":
    //     item.
    //     matrices.translate(0.5F, 0.7F, 0.6F);
    //     matrices.scale(0.3F, 0.3F, 0.3F);

    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.HEAD, light, overlay, matrices, vertexConsumers, 0);
    //     break;

    //   case "WallStandingBlockItem":
    //     matrices.translate(0.5F, 0.4F, 0.4F);
    //     matrices.scale(0.4F, 0.4F, 0.4F);
    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.HEAD, light, overlay, matrices, vertexConsumers, 0);
    //     break;

    //   case "TallBlockItem":
    //     matrices.translate(0.5F, 0.4F, 0.6F);
    //     matrices.scale(0.4F, 0.4F, 0.4F);
    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.HEAD, light, overlay, matrices, vertexConsumers, 0);
    //     break;

    //   case "AliasedBlockItem":
    //     matrices.translate(0.5F, 0.4F, 0.4F);
    //     matrices.scale(0.4F, 0.4F, 0.4F);
    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.HEAD, light, overlay, matrices, vertexConsumers, 0);
    //     break;

    //   case "BannerItem":
    //     matrices.translate(0.5F, 0.55F, 0.6F);
    //     matrices.scale(0.5F, 0.5F, 0.5F);
    //     matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, light, overlay, matrices, vertexConsumers, 0);
    //     break;

    //   default:
    //     matrices.translate(0.5F, 0.7F, 0.6F);
    //     matrices.scale(0.4F, 0.4F, 0.4F);
    //     MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.GUI, light, overlay, matrices, vertexConsumers, 0);
    //     break;
    // }

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

    MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getItemStack(), ModelTransformation.Mode.GUI, light, overlay, matrices, vertexConsumers, 0);

    // float itemSize = 0.4F;

    // float yOffset = -0.2F;
    // float xOffset = 0.0F;
    // float zOffset = 0.0F;

    // float zTranslate = 0.0F;
    // float xTranslate = 0.0F;

    // Quaternion zRotation = Vec3f.POSITIVE_Z.getDegreesQuaternion(0.0F);

    // // move towards the center
    // matrices.translate(xTranslate, 0.65F, zTranslate);

    // // rotate upwards
    // matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));

    // // rotate to face the front
    // matrices.multiply(zRotation);

    // Matrix4f matrix4f = matrices.peek().getModel();

    // bufferBuilder.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
    // bufferBuilder.vertex(matrix4f, -itemSize/2 + xOffset, 0.0F + yOffset, -itemSize/2 + zOffset).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, -itemSize/2 + xOffset, 0.0F + yOffset, itemSize/2 + zOffset).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, itemSize/2 + xOffset, 0.0F + yOffset, itemSize/2 + zOffset).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, itemSize/2 + xOffset, 0.0F + yOffset, -itemSize/2 + zOffset).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
    // tessellator.draw();

    // bufferBuilder.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
    // bufferBuilder.vertex(matrix4f, -itemSize/2 + xOffset, 0.0F + yOffset + 0.2F, -itemSize/2 + zOffset).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, -itemSize/2 + xOffset, 0.0F + yOffset + 0.2F, itemSize/2 + zOffset).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, itemSize/2 + xOffset, 0.0F + yOffset + 0.2F, itemSize/2 + zOffset).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    // bufferBuilder.vertex(matrix4f, itemSize/2 + xOffset, 0.0F + yOffset + 0.2F, -itemSize/2 + zOffset).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
    // tessellator.draw();

    matrices.pop();
  }
}