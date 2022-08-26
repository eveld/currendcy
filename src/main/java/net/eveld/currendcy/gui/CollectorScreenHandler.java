package net.eveld.currendcy.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.eveld.currendcy.Currendcy;

public class CollectorScreenHandler extends ScreenHandler {
  private final Inventory inventory;
  PropertyDelegate progressDelegate;
  PropertyDelegate currentSlotDelegate;

  public CollectorScreenHandler(int syncId, PlayerInventory playerInventory) {
    this(syncId, playerInventory, new SimpleInventory(9), new ArrayPropertyDelegate(1), new ArrayPropertyDelegate(1));
  }

  public CollectorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate progressDelegate, PropertyDelegate currentSlotDelegate) {
    super(Currendcy.COLLECTOR_SCREEN_HANDLER, syncId);
    checkSize(inventory, 9);
    this.inventory = inventory;
    this.progressDelegate = progressDelegate;
    this.currentSlotDelegate = currentSlotDelegate;

    //some inventories do custom logic when a player opens it.
    inventory.onOpen(playerInventory.player);
    
    // tell the screenhandler about the delegate, so it will sync the data inside.
    this.addProperties(progressDelegate);
    this.addProperties(currentSlotDelegate);

    int r;
    int c;
    int i = 0;

    //Our inventory
    for (r = 0; r < 3; ++r) {
      for (c = 0; c < 3; ++c) {
        this.addSlot(new Slot(inventory, i, 30 + (c * 18), 17 + r * 18));
        i++;
      }
    }

    //The player inventory
    for (r = 0; r < 3; ++r) {
      for (c = 0; c < 9; ++c) {
        this.addSlot(new Slot(playerInventory, i, 8 + c * 18, 84 + r * 18));
        i++;
      }
    }
    //The player Hotbar
    for (c = 0; c < 9; ++c) {
      this.addSlot(new Slot(playerInventory, c, 8 + c * 18, 142));
    }
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return this.inventory.canPlayerUse(player);
  }

  public int getProgress(){
    return progressDelegate.get(0);
  }

  public ItemStack getCurrentSlot(){
    return this.inventory.getStack(currentSlotDelegate.get(0));
  }

  @Override
  public ItemStack transferSlot(PlayerEntity player, int invSlot) {
    ItemStack newStack = ItemStack.EMPTY;
    Slot slot = this.slots.get(invSlot);
    if (slot != null && slot.hasStack()) {
      ItemStack originalStack = slot.getStack();
      newStack = originalStack.copy();
      if (invSlot < this.inventory.size()) {
        if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
        return ItemStack.EMPTY;
      }

      if (originalStack.isEmpty()) {
        slot.setStack(ItemStack.EMPTY);
      } else {
        slot.markDirty();
      }
    }

    return newStack;
  }
}
