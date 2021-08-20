package net.eveld.currendcy.parts;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface StandardInventory extends Inventory {

	DefaultedList<ItemStack> getItems();
	// Inventory

	/**
	 * Returns the inventory size.
	 */
	@Override
	default int size() {
		return getItems().size();
	}

	/**
	 * @return true if this inventory has only empty stacks, false otherwise
	 */
	@Override
	default boolean isEmpty() {
		for (int i = 0; i < size(); i++) {
			ItemStack stack = getStack(i);
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the item in the slot.
	 */
	@Override
	default ItemStack getStack(int slot) {
		return getItems().get(slot);
	}

	/**
	 * Takes a stack of the size from the slot.
	 * <p>(default implementation) If there are less items in the slot than what are requested,
	 * takes all items in that slot.
	 */
	@Override
	default ItemStack removeStack(int slot, int count) {
		ItemStack result = Inventories.splitStack(getItems(), slot, count);
		markDirty();
		return result;
	}

	/**
	 * Removes the current stack in the {@code slot} and returns it.
	 */
	@Override
	default ItemStack removeStack(int slot) {
		markDirty();
		return Inventories.removeStack(getItems(), slot);
	}

	/**
	 * Replaces the current stack in the {@code slot} with the provided stack.
	 * <p>If the stack is too big for this inventory ({@link Inventory#getMaxCountPerStack()}),
	 * it gets resized to this inventory's maximum amount.
	 */
	@Override
	default void setStack(int slot, ItemStack stack) {
		getItems().set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack()) {
			stack.setCount(getMaxCountPerStack());
		}
		markDirty();
	}

	/**
	 * Clears {@linkplain #getItems() the item list}}.
	 */
	@Override
	default void clear() {
		getItems().clear();
	}

	@Override
	default void markDirty() {
		handleChanges();
	}

	default void handleChanges() {

	}

	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	default void dropEverything(World world, BlockPos pos) {
		for (ItemStack is : getItems()) {
			world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), is));
		}
		getItems().clear();
	}
}
