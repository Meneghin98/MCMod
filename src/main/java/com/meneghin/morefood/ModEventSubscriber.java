package com.meneghin.morefood;

import com.meneghin.morefood.init.ModItemGroups;
import com.meneghin.morefood.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = MoreFood.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event){
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "cardboard"));
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "cardboard_box_small"));
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "cardboard_box_tall"));
		Food pear = (new Food.Builder()).hunger(4).saturation(0.7f).build();
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).food(pear)), "pear"));
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "pear_sapling"));
		event.getRegistry().register(setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "apple_sapling"));
		event.getRegistry().register(setup(new BlockItem(ModItems.PEAR_LOG, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "pear_log"));
	}

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(setup(new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).sound(SoundType.WOOD)),"pear_log"));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(MoreFood.MOD_ID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
