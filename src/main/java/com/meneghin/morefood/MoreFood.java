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
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreFood.MOD_ID)
public class MoreFood
{
    public static final String MOD_ID = "bnmf";
    static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private final Set<Pair<String, Item>> items;
    private final Set<Pair<String, Block>> blocks;

    private static MoreFood instance;

    public MoreFood(){
        LOGGER.info("Mod construction started");
        instance = this;
        items = new HashSet<>();
        blocks = new HashSet<>();
        BlockHepler blockHepler = new BlockHepler();
        addItem("cardboard");
        addItem("cardboard_box_small");
        addItem("cardboard_box_tall");

        Food pear = (new Food.Builder()).hunger(4).saturation(0.7f).build();
        addItem("pear", new Item.Properties().food(pear));

        addItem("pear_sapling");

        blockHepler.setBlockClass(LogBlock.class)
                .setBlockParams(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG));
        Block b = blockHepler.getBlock();
        blockHepler.setItemClass(FuelBlockItem.class)
                .setItemParams(b, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP), 300);
        BlockItem i = blockHepler.getItem();
        if (b != null && i != null)
            addBlock("pear_log", b, i);

        blockHepler.setBlockClass(Block.class)
                .setBlockParams(Block.Properties.from(Blocks.OAK_PLANKS));
        b = blockHepler.getBlock();
        blockHepler.setItemParams(b, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP),300);
        i = blockHepler.getItem();
        if (b != null && i != null)
            addBlock("pear_planks", b, i);

        blockHepler.setBlockClass(LeavesBlock.class)
                .setBlockParams(Block.Properties.from(Blocks.OAK_LEAVES));
        b = blockHepler.getBlock();
        blockHepler.setItemClass(BlockItem.class)
                .setItemParams(b, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP));
        i = blockHepler.getItem();
        if (b != null && i != null)
            addBlock("pear_leaves", b, i);

        LOGGER.info("Mod construction ended");
    }

    private void addItem(String name){
        Item item = new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP));
        items.add(new Pair<>(name, item));
        LOGGER.info("Added item "+ name);
    }
    private void addItem(String name, Item.Properties properties){
        Item item = new Item(properties.group(ModItemGroups.MOD_ITEM_GROUP));
        items.add(new Pair<>(name, item));
        LOGGER.info("Added item "+ name);
    }

    private void addBlock(String name, Block block, BlockItem item){
        blocks.add(new Pair<>(name,block));
        items.add(new Pair<>(name, item));
        LOGGER.info("Added Block & item "+ name);
    }


    public static MoreFood getInstance() {
        return instance;
    }

    public Set<Pair<String, Item>> getItems() {
        return items;
    }

    public Set<Pair<String, Block>> getBlocks() {
        return blocks;
    }
}

class BlockHepler{

    private final List<Object> blockParams;
    private final List<Object> itemParams;
    private Class<? extends Block> blockClass;
    private Class<? extends BlockItem> itemClass;

    public BlockHepler() {
        blockParams = new ArrayList<>();
        itemParams = new ArrayList<>();
    }

    <B extends Block, I extends BlockItem> BlockHepler(Class<B> blockClass, Class<I> itemClass){
        blockParams = new ArrayList<>();
        itemParams = new ArrayList<>();
        this.blockClass = blockClass;
        this.itemClass = itemClass;
    }

    public <B extends Block> BlockHepler setBlockClass(Class<B> blockClass) {
        this.blockClass = blockClass;
        return this;
    }

    public <I extends BlockItem> BlockHepler setItemClass(Class<I> itemClass) {
        this.itemClass = itemClass;
        return this;
    }

    BlockHepler setBlockParams(Object... objects){
        blockParams.clear();
        blockParams.addAll(Arrays.asList(objects));
        return this;
    }
    BlockHepler setItemParams(Object... objects){
        itemParams.clear();
        itemParams.addAll(Arrays.asList(objects));
        return this;
    }

    Block getBlock(){
        Constructor<? extends Block> constructor = null;
        List<Class<?>> classes = new ArrayList<>();
        for (Object o : blockParams) {
            classes.add(o.getClass());
        }
        try {
            constructor = blockClass.getDeclaredConstructor(classes.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            MoreFood.LOGGER.error("Block constructor has lifted an exception");
            e.printStackTrace();
        }
        if (constructor != null){
            try {
                Block b = constructor.newInstance(blockParams.toArray(new Object[0]));
                MoreFood.LOGGER.info("Block created");
                return b;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MoreFood.LOGGER.error("Block constructor is null");
        return null;
    }
    BlockItem getItem(){
        Constructor<? extends BlockItem> constructor = null;
        List<Class<?>> classes = new ArrayList<>();
        for (Object o : itemParams) {
            classes.add(o.getClass());
        }
        classes.set(0, Block.class);
        try {
            constructor = itemClass.getDeclaredConstructor(classes.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            MoreFood.LOGGER.error("Item constructor has lifted an exception");
            e.printStackTrace();
        }
        if (constructor != null){
            try {
                BlockItem i = constructor.newInstance(itemParams.toArray(new Object[0]));
                MoreFood.LOGGER.info("BlockItem created");
                return i;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MoreFood.LOGGER.error("Item constructor is null");
        return null;
    }
}