package net.eveld.currendcy.block;

import org.jetbrains.annotations.Nullable;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.entity.VaultEntity;
import net.eveld.currendcy.utils.VaultManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class Vault extends BlockWithEntity {
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
  
  public Vault(Settings settings) {
    super(Settings.of(Material.METAL).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState());
  }
  
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new VaultEntity(pos, state);
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
    return world.isClient ? null : checkType(type, Currendcy.DISPLAY_ENTITY, VaultEntity::tick);
  }

  public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof VaultEntity) {
      VaultEntity vault = (VaultEntity)world.getBlockEntity(pos);
      VaultManager.getInstance().removeVault(vault);
    }
    this.spawnBreakParticles(world, player, pos, state);
    world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
  }

  public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof VaultEntity) {
      VaultEntity vault = (VaultEntity)world.getBlockEntity(pos);
      VaultManager.getInstance().addVault(vault);
    }
  }
}