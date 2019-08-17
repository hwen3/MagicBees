package magicbees.integration.thaumcraft;

import com.google.common.base.Preconditions;
import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import magicbees.MagicBees;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.bees.BeeIntegrationInterface;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectSpawnMob;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleFlowerProvider;
import magicbees.init.ItemRegister;
import magicbees.integration.thaumcraft.effects.*;
import magicbees.integration.thaumcraft.util.AuraFlowerProvider;
import magicbees.integration.thaumcraft.util.ThaumcraftFlowerProvider;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.ModNames;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.world.aura.AuraHandler;

import javax.annotation.Nonnull;
import java.util.function.IntFunction;

/**
 * Created by Elec332 on 19-5-2017.
 */
@MagicBeesModule(owner = MagicBees.modid, name = "Thaumcraft Integration", modDependencies = ModNames.THAUMCRAFT)
public class IntegrationThaumcraft implements IMagicBeesModule {

    public static int MAX_AURA = AuraHandler.AURA_CEILING;
    public static Aspect ASPECT_TIME = new Aspect("tempus", 0xB68CFF, new Aspect[]{Aspect.VOID, Aspect.ORDER}, new MagicBeesResourceLocation("textures/aspects/tempus.png"), 1);

    @Override
    public void init(IMagicBeesInitialisationEvent event) {
        Block shimmerleaf = Preconditions.checkNotNull(BlocksTC.shimmerleaf);
        Block cinderpearl = Preconditions.checkNotNull(BlocksTC.cinderpearl);

        BeeIntegrationInterface.blockTCAirShard = BlocksTC.crystalAir;
        BeeIntegrationInterface.blockTCFireShard = BlocksTC.crystalFire;
        BeeIntegrationInterface.blockTCWaterShard = BlocksTC.crystalWater;
        BeeIntegrationInterface.blockTCEarthShard = BlocksTC.crystalEarth;
        BeeIntegrationInterface.blockTCOrderShard = BlocksTC.crystalOrder;
        BeeIntegrationInterface.blockTCEntropyShard = BlocksTC.crystalEntropy;
        BeeIntegrationInterface.blockTCFluxShard = BlocksTC.crystalTaint;

        BeeIntegrationInterface.zombieBrain = new ItemStack(ItemsTC.brain);
        BeeIntegrationInterface.nuggetPork = new ItemStack(ItemsTC.chunks, 1, 2);
        BeeIntegrationInterface.nuggetBeef = new ItemStack(ItemsTC.chunks, 1, 0);
        BeeIntegrationInterface.nuggetChicken = new ItemStack(ItemsTC.chunks, 1, 1);
        BeeIntegrationInterface.voidMetalNugget = new ItemStack(ItemsTC.nuggets, 1, 7);

        BeeIntegrationInterface.flowersThaumcraft = new AlleleFlowerProvider(BeeIntegrationInterface.tc_flowers_name, new ThaumcraftFlowerProvider("flowersThaumcraft", shimmerleaf, cinderpearl));
        BeeIntegrationInterface.flowerAuraNode = new AlleleFlowerProvider(BeeIntegrationInterface.tc_flowersAuraNode_name, new AuraFlowerProvider("flowersThaumcraftAura"));

        BeeIntegrationInterface.effectNodeEmpower = new AlleleEffectEmpower(BeeIntegrationInterface.tc_nodeEmpower_name);
        BeeIntegrationInterface.effectNodeRepair = new AlleleEffectPurifying(BeeIntegrationInterface.tc_nodeRepair_name);
        BeeIntegrationInterface.effectNodeConversionTaint = new AlleleEffectTaint(BeeIntegrationInterface.tc_nodeConversionTaint_name);
        BeeIntegrationInterface.effectNodeConversionPure = new AlleleEffectPurifying(BeeIntegrationInterface.tc_nodeConversionPure_name);
        BeeIntegrationInterface.effectNodeConversionHungry = new AlleleEffectDarkHunger(BeeIntegrationInterface.tc_nodeConversionHungry_name);
        BeeIntegrationInterface.effectVisRecharge = new AlleleEffectRejuvenating(BeeIntegrationInterface.tc_visRecharge_name);
        BeeIntegrationInterface.effectSpawnWhisp = new AlleleEffectSpawnMob(BeeIntegrationInterface.tc_spawnWhisp_name, new ResourceLocation("thaumcraft", "Wisp")).setThrottle(100).setSpawnChance(80);

        BeeIntegrationInterface.TCVisMutationRequirement = new IntFunction<IMutationCondition>() {

            @Nonnull
            @Override
            public IMutationCondition apply(final int input) {

                return new IMutationCondition() {

                    @Override
                    public float getChance(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IAllele allele0, @Nonnull IAllele allele1, @Nonnull IGenome genome0, @Nonnull IGenome genome1, @Nonnull IClimateProvider climate) {
                        if (AuraHelper.getVis(world, pos) >= input) {
                            return 1;
                        }
                        return 0;
                    }

                    @Nonnull
                    @Override
                    public String getDescription() {
                        return "Vis requirement: " + input;
                    }

                };

            }

        };

        ItemRegister.tcBackpackFilter = stack -> stack.getItem() == ItemsTC.celestialNotes || stack.getItem() == ItemsTC.curio;

        ThaumcraftRecipes.addRecipes();
        ThaumcraftResearch.setupResearch();
    }

}
