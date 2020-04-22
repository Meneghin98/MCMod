package com.meneghin.morefood;

import com.meneghin.morefood.init.ModItemGroups;
import com.meneghin.morefood.init.ModItems;
import com.meneghin.morefood.item.FuelBlockItem;
import com.meneghin.morefood.trees.MySaplingBlock;
import javafx.util.Pair;
import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = MoreFood.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(setup(new MySaplingBlock(OakTree::new, Block.Properties.from(Blocks.OAK_SAPLING)), "apple_sapling"));

		Set<Pair<String, Block>> blocks = MoreFood.getInstance().getBlocks();
		for(Pair<String, Block> p: blocks){
			registry.register(setup(p.getValue(), p.getKey()));
		}
	}

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(setup(new BlockItem(ModItems.APPLE_SAPLING, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "apple_sapling"));

		Set<Pair<String,Item>> items = MoreFood.getInstance().getItems();
		for (Pair<String, Item> p: items){
			registry.register(setup(p.getValue(), p.getKey()));
		}
	}


	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(MoreFood.MOD_ID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
