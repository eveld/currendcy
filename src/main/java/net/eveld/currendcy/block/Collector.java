package net.eveld.currendcy.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;

import java.util.Random;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.entity.CollectorEntity;

public class Collector extends BlockWithEntity {
  private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   
  public static final int transferSpeed = 24;
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
  public static final BooleanProperty PROCESSING = Properties.POWERED;

  public Collector(Settings settings) {
    super(settings);
    setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(PROCESSING, false));
  }

  // Eat all items dropped in it.
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof CollectorEntity) {
      CollectorEntity.onEntityCollided(world, pos, state, entity, (CollectorEntity)blockEntity);
    }
  }

  // Display particles and play sound when PROCESSING.
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    if ((Boolean)state.get(PROCESSING)) {
      if (random.nextInt(50) == 0) {
        world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
      }

      for(int i = 0; i < 4; ++i) {
          double x = (double)pos.getX();
          double y = (double)pos.getY();
          double z = (double)pos.getZ();
          double vx = ((double)random.nextFloat() - 0.5D) * 0.5D;
          double vy = ((double)random.nextFloat() - 0.5D) * 0.5D;
          double vz = ((double)random.nextFloat() - 0.5D) * 0.5D;
          x = x + 0.5D;
          y = y + 0.5D;
          z = z + 0.5D;

          world.addParticle(ParticleTypes.PORTAL, x, y, z, vx, vy, vz);
      }
    }
  }

  // On use show transfer screen.
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (!world.isClient) {
        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

        if (screenHandlerFactory != null) {
          player.openHandledScreen(screenHandlerFactory);
        }
    }
    return ActionResult.SUCCESS;
  }

  // On destroy scatter all containing items.
  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity entity = world.getBlockEntity(pos);
      if (entity instanceof CollectorEntity) {
        ItemScatterer.spawn(world, pos, (CollectorEntity)entity);
        world.updateComparators(pos,this);
      }
      super.onStateReplaced(state, world, pos, newState, moved);
    }
  }

  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);
  }

  public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
    return RAYCAST_SHAPE;
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new CollectorEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
    builder.add(PROCESSING);
	}

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return world.isClient ? null : checkType(type, Currendcy.COLLECTOR_ENTITY, CollectorEntity::tick);
  }

  @Override
  public boolean hasComparatorOutput(BlockState state) {
    return true;
  }

  @Override
  public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
  }
}