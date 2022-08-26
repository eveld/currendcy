package net.eveld.currendcy.block;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.entity.DispenserEntity;
import net.eveld.currendcy.block.entity.DisplayEntity;
import net.eveld.currendcy.block.entity.DisplayEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.world.World;

public class Display extends BlockWithEntity {
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
  
  public Display(Settings settings) {
    super(Settings.of(Material.METAL).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState());
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (world.isClient) return ActionResult.SUCCESS;

    ItemStack stack = player.getStackInHand(hand);
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof DisplayEntity) {
      DisplayEntity displayEntity = (DisplayEntity)blockEntity;

      if (stack.isOf(Currendcy.CARD)) {
        BlockPos checkPos = pos.offset(Direction.DOWN);
        BlockEntity foundBlock = world.getBlockEntity(checkPos);

        if (foundBlock instanceof DispenserEntity) {
          DispenserEntity dispenser = (DispenserEntity)foundBlock;
          BlockPointerImpl pointer = new BlockPointerImpl(MinecraftClient.getInstance().getServer().getOverworld(), checkPos);
          Direction direction = dispenser.getCachedState().get(FACING);
          double x = pointer.getX() + 0.7D * (double)direction.getOffsetX();
          double y = pointer.getY() + 0.7D * (double)direction.getOffsetY();
          double z = pointer.getZ() + 0.7D * (double)direction.getOffsetZ();

          ItemStack dispensedStack = displayEntity.removeItem(1);
          dispenser.dispense(world, dispensedStack, 1, direction, new PositionImpl(x, y, z));
          world.playSound(null, pos, Currendcy.DISPLAY_CLICK_SOUND, SoundCategory.BLOCKS, 0.5f, 1f);
        }
      } else if (!stack.isEmpty()){
        displayEntity.setItem(stack.copy());
        stack.setCount(0);
        world.playSound(null, pos, Currendcy.DISPLAY_CLICK_SOUND, SoundCategory.BLOCKS, 0.5f, 1f);
      }
    }
    return ActionResult.SUCCESS;
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new DisplayEntity(pos, state);
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

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return world.isClient ? null : checkType(type, Currendcy.DISPLAY_ENTITY, DisplayEntity::tick);
  }
}