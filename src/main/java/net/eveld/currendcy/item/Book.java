package net.eveld.currendcy.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import net.eveld.currendcy.Currendcy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class Book extends Item {
  public Book(Settings settings) {
      super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    if (!world.isClient) {
      PatchouliAPI.get().openBookGUI((ServerPlayerEntity)playerEntity, new Identifier(Currendcy.MODID, "currendcy"));
      return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    return TypedActionResult.consume(playerEntity.getStackInHand(hand));
  }
}