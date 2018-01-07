package magicbees.world;

import com.google.common.base.Predicate;
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
 * Created by Elec332 on 11-2-2017.
 */
public class HiveGenUnderground implements IHiveGen {

    public HiveGenUnderground(int minLevel, int range, int surroundCount) {
        this(minLevel, range, Blocks.STONE, surroundCount);
    }

    public HiveGenUnderground(int minLevel, int range, Block replace, int surroundCount) {
        this.minLevel = minLevel;
        this.range = range;
        this.replace = replace;
        this.surroundCount = surroundCount;
    }

    protected final int minLevel;
    protected final int range;
    protected final Block replace;
    private final int surroundCount;

    @Nullable
    @Override
    public BlockPos getPosForHive(@Nonnull World world, int x, int z) {
        return new BlockPos(x, minLevel + world.rand.nextInt(range), z);
    }

    @Override
    public boolean isValidLocation(@Nonnull World world, @Nonnull BlockPos blockPos) {
        int i = surroundCount;
        for (EnumFacing f : EnumFacing.VALUES){
            if (isReplaceableOreGen(world.getBlockState(blockPos.offset(f)), world, blockPos, replace)){
                i--;
            }
            if (i <= 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canReplace(@Nonnull IBlockState iBlockState, @Nonnull World world, @Nonnull BlockPos blockPos) {
        return !world.isAirBlock(blockPos) && isReplaceableOreGen(iBlockState, world, blockPos, replace);
    }

    public static boolean isReplaceableOreGen(IBlockState iBlockState, World world, BlockPos blockPos, Block replace){
        return iBlockState.getBlock().isReplaceableOreGen(iBlockState, world, blockPos, new Predicate<IBlockState>() {

            @Override
            public boolean apply(IBlockState input) {
                return input.getBlock() == replace;
            }

        });
    }

}
