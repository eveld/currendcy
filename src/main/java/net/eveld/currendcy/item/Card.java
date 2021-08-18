package net.eveld.currendcy.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Hand;

public class Card extends Item {
 
  public Card(Settings settings) {
      super(settings);
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
      playerEntity.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
      return TypedActionResult.success(playerEntity.getStackInHand(hand));
  }
}