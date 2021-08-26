package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.parts.TransferInventory;

public class VendorDisplayEntity extends BlockEntity implements TransferInventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

	private ItemStack stack;
  private ArrayList<ItemStack> items = new ArrayList<ItemStack>();

	public VendorDisplayEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VENDOR_DISPLAY_ENTITY, pos, state);

		for (Item item : Item.BLOCK_ITEMS.values()) {
			items.add(new ItemStack(item, 1));
		}

    Random rand = new Random();
		this.inventory.set(0, items.get(rand.nextInt(items.size())));
	}

	public ItemStack getItemStack() {
		return this.getItems().get(0);
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, VendorDisplayEntity entity) {
		// Do something every tick.
	}

	@Override
	public void readNbt(NbtCompound nbt) {
			super.readNbt(nbt);
			Inventories.readNbt(nbt, this.inventory);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
			super.writeNbt(nbt);
			Inventories.writeNbt(nbt, this.inventory);
			return nbt;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}
}