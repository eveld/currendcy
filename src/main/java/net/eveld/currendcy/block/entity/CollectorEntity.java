package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.gui.CollectorScreenHandler;
import net.eveld.currendcy.parts.TransferInventory;

public class CollectorEntity extends BlockEntity implements NamedScreenHandlerFactory, TransferInventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);

	public CollectorEntity(BlockPos pos, BlockState state) {
		super(Currendcy.COLLECTOR_ENTITY, pos, state);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
			return new CollectorScreenHandler(syncId, playerInventory, this);
	}

	@Override
	public Text getDisplayName() {
			return new TranslatableText(getCachedState().getBlock().getTranslationKey());
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

	public static void tick(World world, BlockPos blockPos, BlockState state, CollectorEntity entity) {
		// Do something every tick.
	}
}