package net.eveld.currendcy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

import net.eveld.currendcy.block.entity.CollectorEntity;

public class Collector extends HorizontalFacingBlock implements BlockEntityProvider {
  public Collector(Settings settings) {
    super(settings);
    setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (!world.isClient) {
        player.sendMessage(new LiteralText("Show collector GUI"), false);
    }

    return ActionResult.SUCCESS;
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new CollectorEntity(pos, state);
  }

  @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(Properties.HORIZONTAL_FACING);
	}
}