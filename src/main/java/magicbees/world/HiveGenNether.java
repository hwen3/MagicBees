package magicbees.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-2-2017.
 */
public class HiveGenNether extends HiveGenUnderground {

    public HiveGenNether(int minLevel, int range, int surroundCount) {
        super(minLevel, range, Blocks.NETHERRACK, surroundCount);
    }

    @Nullable
    @Override
    public BlockPos getPosForHive(@Nonnull World world, int x, int z) {
        BlockPos cP = super.getPosForHive(world, x, z);
        if(cP != null && !this.isValidLocation(world, cP)) {
            int searchDirection = world.rand.nextBoolean()?4:-4;

            while(!Block.isEqualTo(world.getBlockState(cP).getBlock(), this.replace)) {
                cP = cP.up(searchDirection);
                if(cP.getY() < this.minLevel || cP.getY() > this.minLevel + this.range || Block.isEqualTo(world.getBlockState(cP.down(1)).getBlock(), Blocks.BEDROCK)) {
                    return null;
                }
            }
        }

        return cP;
    }

}
