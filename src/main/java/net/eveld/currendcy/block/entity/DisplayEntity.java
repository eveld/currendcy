package net.eveld.currendcy.block.entity;

import net.eveld.currendcy.Currendcy;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisplayEntity extends BlockEntity implements BlockEntityClientSerializable  {
	ItemStack item;

	public DisplayEntity(BlockPos pos, BlockState state) {
		super(Currendcy.DISPLAY_ENTITY, pos, state);
		this.item = ItemStack.EMPTY;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public boolean hasItem() {
		return !this.item.isOf(Items.AIR);
 }

	public void setItem(ItemStack item) {
		this.item = item;
		this.markDirty();
		this.sync();
	}

	public ItemStack removeItem(int amount) {
		ItemStack result = this.item.split(amount);
		if (this.item.isEmpty()) {
			this.item = ItemStack.EMPTY;
		}
		this.markDirty();
		this.sync();
		return result;
	}
	
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("item", 10)) {
			 this.item = ItemStack.fromNbt(nbt.getCompound("item"));
		} else {
			 this.item = ItemStack.EMPTY;
		}
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (this.hasItem()) {
			nbt.put("item", this.getItem().writeNbt(new NbtCompound()));
		}

		return nbt;
	}

	@Override
	public void fromClientTag(NbtCompound nbt) {
		if (nbt.contains("item", 10)) {
			 this.item = ItemStack.fromNbt(nbt.getCompound("item"));
		} else {
			 this.item = ItemStack.EMPTY;
		}
	}

	@Override
	public NbtCompound toClientTag(NbtCompound nbt) {
		if (!this.getItem().isEmpty()) {
			nbt.put("item", this.getItem().writeNbt(new NbtCompound()));
		}

		return nbt;
	}

	
	public static void tick(World world, BlockPos blockPos, BlockState state, DisplayEntity entity) {
		// Do something every tick.
	}
}