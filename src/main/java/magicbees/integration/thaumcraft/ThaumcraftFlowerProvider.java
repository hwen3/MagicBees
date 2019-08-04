package magicbees.integration.thaumcraft;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlowerGrowthHelper;
import forestry.api.genetics.IFlowerGrowthRule;
import magicbees.elec332.corerepack.compat.forestry.bee.FlowerProvider;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 31-7-2019
 */
public class ThaumcraftFlowerProvider extends FlowerProvider implements IFlowerGrowthRule {

    public ThaumcraftFlowerProvider(@Nonnull String flowerType, Block shimmerleaf, Block cinderpearl) {
        super(flowerType);
        this.shimmerleaf = shimmerleaf;
        this.cinderpearl = cinderpearl;
        FlowerManager.flowerRegistry.registerGrowthRule(this, this.getFlowerType());
        FlowerManager.flowerRegistry.registerAcceptableFlower(shimmerleaf, getFlowerType());
        FlowerManager.flowerRegistry.registerAcceptableFlower(cinderpearl, getFlowerType());
    }

    private final Block shimmerleaf, cinderpearl;

    @Override
    public boolean growFlower(@Nonnull IFlowerGrowthHelper helper, @Nonnull String flowerType, @Nonnull World world, @Nonnull BlockPos pos) {
        if (world.isAirBlock(pos)) {
            Block b = world.getBlockState(pos.down()).getBlock();
            if (b == Blocks.GRASS || b == Blocks.DIRT) {
                world.setBlockState(pos, shimmerleaf.getDefaultState(), 2);
                return true;
            } else if (b == Blocks.SAND) {
                world.setBlockState(pos, cinderpearl.getDefaultState(), 2);
                return true;
            }
        }
        return false;
    }

}
