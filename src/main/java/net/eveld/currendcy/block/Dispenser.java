package net.eveld.currendcy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;

import net.eveld.currendcy.block.entity.DispenserEntity;

public class Dispenser extends BlockWithEntity {
  private static final DispenserBehavior BEHAVIOR = new ItemDispenserBehavior();
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
  
  public Dispenser(Settings settings) {
    super(Settings.of(Material.METAL).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState());
  }

  protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
    return BEHAVIOR;
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new DispenserEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
	}

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }
}