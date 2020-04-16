package com.meneghin.morefood.init;

import com.meneghin.morefood.MoreFood;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import java.util.function.Supplier;

public class ModItemGroups {

	public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroup(MoreFood.MOD_ID, () -> new ItemStack(ModItems.CARDBOARD));


	public static class ModItemGroup extends ItemGroup {

		private final Supplier<ItemStack> itemSupplier;

		public ModItemGroup(final String label, final Supplier<ItemStack> itemSupplier) {
			super(label);
			this.itemSupplier = itemSupplier;
		}

		@Override
		public ItemStack createIcon() {
			return itemSupplier.get();
		}
	}
}
