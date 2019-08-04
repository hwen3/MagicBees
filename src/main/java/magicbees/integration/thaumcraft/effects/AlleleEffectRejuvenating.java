package magicbees.integration.thaumcraft.effects;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import magicbees.integration.thaumcraft.AuraHelper;
import magicbees.integration.thaumcraft.IntegrationThaumcraft;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 3-8-2019
 */
public class AlleleEffectRejuvenating extends AlleleEffectThrottled {

    public AlleleEffectRejuvenating(ResourceLocation rl) {
        super(rl);
        setRequiresWorkingQueen();
        setThrottle(15);
    }

    @Override
    public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        AuraHelper.handleRandomChunk(genome, housing, auraChunk -> {
            int max = Math.min(IntegrationThaumcraft.MAX_AURA, auraChunk.getBase() * 2);
            float newV = Math.min((float) (auraChunk.getVis() + 0.09 * 2 * housing.getWorldObj().rand.nextDouble()), max);
            auraChunk.setVis(newV);
        });
        storedData.setInteger(0, 0);
        return storedData;
    }

    @Override
    public IEffectData validateStorage(IEffectData storedData) {
        if (storedData == null || !(storedData instanceof EffectData)) {
            storedData = new EffectData(1, 0, 0);
        }
        return storedData;
    }

}