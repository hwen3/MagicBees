package magicbees.integration.botania;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlowerGrowthHelper;
import forestry.api.genetics.IFlowerGrowthRule;
import magicbees.elec332.corerepack.compat.forestry.bee.FlowerProvider;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class FlowerProviderBotania extends FlowerProvider implements IFlowerGrowthRule {

    @SuppressWarnings("deprecation")
    public FlowerProviderBotania(@Nonnull String flowerType, Block flower) {
        super(flowerType);
        this.flower = flower;
        FlowerManager.flowerRegistry.registerGrowthRule(this, this.getFlowerType());
        for (int i = 0; i < EnumDyeColor.values().length; i++) {
            FlowerManager.flowerRegistry.registerAcceptableFlower(flower.getStateFromMeta(i));
        }
    }

    private final Block flower;

    @Override
    @SuppressWarnings("deprecation")
    public boolean growFlower(@Nonnull IFlowerGrowthHelper helper, @Nonnull String flowerType, @Nonnull World world, @Nonnull BlockPos pos) {
        if (world.isAirBlock(pos) && flower.canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, flower.getStateFromMeta(world.rand.nextInt(EnumDyeColor.values().length)), 2);
        }
        return false;
    }

}
