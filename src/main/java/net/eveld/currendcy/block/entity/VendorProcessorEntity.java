package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.eveld.currendcy.Currendcy;

public class VendorProcessorEntity extends BlockEntity {

	public VendorProcessorEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VENDOR_PROCESSOR_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, VendorProcessorEntity entity) {
		// Do something every tick.
	}
}