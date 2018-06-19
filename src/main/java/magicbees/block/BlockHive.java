package magicbees.block;

import forestry.api.apiculture.IHiveTile;
import magicbees.bees.EnumBeeHives;
import magicbees.tile.TileHive;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 15-4-2017.
 */
public class BlockHive extends magicbees.elec332.corerepack.compat.forestry.bee.BlockHive<EnumBeeHives> implements ITileEntityProvider {

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

	@Nonnull
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "." + EnumBeeHives.values()[stack.getItemDamage()].getName();
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IHiveTile) {
			IHiveTile hive = (IHiveTile) tile;
			hive.onBroken(world, pos, player, canHarvestBlock(world, pos, player));
		}
	}

	@Override //Fixes picking block returning wrong block
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TileHive();
	}

}
