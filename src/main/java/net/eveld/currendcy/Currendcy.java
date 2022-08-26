package net.eveld.currendcy;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.eveld.currendcy.block.Collector;
import net.eveld.currendcy.block.Dispenser;
import net.eveld.currendcy.block.Display;
import net.eveld.currendcy.block.Vault;
import net.eveld.currendcy.block.entity.CollectorEntity;
import net.eveld.currendcy.block.entity.DispenserEntity;
import net.eveld.currendcy.block.entity.DisplayEntity;
import net.eveld.currendcy.block.entity.VaultEntity;
import net.eveld.currendcy.gui.CollectorScreenHandler;
import net.eveld.currendcy.item.Book;
import net.eveld.currendcy.item.Card;
import net.eveld.currendcy.item.Chip;
import net.eveld.currendcy.utils.Ingredient;
import net.eveld.currendcy.utils.Recipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Currendcy implements ModInitializer {
	public static final String MODID = "currendcy";
	public static final String MOD_NAME = "Currendcy";

	// Items
	public static final Identifier BOOK_ID = identifier("guide_book");
	public static JsonObject BOOK_RECIPE;
	public static Item BOOK;

	public static final Identifier CARD_ID = identifier("card");
	public static JsonObject CARD_RECIPE;
	public static Item CARD;

	public static final Identifier CHIP_ID = identifier("chip");
	public static JsonObject CHIP_RECIPE;
	public static Item CHIP;

	// Blocks
	public static final Identifier COLLECTOR_ID = identifier("collector");
	public static JsonObject COLLECTOR_RECIPE;
	public static final Collector COLLECTOR = new Collector(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<CollectorEntity> COLLECTOR_ENTITY;

	public static final Identifier VAULT_ID = identifier("vault");
	public static JsonObject VAULT_RECIPE;
	public static final Vault VAULT = new Vault(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
	public static BlockEntityType<VaultEntity> VAULT_ENTITY;

	public static final Identifier DISPLAY_ID = identifier("display");
	public static JsonObject DISPLAY_RECIPE;
	public static final Display DISPLAY = new Display(FabricBlockSettings.of(Material.GLASS).hardness(4.0f));
	public static BlockEntityType<DisplayEntity> DISPLAY_ENTITY;

	public static final Identifier DISPENSER_ID = identifier("dispenser");
	public static JsonObject DISPENSER_RECIPE;
	public static final Dispenser DISPENSER = new Dispenser(FabricBlockSettings.of(Material.GLASS).hardness(4.0f));
	public static BlockEntityType<DispenserEntity> DISPENSER_ENTITY;

	// Item group
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(identifier("general"), () -> new ItemStack(BOOK));

	// GUI
	public static ScreenHandlerType<CollectorScreenHandler> COLLECTOR_SCREEN_HANDLER;

	// Sound
	public static final Identifier displayClickSoundID = identifier("display_click");
	public static SoundEvent DISPLAY_CLICK_SOUND = new SoundEvent(displayClickSoundID);
	public static final Identifier collectorActiveSoundID = identifier("collector_active");
	public static SoundEvent COLLECTOR_ACTIVE_SOUND = new SoundEvent(collectorActiveSoundID);

	@Override
	public void onInitialize() {
		BOOK = Registry.register(Registry.ITEM, BOOK_ID, new Book(new Item.Settings().group(ITEM_GROUP)));
		BOOK_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("E", new Identifier("ender_eye"), "item"),
				new Ingredient("B", new Identifier("book"), "item")
			),
			Lists.newArrayList(
				"EB ",
				"   ",
				"   "
			),  
			BOOK_ID
		).JSON();

		CHIP = Registry.register(Registry.ITEM, CHIP_ID, new Chip(new Item.Settings().group(ITEM_GROUP)));
		CHIP_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("G", new Identifier("gold_ingot"), "item"),
				new Ingredient("R", new Identifier("redstone"), "item"),
				new Ingredient("E", new Identifier("ender_eye"), "item"),
				new Ingredient("Q", new Identifier("quartz"), "item")
			),
			Lists.newArrayList(
				"GRG",
				"RER",
				"GQG"
			), 
			CHIP_ID
		).JSON();

		CARD = Registry.register(Registry.ITEM, CARD_ID, new Card(new Item.Settings().group(ITEM_GROUP)));
		CARD_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("G", new Identifier("glass"), "item"),
				new Ingredient("R", new Identifier("redstone"), "item"),
				new Ingredient("E", new Identifier("ender_chest"), "item"),
				new Ingredient("I", new Identifier("iron_ingot"), "item"),
				new Ingredient("C", CHIP_ID, "item")
			),
			Lists.newArrayList(
				"GRG",
				"RCR",
				"IEI"
			), 
			CARD_ID
		).JSON();

		Registry.register(Registry.BLOCK, VAULT_ID, VAULT);
		Registry.register(Registry.ITEM, VAULT_ID, new BlockItem(VAULT, new Item.Settings().group(ITEM_GROUP)));
		VAULT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, VAULT_ID, FabricBlockEntityTypeBuilder.create(VaultEntity::new, VAULT).build(null));
		VAULT_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("G", new Identifier("glass"), "item"),
				new Ingredient("A", new Identifier("respawn_anchor"), "item"),
				new Ingredient("C", CHIP_ID, "item"),
				new Ingredient("E", new Identifier("ender_chest"), "item"),
				new Ingredient("I", new Identifier("iron_ingot"), "item")
			),
			Lists.newArrayList(
				"GAG",
				"ICI",
				"IEI"
			), 
			VAULT_ID
		).JSON();

		Registry.register(Registry.BLOCK, COLLECTOR_ID, COLLECTOR);
		Registry.register(Registry.ITEM, COLLECTOR_ID, new BlockItem(COLLECTOR, new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.SOUND_EVENT, collectorActiveSoundID, COLLECTOR_ACTIVE_SOUND);
		COLLECTOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, COLLECTOR_ID, FabricBlockEntityTypeBuilder.create(CollectorEntity::new, COLLECTOR).build(null));
		COLLECTOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(COLLECTOR_ID, CollectorScreenHandler::new);
		COLLECTOR_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("G", new Identifier("glass"), "item"),
				new Ingredient("A", new Identifier("respawn_anchor"), "item"),
				new Ingredient("C", CHIP_ID, "item"),
				new Ingredient("E", new Identifier("ender_chest"), "item"),
				new Ingredient("I", new Identifier("iron_ingot"), "item")
			),
			Lists.newArrayList(
				"GAG",
				"ICI",
				"IEI"
			), 
			COLLECTOR_ID
		).JSON();

		Registry.register(Registry.BLOCK, DISPLAY_ID, DISPLAY);
		Registry.register(Registry.ITEM, DISPLAY_ID, new BlockItem(DISPLAY, new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.SOUND_EVENT, displayClickSoundID, DISPLAY_CLICK_SOUND);
		DISPLAY_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, DISPLAY_ID, FabricBlockEntityTypeBuilder.create(DisplayEntity::new, DISPLAY).build(null));
		DISPLAY_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("G", new Identifier("glass"), "item"),
				new Ingredient("C", CHIP_ID, "item"),
				new Ingredient("E", new Identifier("ender_chest"), "item"),
				new Ingredient("I", new Identifier("iron_ingot"), "item")
			),
			Lists.newArrayList(
				"GGG",
				"ICI",
				"IEI"
			), 
			DISPLAY_ID
		).JSON();

		Registry.register(Registry.BLOCK, DISPENSER_ID, DISPENSER);
		Registry.register(Registry.ITEM, DISPENSER_ID, new BlockItem(DISPENSER, new Item.Settings().group(ITEM_GROUP)));
		DISPENSER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, DISPENSER_ID, FabricBlockEntityTypeBuilder.create(DispenserEntity::new, DISPENSER).build(null));
		DISPENSER_RECIPE = new Recipe(
			Lists.newArrayList(
				new Ingredient("H", new Identifier("hopper"), "item"),
				new Ingredient("C", CHIP_ID, "item"),
				new Ingredient("D", new Identifier("dispenser"), "item"),
				new Ingredient("I", new Identifier("iron_ingot"), "item")
			),
			Lists.newArrayList(
				"IHI",
				"ICI",
				"IDI"
			), 
			DISPENSER_ID
		).JSON();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MODID, path);
	}
	
}
