package com.meneghin.morefood.trees;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;
import java.util.function.Supplier;

public class MySaplingBlock extends BushBlock implements IGrowable {

	public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
	private final Supplier<Tree> tree;


	public MySaplingBlock(Supplier<Tree> tree, Properties properties) {
		super(properties);
		this.tree = tree;
	}


	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
		this.grow(world, pos, state, rand);
	}

	private void grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
		if (state.get(STAGE) == 0) {
			world.setBlockState(pos, state.cycle(STAGE), 4);
		} else {
			if (!ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
			this.tree.get().func_225545_a_(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}
}
