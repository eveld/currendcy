package net.eveld.currendcy.block.entity;

import java.util.HashMap;
import java.util.Map.Entry;

import net.eveld.currendcy.Currendcy;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultEntity extends BlockEntity implements BlockEntityClientSerializable  {
	private HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
	
	public VaultEntity(BlockPos pos, BlockState state) {
		super(Currendcy.VAULT_ENTITY, pos, state);
	}

	public void addItem(ItemStack stack) {
		Item item = stack.getItem();
		int id = Item.getRawId(item);
		int currentCount = 0;
		if (items.containsKey(id)) {
			currentCount = items.get(id);
		}
		items.put(id, currentCount + stack.getCount());

		System.out.println(items);
		this.markDirty();
	}
	
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("items", 9)) {
			NbtList list = nbt.getList("items", 10);
			for (int i = 0; i < list.size(); i++) {
				NbtCompound item = list.getCompound(i);

				int id = item.getInt("id");
				int count = item.getInt("count");

				this.items.put(id, count);
			}
	 	}
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		NbtList list = new NbtList();

		for (Entry<Integer,Integer> entry : items.entrySet()) {
			NbtCompound item = new NbtCompound();
			item.putInt("id", entry.getKey());
			item.putInt("count", entry.getValue());
			list.add(item);
		}

		nbt.put("items", list);
		
		return nbt;
	}

	@Override
	public void fromClientTag(NbtCompound nbt) {
	}

	@Override
	public NbtCompound toClientTag(NbtCompound nbt) {
		return nbt;
	}

	
	public static void tick(World world, BlockPos blockPos, BlockState state, DisplayEntity entity) {
		// Do something every tick.
	}
}