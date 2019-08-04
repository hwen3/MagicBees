package magicbees.integration.thaumcraft;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlowerGrowthHelper;
import forestry.api.genetics.IFlowerGrowthRule;
import magicbees.elec332.corerepack.compat.forestry.bee.FlowerProvider;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2019
 */
public class AuraFlowerProvider extends FlowerProvider implements IFlowerGrowthRule {

    public AuraFlowerProvider(@Nonnull String flowerType) {
        super(flowerType);
        FlowerManager.flowerRegistry.registerGrowthRule(this, this.getFlowerType());
        FlowerManager.flowerRegistry.registerAcceptableFlower(BlocksTC.vishroom, getFlowerType());
    }

    @Override
    public boolean growFlower(@Nonnull IFlowerGrowthHelper helper, @Nonnull String flowerType, @Nonnull World world, @Nonnull BlockPos pos) {
        return false;
    }

}
