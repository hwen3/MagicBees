package magicbees.block;
import forestry.api.apiculture.IHiveTile;
import forestry.apiculture.tiles.TileHive;
import magicbees.bees.EnumBeeHives;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 15-4-2017.
 */
public class BlockHive extends magicbees.elec332.corerepack.compat.forestry.bee.BlockHive<EnumBeeHives> implements ITileEntityProvider {

	@Override
	public BlockHive register(@Nonnull ResourceLocation rl) {
		super.register(rl);
		return this;
	}

	@Nonnull
	@Override
	public Class<EnumBeeHives> getHiveTypes() {
		return EnumBeeHives.class;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		super.onBlockClicked(world, pos, player);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IHiveTile) {
			IHiveTile hive = (IHiveTile) tile;
			hive.onAttack(world, pos, player);
		}
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IHiveTile) {
			IHiveTile hive = (IHiveTile) tile;
			hive.onBroken(world, pos, player, canHarvestBlock(world, pos, player));
		}
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TileHive();
	}

}
