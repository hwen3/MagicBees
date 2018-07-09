package magicbees.bees;

import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IAlleleBeeSpeciesBuilder;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.*;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.ForestryAlleles;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleFlowerProvider;
import magicbees.elec332.corerepack.compat.forestry.bee.BeeGenomeTemplate;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.Function;

import static magicbees.elec332.corerepack.compat.forestry.ForestryAlleles.*;

/**
 * Created by Elec332 on 15-5-2017.
 */
public final class BeeIntegrationInterface {

	public static final ResourceLocation bot_flowers_name = new MagicBeesResourceLocation("flowersBotania");
	public static final ResourceLocation bot_dreaming_name = new MagicBeesResourceLocation("effectDreaming");

	public static final ResourceLocation te_spawnBlizz_name = new MagicBeesResourceLocation("spawnBlizz");
	public static final ResourceLocation te_spawnBlitz_name = new MagicBeesResourceLocation("spawnBlitz");
	public static final ResourceLocation te_spawnBasalz_name = new MagicBeesResourceLocation("spawnBasalz");

	public static final ResourceLocation tc_visRecharge_name = new MagicBeesResourceLocation("visRecharge");
	public static final ResourceLocation tc_nodeEmpower_name = new MagicBeesResourceLocation("nodeEmpower");
	public static final ResourceLocation tc_nodeRepair_name = new MagicBeesResourceLocation("nodeRepair");
	public static final ResourceLocation tc_nodeConversionTaint_name = new MagicBeesResourceLocation("nodeConversionTaint");
	public static final ResourceLocation tc_nodeConversionPure_name = new MagicBeesResourceLocation("nodeConversionPure");
	public static final ResourceLocation tc_nodeConversionHungry_name = new MagicBeesResourceLocation("nodeConversionHungry");
	public static final ResourceLocation tc_spawnWhisp_name = new MagicBeesResourceLocation("spawnWhisp");
	public static final ResourceLocation tc_flowers_name = new MagicBeesResourceLocation("flowersThaumcraft");
	public static final ResourceLocation tc_flowersAuraNode_name = new MagicBeesResourceLocation("flowersThaumcraftAuraNode");

	public static AlleleFlowerProvider flowersBotania, flowerAuraNode, flowersThaumcraft;
	public static IAlleleEffect effectDreaming, effectSpawnBlizz, effectSpawnBlitz, effectSpawnBasalz,
			effectVisRecharge, effectNodeEmpower, effectNodeRepair, effectNodeConversionTaint, effectNodeConversionPure, effectNodeConversionHungry, effectSpawnWhisp;

	public static IBlockState blockRSAFluxedElectrum;
	public static ItemStack itemRSAFluxedElectrumNugget;
	public static IBlockState livingWood, aeSkyStone;
	public static Item itemPetal, itemPastureSeed, itemManaResource;
	public static int seedTypes;

	public static Block blockTCAirShard, blockTCFireShard, blockTCWaterShard, blockTCEarthShard, blockTCOrderShard, blockTCEntropyShard, blockTCFluxShard;
	public static Function<Integer, IMutationCondition> TCVisMutationRequirement;
	public static ItemStack zombieBrain, nuggetPork, nuggetBeef, nuggetChicken, voidMetalNugget;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	static {
		Item nullItem = Items.FLOWER_POT;
		IBlockState nullBlock = Blocks.YELLOW_FLOWER.getDefaultState();

		//prevent null items/blocks/alleles/effects/whatever
		flowersBotania = new AlleleFlowerProvider(bot_flowers_name, ForestryAlleles.FLOWERS_VANILLA.getProvider());
		effectDreaming = getPlaceholderEffect(bot_dreaming_name);

		effectSpawnBlizz = getPlaceholderEffect(te_spawnBlizz_name);
		effectSpawnBlitz = getPlaceholderEffect(te_spawnBlitz_name);
		effectSpawnBasalz = getPlaceholderEffect(te_spawnBasalz_name);
		blockRSAFluxedElectrum = livingWood = aeSkyStone = nullBlock;
		itemPetal = itemPastureSeed = itemManaResource = nullItem;
		itemRSAFluxedElectrumNugget = new ItemStack(nullItem);

		blockTCAirShard = blockTCEarthShard = blockTCFireShard = blockTCWaterShard = blockTCOrderShard = blockTCEntropyShard = blockTCFluxShard = Blocks.COMMAND_BLOCK;
		TCVisMutationRequirement = integer -> new IMutationCondition() {

            @Override
            public float getChance(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IAllele allele0, @Nonnull IAllele allele1, @Nonnull IGenome genome0, @Nonnull IGenome genome1, @Nonnull IClimateProvider climate) {
                return 0;
            }

            @Override
            @Nonnull
            public String getDescription() {
                return "No mutations for you...";
            }

        };
		zombieBrain = nuggetPork = nuggetBeef = nuggetChicken = voidMetalNugget = new ItemStack(nullItem);

		effectVisRecharge = getPlaceholderEffect(tc_visRecharge_name);
		effectNodeEmpower = getPlaceholderEffect(tc_nodeEmpower_name);
		effectNodeRepair = getPlaceholderEffect(tc_nodeRepair_name);
		effectNodeConversionTaint = getPlaceholderEffect(tc_nodeConversionTaint_name);
		effectNodeConversionPure = getPlaceholderEffect(tc_nodeConversionPure_name);
		effectNodeConversionHungry = getPlaceholderEffect(tc_nodeConversionHungry_name);
		effectSpawnWhisp = getPlaceholderEffect(tc_spawnWhisp_name);

		flowersThaumcraft = new AlleleFlowerProvider(tc_flowers_name, ForestryAlleles.FLOWERS_VANILLA.getProvider());
		flowerAuraNode = new AlleleFlowerProvider(tc_flowersAuraNode_name, ForestryAlleles.FLOWERS_VANILLA.getProvider());
	}

	public static void getTemplateTE(BeeGenomeTemplate template){
		BeeGenomeTemplate ret = new BeeGenomeTemplate();
		ret.setHumidityTolerance(TOLERANCE_BOTH_2);
		ret.setTemperatureTolerance(TOLERANCE_BOTH_2);
		ret.setToleratesRain(TRUE_RECESSIVE);
		ret.setSpeed(SPEED_FAST);
		ret.setFertility(FERTILITY_HIGH);
		ret.setLifeSpan(LIFESPAN_LONGER);
		for (int i = 1; i < ret.getAlleles().length; i++) {
			template.getAlleles()[i] = ret.getAlleles()[i];
		}
	}

	public static void getTemplateTENether(BeeGenomeTemplate template){
		getTemplateTE(template);
		template.setHumidityTolerance(TOLERANCE_NONE);
		template.setTemperatureTolerance(TOLERANCE_NONE);
		template.setCaveDwelling(TRUE_RECESSIVE);
		template.setSpeed(SPEED_SLOW);
		template.setFertility(FERTILITY_NORMAL);
		template.setFlowerProvider(FLOWERS_NETHER);
		template.setEffect((IAlleleEffect) EnumBeeSpecies.getForestryAllele("effectIgnition"));
		template.setNeverSleeps(TRUE_RECESSIVE);
	}

	public static void getTemplateTEEnd(BeeGenomeTemplate template){
		getTemplateTE(template);
		template.setNeverSleeps(TRUE_RECESSIVE);
		template.setCaveDwelling(TRUE_RECESSIVE);
		template.setSpeed(SPEED_FASTEST);
		template.setLifeSpan(LIFESPAN_LONGEST);
	}

	public static void addPetals(IAlleleBeeSpeciesBuilder species, float chance){
		if (itemPetal == null){
			return;
		}
		for (int i = 0; i < EnumDyeColor.values().length; i++) {
			species.addSpecialty(new ItemStack(itemPetal, 1, i), chance);
		}
	}

	private BeeIntegrationInterface(){
		throw new RuntimeException();
	}

	private static IAlleleBeeEffect getPlaceholderEffect(ResourceLocation name){
		return new AlleleEffectThrottled(name) {

			@Override
			public IEffectData doEffectThrottled(IBeeGenome beeGenome, IEffectData effectData, IBeeHousing beeHousing) {
				return effectData;
			}

			@Override
			@SuppressWarnings("all")
			public IEffectData validateStorage(IEffectData effectData) {
				if (effectData == null){
					return new EffectData(1, 0, 0);
				}
				return effectData;
			}

		};
	}

}
