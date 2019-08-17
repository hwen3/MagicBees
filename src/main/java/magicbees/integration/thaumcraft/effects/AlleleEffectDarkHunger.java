package magicbees.integration.thaumcraft.effects;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import magicbees.integration.thaumcraft.util.AuraHelper;
import magicbees.integration.thaumcraft.util.TaintHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import thaumcraft.api.entities.IEldritchMob;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.entities.monster.cult.EntityCultist;
import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
import thaumcraft.common.entities.monster.cult.EntityCultistKnight;
import thaumcraft.common.lib.SoundsTC;

import java.util.List;
import java.util.Random;

/**
 * Created by Elec332 on 3-8-2019
 */
public class AlleleEffectDarkHunger extends AlleleEffectThrottled {

    public AlleleEffectDarkHunger(ResourceLocation rl) {
        super(rl);
        setRequiresWorkingQueen();
        setThrottle(1000);
    }

    private static final float VIS_REQUIRED = Short.MAX_VALUE / 2f;

    @Override
    public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        AuraHelper.handleRandomChunk(genome, housing, auraChunk -> {
            EffectData data = (EffectData) storedData;
            World world = housing.getWorldObj();
            Random random = world.rand;
            float nom = random.nextFloat() * 100;
            if (auraChunk.getVis() > nom + 1) {
                auraChunk.setVis(auraChunk.getVis() - nom);
                data.setFloat(0, data.getFloat(0) + nom);
            } else {
                TaintHelper.explodeTaint(housing.getCoordinates(), world, auraChunk, random, data.getFloat(0), auraChunk.getVis(), nom, VIS_REQUIRED);
            }
            if (data.getFloat(0) >= VIS_REQUIRED) {
                List<EntityLiving> l = getEntitiesInRange(genome, housing, EntityLiving.class, e -> !(e instanceof IEldritchMob || e instanceof ITaintedMob || e instanceof IMob));
                if (l.size() > 0) {
                    EntityLiving el = l.get(random.nextInt(l.size()));
                    el.attackEntityFrom(DamageSource.MAGIC, 1000000); //Die pls
                    Vec3d pos = el.getPositionVector();

                    EntityCultist e;
                    if (random.nextFloat() > 0.3) {
                        e = new EntityCultistKnight(world);
                    } else {
                        e = new EntityCultistCleric(world);
                    }

                    e.setPosition(pos.x, pos.y, pos.z);
                    e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e.getPosition())), null);
                    data.setFloat(0, 0);
                    world.spawnEntity(e);
                    e.spawnExplosionParticle();
                    e.playSound(SoundsTC.wandfail, 1.0F, 1.0F);
                }
            }
        });
        storedData.setInteger(0, 0);
        return storedData;
    }

    @Override
    public IEffectData validateStorage(IEffectData storedData) {
        if (storedData == null || !(storedData instanceof EffectData)) {
            storedData = new EffectData(1, 0, 1);
        }
        return storedData;
    }

}