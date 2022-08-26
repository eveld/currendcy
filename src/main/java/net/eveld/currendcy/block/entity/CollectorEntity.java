package net.eveld.currendcy.block.entity;

import java.util.ArrayList;

import net.eveld.currendcy.Currendcy;
import net.eveld.currendcy.block.Collector;
import net.eveld.currendcy.gui.CollectorScreenHandler;
import net.eveld.currendcy.utils.TransferInventory;
import net.eveld.currendcy.utils.VaultManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CollectorEntity extends BlockEntity implements NamedScreenHandlerFactory, TransferInventory {
	public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

	private static int progress = 0;
	private static int currentSlot = 0;
	private static VaultEntity vault;

	public CollectorEntity(BlockPos pos, BlockState state) {
		super(Currendcy.COLLECTOR_ENTITY, pos, state);
		progress = 0;
		currentSlot = 0;

		ArrayList<VaultEntity> vaults = VaultManager.getInstance().getVaults();
		vault = vaults.get(0);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	private final PropertyDelegate progressDelegate = new PropertyDelegate(){
		@Override
		public int get(int index) {
			return progress;
		}

		@Override
		public void set(int index, int value) {
			progress = value;
		}

		@Override
		public int size() {
			return 1;
		}
	};

	private final PropertyDelegate currentSlotDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			return currentSlot;
		}

		@Override
		public void set(int index, int value) {
			currentSlot = value;
		}

		@Override
		public int size() {
			return 1;
		}
	};

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

	public int findNonEmptySlot() {
		for (int i = 0; i < this.getItems().size(); i++) {
			if (!this.getStack(i).isEmpty()) {
				return i;
			}
		}
		return 0;
	}

	private static void transferItem(ItemStack stack) {
		vault.addItem(stack);
	}

	// Each tick increase progress of transferring an item.
	public static void tick(World world, BlockPos blockPos, BlockState state, CollectorEntity entity) {
		if(!world.isClient) {
			ItemStack stack = entity.getStack(currentSlot);

			// If the slot we were transferring is now empty.
			if(stack.isEmpty()) {
				progress = 0;
				currentSlot = entity.findNonEmptySlot();
				stack = entity.getStack(currentSlot);
			}
			
			state = (BlockState)state.with(Collector.PROCESSING, !stack.isEmpty());
			world.setBlockState(blockPos, state, Block.NOTIFY_ALL);

			progress ++;
			if(progress >= Collector.transferSpeed) {
				ItemStack transferedStack = stack.split(1);
				progress = 0;

				transferItem(transferedStack);
			}

			
    }
	}

	// If an item touches the collector, consume it.
  public static void onEntityCollided(World world, BlockPos pos, BlockState state, Entity itemEntity, CollectorEntity blockEntity) {
		if (itemEntity instanceof ItemEntity) {
			ItemStack itemStack = ((ItemEntity)itemEntity).getStack().copy();
			System.out.println(blockEntity.getItems());
			for (int i = 0; i < blockEntity.getItems().size(); i++) {
				if (blockEntity.getStack(i).isEmpty()) {
					blockEntity.setStack(i, itemStack);
					itemEntity.discard();
					break;
				}
			}
		}
  }

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new CollectorScreenHandler(syncId, playerInventory, this, this.progressDelegate, this.currentSlotDelegate);
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}
}