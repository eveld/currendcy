package net.eveld.currendcy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.entity.VendorProcessorEntity;
import net.eveld.currendcy.item.Card;

public class VendorProcessor extends BlockWithEntity {
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
  public static final BooleanProperty CARD_INSERTED = BooleanProperty.of("card_inserted");
  
  private Card card;

  public VendorProcessor(Settings settings) {
    super(Settings.of(Material.METAL).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState().with(CARD_INSERTED, false));
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (!world.isClient) {
      if (state.get(CARD_INSERTED)) {
        // Give the card to the player
        world.playSound(null, pos, Currendcy.EJECT_CARD_SOUND, SoundCategory.BLOCKS, 1f, 1f);
        player.getInventory().insertStack(new ItemStack(card, 1));

        // Remove the card and set card inserted to false
        world.setBlockState(pos, state.with(CARD_INSERTED, false));
        card = null;
      } else {
        if (player.getMainHandStack().isOf(Currendcy.CARD)) {
          // Take the card from the player
          world.playSound(null, pos, Currendcy.INSERT_CARD_SOUND, SoundCategory.BLOCKS, 0.5f, 1f);
          this.card = (Card) player.getMainHandStack().getItem();
          player.getMainHandStack().decrement(1);

          // Set card inserted to true
          world.setBlockState(pos, state.with(CARD_INSERTED, true));

          player.sendMessage(new LiteralText("Card slapped against machine! Balance = " + card.getBalance()), false);
        } else {
          player.sendMessage(new LiteralText("WTF is this item doing here? GTFO!"), false);
        }
      }
    }

    return ActionResult.SUCCESS;
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new VendorProcessorEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
    stateManager.add(CARD_INSERTED);
	}

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return world.isClient ? null : checkType(type, Currendcy.VENDOR_PROCESSOR_ENTITY, VendorProcessorEntity::tick);
  }
}