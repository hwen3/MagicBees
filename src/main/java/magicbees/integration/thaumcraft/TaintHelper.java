package magicbees.integration.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.entities.monster.boss.EntityTaintacleGiant;
import thaumcraft.common.entities.monster.tainted.EntityTaintSeed;
import thaumcraft.common.entities.monster.tainted.EntityTaintSeedPrime;
import thaumcraft.common.entities.monster.tainted.EntityTaintacle;
import thaumcraft.common.entities.monster.tainted.EntityTaintacleSmall;
import thaumcraft.common.world.aura.AuraChunk;
import thaumcraft.common.world.aura.AuraHandler;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Created by Elec332 on 3-8-2019
 */
public class TaintHelper {

    public static void explodeTaint(BlockPos pos, World world, AuraChunk auraChunk, Random random, float stored, float vis, float nom, float maxStored) {
        float perc = stored / maxStored;

        float vinv = 1.5f + 0.3f * (float) Math.sqrt(Math.min(vis / nom, 1));
        float flux = Math.max(30, (stored / 150) * vinv);
        flux *= 0.85;
        flux = Math.min(flux, 150);
        flux *= (0.8 + 0.2 * random.nextFloat()) * 1.5;

        auraChunk.setFlux(auraChunk.getFlux() + flux);

        int x = pos.getX() >> 4;
        int z = pos.getZ() >> 4;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                AuraHandler.addFlux(world, new BlockPos((x + i) * 16, 10, (z + j) * 16), flux / 3);
            }
        }

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            for (int i = 1; i <= 2; i++) {
                AuraHandler.addFlux(world, new BlockPos((x + facing.getFrontOffsetX() * i) * 16, 10, (z + facing.getFrontOffsetZ() * i) * 16), flux / 3);
            }
        }

        world.createExplosion(null, pos.getX(), pos.getY() + 1.7, pos.getZ(), 4.4f, true);
        pos = world.getTopSolidOrLiquidBlock(pos);
        int sr = 4;
        int times = 1 + random.nextInt(3);
        if (stored > maxStored * 0.24) {
            int add = (int) Math.ceil(stored / 3000);
            sr += add;
            times += add / 2;
        }
        for (int i = 0; i < times + 1; i++) {
            int mod = i == 0 ? 0 : 1;
            BlockPos poz = world.getTopSolidOrLiquidBlock(pos.add((random.nextInt(2 * sr * mod + 1) - sr * mod) * (2 - mod), 0, (random.nextInt(2 * sr * mod + 1) - sr * mod) * (2 - mod)));
            if (poz.equals(pos) || poz.equals(pos.up())) {
                poz = world.getTopSolidOrLiquidBlock(poz.offset(EnumFacing.HORIZONTALS[random.nextInt(4)], 2));
            }
            Entity e = random.nextDouble() * 1.2 < perc ? new EntityTaintSeedPrime(world) : new EntityTaintSeed(world);
            e.setPosition(poz.getX() + 0.5, poz.getY(), poz.getZ() + 0.5);
            int range = e instanceof EntityTaintSeedPrime ? 6 : 3;
            world.spawnEntity(e);
            thaumcraft.common.blocks.world.taint.TaintHelper.addTaintSeed(world, poz);
            for (int j = 0; j < stored / 15 + range * 2; j++) {
                thaumcraft.common.blocks.world.taint.TaintHelper.spreadFibres(world, poz.add(MathHelper.nextDouble(random, -range, range), MathHelper.nextDouble(random, -1, 1), MathHelper.nextDouble(random, -range, range)), true);
            }
            poz = poz.add(-1, 1, -1); //Make sure the taint seeds don't suffocate
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    world.setBlockToAir(poz.add(j, 0, k));
                }
            }

        }
        Entity e;
        if (random.nextDouble() < 0.97 && perc > 0.12 || random.nextDouble() < 0.12) {
            e = perc < 0.9 - (random.nextDouble() / 2.5) ? new EntityTaintacle(world) : new EntityTaintacleGiant(world);
        } else {
            e = new EntityTaintacleSmall(world) {

                @Override
                public boolean isEntityInvulnerable(@Nonnull DamageSource source) {
                    return source.isMagicDamage() || source.isExplosion();
                }

            };
        }
        e.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        for (int i = 0; i < 16; i++) {
            thaumcraft.common.blocks.world.taint.TaintHelper.spreadFibres(world, pos, true);
            thaumcraft.common.blocks.world.taint.TaintHelper.spreadFibres(world, pos.down(), true);
            thaumcraft.common.blocks.world.taint.TaintHelper.spreadFibres(world, pos.up(), true);
        }
        world.spawnEntity(e);
    }

}
