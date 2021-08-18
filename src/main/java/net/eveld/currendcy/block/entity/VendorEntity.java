package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import net.eveld.currendcy.Currendcy;

public class VendorEntity extends BlockEntity {

	public VendorEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VENDOR_ENTITY, pos, state);
	}
}