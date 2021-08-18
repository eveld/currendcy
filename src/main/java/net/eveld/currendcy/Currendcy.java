package net.eveld.currendcy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.eveld.currendcy.item.Card;
import net.eveld.currendcy.block.Vault;
import net.eveld.currendcy.block.entity.VaultEntity;
import net.eveld.currendcy.block.Collector;
import net.eveld.currendcy.block.entity.CollectorEntity;
import net.eveld.currendcy.block.Vendor;
import net.eveld.currendcy.block.entity.VendorEntity;

public class Currendcy implements ModInitializer {
	public static final String MODID = "currendcy";
	public static final String MOD_NAME = "Currendcy";

	// Items
	public static Item FABRIC_ITEM;

	// Blocks
	public static final Vault VAULT = new Vault(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<VaultEntity> VAULT_ENTITY;

	public static final Collector COLLECTOR = new Collector(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<CollectorEntity> COLLECTOR_ENTITY;

	public static final Vendor VENDOR = new Vendor(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<VendorEntity> VENDOR_ENTITY;
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "general"), () -> new ItemStack(VAULT));

	@Override
	public void onInitialize() {
		Identifier cardID = new Identifier(MODID, "card");
		
		FABRIC_ITEM = Registry.register(Registry.ITEM, cardID, new Card(new Item.Settings().group(ITEM_GROUP)));

		Identifier vaultID = new Identifier(MODID, "vault");
		Registry.register(Registry.BLOCK, vaultID, VAULT);
		Registry.register(Registry.ITEM, vaultID, new BlockItem(VAULT, new Item.Settings().group(ITEM_GROUP)));
		VAULT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, vaultID, FabricBlockEntityTypeBuilder.create(VaultEntity::new, VAULT).build(null));

		Identifier collectorID = new Identifier(MODID, "collector");
		Registry.register(Registry.BLOCK, collectorID, COLLECTOR);
		Registry.register(Registry.ITEM, collectorID, new BlockItem(COLLECTOR, new Item.Settings().group(ITEM_GROUP)));
		COLLECTOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, collectorID, FabricBlockEntityTypeBuilder.create(CollectorEntity::new, COLLECTOR).build(null));

		Identifier vendorID = new Identifier(MODID, "vendor");
		Registry.register(Registry.BLOCK, vendorID, VENDOR);
		Registry.register(Registry.ITEM, vendorID, new BlockItem(VENDOR, new Item.Settings().group(ITEM_GROUP)));
		VENDOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, vendorID, FabricBlockEntityTypeBuilder.create(VendorEntity::new, VENDOR).build(null));

		// COLLECTOR_ENTITY = registerBlock(new Identifier(MODID, "collector"), COLLECTOR, CollectorEntity::new);
	}

	// @SuppressWarnings("unchecked")
	// private static <T extends BlockEntity> BlockEntityType<T> registerBlock(Block block, Identifier id, Supplier<BlockEntity> entity) {
	// 	Registry.register(Registry.BLOCK, id, block);
	// 	Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ITEM_GROUP)));
	// 	return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(CollectorEntity::new, block).build(null));
	// }
}
