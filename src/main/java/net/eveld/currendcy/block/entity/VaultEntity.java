package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.eveld.currendcy.Currendcy;

public class VaultEntity extends BlockEntity {

	public VaultEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VAULT_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, VaultEntity entity) {
		// Do something every tick.
	}
}