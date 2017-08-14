package magicbees.bees.allele;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IEffectData;
import magicbees.api.ITransmutationController;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-2-2017.
 */
public class AlleleEffectTransmuting extends AlleleEffectThrottled {

    public AlleleEffectTransmuting(ResourceLocation rl, ITransmutationController transmutationController) {
        super(rl);
        setDominant();
        setThrottle(200);
        this.transmutationController = transmutationController;
    }

    private final ITransmutationController transmutationController;

    @Override
    public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        World world = housing.getWorldObj();
        BlockPos coords = housing.getCoordinates();
        IBeeModifier beeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
        int xRange = (int)(beeModifier.getTerritoryModifier(genome, 1.0F) * (float)genome.getTerritory().getX());
        int yRange = (int)(beeModifier.getTerritoryModifier(genome, 1.0F) * (float)genome.getTerritory().getY());
        int zRange = (int)(beeModifier.getTerritoryModifier(genome, 1.0F) * (float)genome.getTerritory().getZ());
        int xCoord = coords.getX() + world.rand.nextInt(xRange) - xRange / 2;
        int yCoord = coords.getY() + world.rand.nextInt(yRange) - yRange / 2;
        int zCoord = coords.getZ() + world.rand.nextInt(zRange) - zRange / 2;
        BlockPos pos = new BlockPos(xCoord, yCoord, zCoord);
        transmutationController.transmute(world, pos);
        storedData.setInteger(0, 0);
        return storedData;
    }

    @Override
    public IEffectData validateStorage(@Nullable IEffectData storedData) {
        if (storedData == null || !(storedData instanceof EffectData)) {
            storedData = new EffectData(1, 0, 0);
        }
        return storedData;
    }

}
