package net.eveld.currendcy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import net.eveld.currendcy.Currendcy;

public class VendorDisplayEntity extends BlockEntity {
	private ItemStack stack;
  private ArrayList<ItemStack> items = new ArrayList<ItemStack>();

	public VendorDisplayEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VENDOR_DISPLAY_ENTITY, pos, state);

		items.add(new ItemStack(Items.JUKEBOX, 1));
    items.add(new ItemStack(Items.COBBLESTONE, 1));
    items.add(new ItemStack(Items.DIAMOND, 1));
    items.add(new ItemStack(Items.GOLDEN_AXE, 1));
    items.add(new ItemStack(Items.ACACIA_BOAT, 1));
		items.add(new ItemStack(Items.BEEHIVE, 1));
		items.add(new ItemStack(Items.CARROT, 1));
		items.add(new ItemStack(Items.DANDELION, 1));
		items.add(new ItemStack(Items.EMERALD, 1));
		items.add(new ItemStack(Items.FILLED_MAP, 1));
		items.add(new ItemStack(Items.GLOW_BERRIES, 1));

    Random rand = new Random();
    stack = items.get(rand.nextInt(items.size()));
	}

	public ItemStack getItem() {
		return this.stack;
	}

	public static void tick(World world, BlockPos blockPos, BlockState state, VendorDisplayEntity entity) {
		// Do something every tick.
	}
}