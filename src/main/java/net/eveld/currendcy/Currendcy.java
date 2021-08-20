package net.eveld.currendcy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.eveld.currendcy.item.Card;
import net.eveld.currendcy.block.Vault;
import net.eveld.currendcy.block.entity.VaultEntity;
import net.eveld.currendcy.block.Collector;
import net.eveld.currendcy.block.entity.CollectorEntity;
import net.eveld.currendcy.block.VendorProcessor;
import net.eveld.currendcy.block.entity.VendorProcessorEntity;
import net.eveld.currendcy.block.VendorDisplay;
import net.eveld.currendcy.block.entity.VendorDisplayEntity;
import net.eveld.currendcy.gui.CollectorScreenHandler;

public class Currendcy implements ModInitializer {
	public static final String MODID = "currendcy";
	public static final String MOD_NAME = "Currendcy";

	// Items
	public static Item CARD;

	// Blocks
	public static final Vault VAULT = new Vault(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<VaultEntity> VAULT_ENTITY;

	public static final Collector COLLECTOR = new Collector(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<CollectorEntity> COLLECTOR_ENTITY;

	public static final VendorProcessor VENDOR_PROCESSOR = new VendorProcessor(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<VendorProcessorEntity> VENDOR_PROCESSOR_ENTITY;

	public static final VendorDisplay VENDOR_DISPLAY = new VendorDisplay(FabricBlockSettings.of(Material.GLASS).hardness(4.0f));
	public static BlockEntityType<VendorDisplayEntity> VENDOR_DISPLAY_ENTITY;

	// Item group
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "general"), () -> new ItemStack(VAULT));

	// GUI
	public static ScreenHandlerType<CollectorScreenHandler> COLLECTOR_SCREEN_HANDLER;

	// Sound
	public static final Identifier insertCardSoundID = new Identifier(MODID, "insert_card");
	public static SoundEvent INSERT_CARD_SOUND = new SoundEvent(insertCardSoundID);

	public static final Identifier ejectCardSoundID = new Identifier(MODID, "eject_card");
	public static SoundEvent EJECT_CARD_SOUND = new SoundEvent(ejectCardSoundID);

	public static final Identifier clickDisplaySoundID = new Identifier(MODID, "click_display");
	public static SoundEvent CLICK_DISPLAY_SOUND = new SoundEvent(clickDisplaySoundID);

	@Override
	public void onInitialize() {
		Identifier cardID = new Identifier(MODID, "card");
		CARD = Registry.register(Registry.ITEM, cardID, new Card(new Item.Settings().group(ITEM_GROUP)));

		Identifier vaultID = new Identifier(MODID, "vault");
		Registry.register(Registry.BLOCK, vaultID, VAULT);
		Registry.register(Registry.ITEM, vaultID, new BlockItem(VAULT, new Item.Settings().group(ITEM_GROUP)));
		VAULT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, vaultID, FabricBlockEntityTypeBuilder.create(VaultEntity::new, VAULT).build(null));

		Identifier collectorID = new Identifier(MODID, "collector");
		Registry.register(Registry.BLOCK, collectorID, COLLECTOR);
		Registry.register(Registry.ITEM, collectorID, new BlockItem(COLLECTOR, new Item.Settings().group(ITEM_GROUP)));
		COLLECTOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, collectorID, FabricBlockEntityTypeBuilder.create(CollectorEntity::new, COLLECTOR).build(null));
		COLLECTOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(collectorID, CollectorScreenHandler::new);

		Identifier vendorProcessorID = new Identifier(MODID, "vendor_processor");
		Registry.register(Registry.BLOCK, vendorProcessorID, VENDOR_PROCESSOR);
		Registry.register(Registry.ITEM, vendorProcessorID, new BlockItem(VENDOR_PROCESSOR, new Item.Settings().group(ITEM_GROUP)));
		VENDOR_PROCESSOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, vendorProcessorID, FabricBlockEntityTypeBuilder.create(VendorProcessorEntity::new, VENDOR_PROCESSOR).build(null));

		Identifier vendorDisplayID = new Identifier(MODID, "vendor_display");
		Registry.register(Registry.BLOCK, vendorDisplayID, VENDOR_DISPLAY);
		Registry.register(Registry.ITEM, vendorDisplayID, new BlockItem(VENDOR_DISPLAY, new Item.Settings().group(ITEM_GROUP)));
		VENDOR_DISPLAY_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, vendorDisplayID, FabricBlockEntityTypeBuilder.create(VendorDisplayEntity::new, VENDOR_DISPLAY).build(null));

		Registry.register(Registry.SOUND_EVENT, insertCardSoundID, INSERT_CARD_SOUND);
		Registry.register(Registry.SOUND_EVENT, ejectCardSoundID, EJECT_CARD_SOUND);
		Registry.register(Registry.SOUND_EVENT, clickDisplaySoundID, CLICK_DISPLAY_SOUND);

		// COLLECTOR_ENTITY = registerBlock(new Identifier(MODID, "collector"), COLLECTOR, CollectorEntity::new);
	}

	// @SuppressWarnings("unchecked")
	// private static <T extends BlockEntity> BlockEntityType<T> registerBlock(Block block, Identifier id, Supplier<BlockEntity> entity) {
	// 	Registry.register(Registry.BLOCK, id, block);
	// 	Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ITEM_GROUP)));
	// 	return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(CollectorEntity::new, block).build(null));
	// }
}
