package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.eveld.currendcy.Currendcy;

public class DispenserEntity extends BlockEntity {
	public DispenserEntity(BlockPos pos, BlockState state) {
		super(Currendcy.DISPENSER_ENTITY, pos, state);
	}

	public void dispense(World world, ItemStack stack, int offset, Direction side, Position pos) {
		double d = pos.getX();
		double e = pos.getY();
		double f = pos.getZ();
		if (side.getAxis() == Direction.Axis.Y) {
			 e -= 0.125D;
		} else {
			 e -= 0.15625D;
		}
		
		ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
		double g = world.random.nextDouble() * 0.1D + 0.2D;
		itemEntity.setVelocity(world.random.nextGaussian() * 0.007499999832361937D * (double)offset + (double)side.getOffsetX() * g, world.random.nextGaussian() * 0.007499999832361937D * (double)offset + 0.20000000298023224D, world.random.nextGaussian() * 0.007499999832361937D * (double)offset + (double)side.getOffsetZ() * g);
		world.spawnEntity(itemEntity);
 }

	// public void dispense(VendorDisplayEntity display) {
	// 	BlockPos dispensePos = pos.offset(Direction.DOWN);
	// 	BlockPointerImpl blockPointerImpl = new BlockPointerImpl(world, pos);
	// }
}