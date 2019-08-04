package magicbees.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Created by Elec332 on 22-8-2017.
 */
public class BlockEnchantedEarth extends Block {

    public BlockEnchantedEarth() {
        super(Material.GROUND);
        setTickRandomly(true);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        Block block = world.getBlockState(pos.up()).getBlock();
        if (block.getTickRandomly()) {
            world.scheduleBlockUpdate(pos.up(), block, 0, 0);
        }

        if (random.nextInt(2) == 0) {
            int newX = pos.getX() + (random.nextInt(3) - 1);
            int newY = pos.getY() + 1;
            int newZ = pos.getZ() + (random.nextInt(3) - 1);
            BlockPos newPos = new BlockPos(newX, newY, newZ);
            block = world.getBlockState(newPos).getBlock();
            if (block.getTickRandomly()) {
                world.scheduleBlockUpdate(newPos, block, random.nextInt(5) + 1, 0);
            }
        }
    }

    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, BlockPos pos, @Nonnull EnumFacing direction, IPlantable plantable) {
        return true;
    }

}
