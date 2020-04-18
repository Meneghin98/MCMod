package com.meneghin.morefood.trees.tree;

import com.meneghin.morefood.init.ModItems;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class PearTree extends Tree {

	public static final TreeFeatureConfig PEAR_TREE_CONFIG = (new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModItems.PEAR_LOG.getDefaultState()),
			new SimpleBlockStateProvider(ModItems.PEAR_LEAVES.getDefaultState()),
			new BlobFoliagePlacer(3, 0)))
			.baseHeight(10).foliageHeight(5).heightRandA(8).ignoreVines().setSapling((IPlantable) ModItems.PEAR_SAPLING).build();

	@Override
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return Feature.NORMAL_TREE.withConfiguration(PEAR_TREE_CONFIG);
	}

}
