package magicbees.elec332.corerepack.compat.forestry.allele;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import magicbees.elec332.corerepack.compat.forestry.bee.ForestryBeeEffects;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Elec332 on 14-8-2016.
 */
public abstract class AlleleEffect extends AbstractAllele implements IAlleleBeeEffect {

    public AlleleEffect(ResourceLocation rl) {
        super(rl, EnumBeeChromosome.EFFECT);
    }

    public AlleleEffect(String s) {
        super(s, EnumBeeChromosome.EFFECT);
    }

    public AlleleEffect(String uid, String unlocalizedName) {
        super(uid, unlocalizedName, EnumBeeChromosome.EFFECT);
    }

    private boolean combinable;

    public AlleleEffect setCombinable() {
        this.combinable = true;
        return this;
    }

    @Override
    public abstract IEffectData validateStorage(IEffectData storedData);

    @Override
    public abstract IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing);

    @Override
    public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        ForestryBeeEffects.effectNone.doFX(genome, storedData, housing);
        return storedData;
    }

    @Override
    public boolean isCombinable() {
        return combinable;
    }

    public static AxisAlignedBB getBounding(IBeeGenome genome, IBeeHousing housing) {
        return forestry.apiculture.genetics.alleles.AlleleEffect.getBounding(genome, housing);
    }

    public static <T extends Entity> List<T> getEntitiesInRange(IBeeGenome genome, IBeeHousing housing, Class<T> entityClass) {
        return getEntitiesInRange(genome, housing, entityClass, EntitySelectors.NOT_SPECTATING);
    }

    public static <T extends Entity> List<T> getEntitiesInRange(IBeeGenome genome, IBeeHousing housing, Class<T> entityClass, Predicate<? super T> predicate) {
        AxisAlignedBB boundingBox = getBounding(genome, housing);
        return housing.getWorldObj().getEntitiesWithinAABB(entityClass, boundingBox, predicate::test);
    }

}
