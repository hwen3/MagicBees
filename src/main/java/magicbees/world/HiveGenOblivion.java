package magicbees.world;

import forestry.api.apiculture.hives.IHiveGen;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-2-2017.
 */
public class HiveGenOblivion implements IHiveGen {

    @Nullable
    @Override
    public BlockPos getPosForHive(@Nonnull World world, int x, int z) {

        int surface = world.getHeight(new BlockPos(x, 0, z)).getY();
        if (surface == 0) {
            return null;
        }

        int y = 10;

        // get to the end stone
        while (!Block.isEqualTo(world.getBlockState(new BlockPos(x, y - 1, z)).getBlock(), Blocks.END_STONE) && y < surface) {
            y += 8;
        }

        // hive must be embedded one deep into the stone
        while (!Block.isEqualTo(world.getBlockState(new BlockPos(x, y - 2, z)).getBlock(), Blocks.AIR) && y > -1) {
            y--;
        }

        return new BlockPos(x, y, z);
    }

    @Override
    public boolean isValidLocation(@Nonnull World world, @Nonnull BlockPos pos) {

        for (EnumFacing f : EnumFacing.VALUES) {
            if (!HiveGenUnderground.isReplaceableOreGen(world.getBlockState(pos.offset(f)), world, pos, Blocks.END_STONE)) {
                return false;
            }
        }

        for (int i = 2; i < 4; i++) {
            if (!world.isAirBlock(new BlockPos(pos.getX(), pos.getY() - i, pos.getZ()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canReplace(@Nonnull IBlockState blockState, @Nonnull World world, @Nonnull BlockPos pos) {
        return Block.isEqualTo(blockState.getBlock(), Blocks.END_STONE);
    }

}
