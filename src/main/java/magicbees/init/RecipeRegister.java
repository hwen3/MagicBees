package magicbees.init;

import com.google.common.collect.Maps;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.items.EnumPropolis;
import forestry.apiculture.items.ItemRegistryApiculture;
import forestry.core.fluids.Fluids;
import magicbees.item.types.*;
import magicbees.util.Config;
import magicbees.util.EnumOreResourceType;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Map;

import static magicbees.init.ItemRegister.*;

/**
 * Created by Elec332 on 13-2-2017.
 */
public final class RecipeRegister {

    private static ItemStack beesWax, honeyDrop, honeyDew, magicWax, refractoryWax, pollen, royaljelly;

    public static void init(){
        getItems();
        registerForestryRecipes();
    }

    private static void getItems(){
        if (beesWax == null){
            ItemRegistryApiculture ai = Utils.getApicultureItems();
            beesWax = Utils.getCoreItems().beeswax.getItemStack();
            honeyDrop = ai.honeyDrop.getItemStack();
            honeyDew = ai.honeydew.getItemStack();
            refractoryWax = Utils.getCoreItems().refractoryWax.getItemStack();
            magicWax = getWax(EnumWaxType.MAGIC);
            pollen = ai.pollenCluster.getItemStack();
            royaljelly = ai.royalJelly.getItemStack();
        }
    }

    @SubscribeEvent
    public void registerVanillaRecipes(RegistryEvent.Register<IRecipe> event){
        getItems();

        Ingredient input;
        ItemStack output;

        ItemStack inputStack = getResource(EnumResourceType.EXTENDED_FERTILIZER);
        output = Utils.getCoreItems().fertilizerCompound.getItemStack(6);
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("fertilizer1"), null, output,
                " S ", " F ", " S ",
                'F', inputStack,
                'S', Blocks.SAND
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("fertilizer2"), null, output,
                "   ", "SFS", "   ",
                'F', inputStack,
                'S', Blocks.SAND
        );

        output = output.copy();
        output.setCount(12);
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("fertilizer3"), null, output,
                "aaa", "aFa", "aaa",
                'F', inputStack,
                'a', Utils.getCoreItems().ash
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("exp"), null, new ItemStack(Items.EXPERIENCE_BOTTLE),
                "DDD", "DBD", "DDD",
                'D', getDrop(EnumDropType.INTELLECT),
                'B', Items.GLASS_BOTTLE
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("soulsand1"), null, new ItemStack(Blocks.SOUL_SAND, 4),
                "SwS", "wDw", "SwS",
                'S', Blocks.SAND,
                'D', Blocks.DIRT,
                'w', getWax(EnumWaxType.SOUL)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("soulsand2"), null, new ItemStack(Blocks.SOUL_SAND, 4),
                "wSw", "SDS", "wSw",
                'S', Blocks.SAND,
                'D', Blocks.DIRT,
                'w', getWax(EnumWaxType.SOUL)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("moondial"), null, new ItemStack(ItemRegister.moonDial),
                "DqD", "qrq", "DqD",
                'r', Items.REDSTONE,
                'q', Items.QUARTZ,
                'D', "dyeGreen"
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("skullfragment"), null, getResource(EnumResourceType.SKULL_FRAGMENT),
                "xxx", "xxx", "xxx",
                'x', getResource(EnumResourceType.SKULL_CHIP)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("nSk"), null, new ItemStack(Items.SKULL, 1, 1),
                "xxx", "xxx",
                'x', getResource(EnumResourceType.SKULL_FRAGMENT)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("dragonchunk"), null, getResource(EnumResourceType.DRAGON_CHUNK),
                "xxx", "xxx",
                'x', getResource(EnumResourceType.DRAGON_DUST)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("dragonegg"), null, new ItemStack(Blocks.DRAGON_EGG, 1),
                "ccc", "cec", "ccc",
                'c', getResource(EnumResourceType.DRAGON_CHUNK),
                'e', getResource(EnumResourceType.ESSENCE_FALSE_LIFE)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_eld1"), null, getResource(EnumResourceType.ESSENCE_EVERLASTING_DURABILITY),
                "gwg", "wiw", "gwg",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.MAGIC),
                'i', Blocks.IRON_BLOCK
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_eld2"), null, getResource(EnumResourceType.ESSENCE_EVERLASTING_DURABILITY),
                "wgw", "gig", "wgw",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.MAGIC),
                'i', Blocks.IRON_BLOCK
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_fl1"), null, getResource(EnumResourceType.ESSENCE_FALSE_LIFE),
                "gwg", "wfw", "gwg",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'f', Blocks.RED_FLOWER
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_fl2"), null, getResource(EnumResourceType.ESSENCE_FALSE_LIFE),
                "wgw", "gfg", "wgw",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'f', Blocks.RED_FLOWER
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_cg1"), null, getResource(EnumResourceType.ESSENCE_SHALLOW_GRAVE),
                "gwg", "wfw", "gwg",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'f', Items.ROTTEN_FLESH
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_cg2"), null, getResource(EnumResourceType.ESSENCE_SHALLOW_GRAVE),
                "wgw", "gfg", "wgw",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'f', Items.ROTTEN_FLESH
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_lt1"), null, getResource(EnumResourceType.ESSENCE_LOST_TIME),
                "wgw", "gcg", "wgw",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'c', Items.CLOCK
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_lt2"), null, getResource(EnumResourceType.ESSENCE_LOST_TIME),
                "gwg", "wcw", "gwg",
                'g', Blocks.GLASS,
                'w', getWax(EnumWaxType.SOUL),
                'c', Items.CLOCK
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_fp1"), null, getResource(EnumResourceType.ESSENCE_FICKLE_PERMANENCE),
                "wew", "gcg", "wew",
                'w', getWax(EnumWaxType.SOUL),
                'c', Items.MAGMA_CREAM,
                'e', Items.EGG,
                'g', Blocks.GLOWSTONE
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_fp2"), null, getResource(EnumResourceType.ESSENCE_FICKLE_PERMANENCE),
                "wgw", "ece", "wgw",
                'w', getWax(EnumWaxType.SOUL),
                'c', Items.MAGMA_CREAM,
                'e', Items.EGG,
                'g', Blocks.GLOWSTONE
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("magicframe"), null, new ItemStack(magicFrame),
                "www", "wfw", "www",
                'w', magicWax,
                'f', Utils.getApicultureItems().frameUntreated
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("temporalframe"), null, new ItemStack(temporalFrame),
                "sPs", "PfP", "sPs",
                's', Blocks.SAND,
                'P', getPollen(EnumPollenType.PHASED),
                'f', magicFrame
        );

        input = Ingredient.fromStacks(new ItemStack(magicFrame));
        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("ressilientframe"), null, new ItemStack(resilientFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_EVERLASTING_DURABILITY)),
                input
        );

        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("gentleframe"), null, new ItemStack(gentleFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_FALSE_LIFE)),
                input
        );

        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("necrotictframe"), null, new ItemStack(necroticFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_SHALLOW_GRAVE)),
                input
        );

        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("metabolicframe"), null, new ItemStack(metabolicFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_FICKLE_PERMANENCE)),
                input
        );

        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("temporalframe"), null, new ItemStack(temporalFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_LOST_TIME)),
                input
        );

        GameRegistry.addShapelessRecipe(new MagicBeesResourceLocation("oblivionframe"), null, new ItemStack(oblivionFrame),
                Ingredient.fromStacks(getResource(EnumResourceType.ESSENCE_SCORNFUL_OBLIVION)),
                Ingredient.fromStacks(Utils.getApicultureItems().frameProven.getItemStack())
        );


        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("effectjar"), null, new ItemStack(BlockRegister.effectJar),
                "GSG", "QPQ", "GGG",
                'G', Blocks.GLASS,
                'S', "slabWood",
                'P', getPollen(EnumPollenType.UNUSUAL),
                'Q', Items.QUARTZ
        );
/* todo
        output = new ItemStack(Config.magicApiary);
        GameRegistry.addShapelessRecipe(output,
                Config.pollen.getStackForType(PollenType.UNUSUAL, 2),
                Config.drops.getStackForType(DropType.ENCHANTED, 2),
                new ItemStack(ForestryHelper.apicultureBlock, 1, ForestryHelper.ApicultureBlock.APIARY.ordinal())
        );
*/
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("enchanted_earth_1"), null, new ItemStack(BlockRegister.enchantedEarth),
                "d d", " e ", "d d",
                'd', new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE),
                'e', getResource(EnumResourceType.ESSENCE_FALSE_LIFE)
        );

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("enchanted_earth_2"), null, new ItemStack(BlockRegister.enchantedEarth),
                " d ", "ded", " d ",
                'd', new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE),
                'e', getResource(EnumResourceType.ESSENCE_FALSE_LIFE)
        );

        output = getResource(EnumResourceType.DIMENSIONAL_SINGULARITY);
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("dimensionalsingularity"), null, output,
                " G ", "QEQ", " W ",
                'E', Items.ENDER_EYE,
                'Q', Blocks.QUARTZ_BLOCK,
                'W', Blocks.END_STONE,
                'G', Blocks.GOLD_BLOCK
        );
/* todo
        Magic capsules
        output = new ItemStack(Config.magicCapsule);
        output.stackSize = 4;
        GameRegistry.addRecipe(new ShapedOreRecipe(output,
                "WWW",
                'W', "waxMagical"
        ));

        output = Config.voidCapsule.getCapsuleForLiquid(FluidType.EMPTY);
        output.stackSize = 4;
        GameRegistry.addRecipe(output,
                "T T", "GFG", "T T",
                'G', Blocks.GLASS_PANE,
                'F', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY),
                'T', Items.GOLD_NUGGET
        );
*/

        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("essence_scob"), null, getResource(EnumResourceType.ESSENCE_SCORNFUL_OBLIVION),
                "gst", "sEs", "tsg",
                'g', getResource(EnumResourceType.ESSENCE_SHALLOW_GRAVE),
                't', getResource(EnumResourceType.ESSENCE_LOST_TIME),
                's', new ItemStack(Items.SKULL, 1, 1),
                'E', Blocks.DRAGON_EGG
        );
        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped("gst", "sEs", "tsg",
                'g', getResource(EnumResourceType.ESSENCE_SHALLOW_GRAVE),
                't', getResource(EnumResourceType.ESSENCE_LOST_TIME),
                's', new ItemStack(Items.SKULL, 1, 1),
                'E', Blocks.DRAGON_EGG);
        event.getRegistry().register((new ShapedRecipes("", primer.width, primer.height, primer.input, getResource(EnumResourceType.ESSENCE_SCORNFUL_OBLIVION)){

            @Override
            @Nonnull
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                ret.set(4, new ItemStack(Blocks.DRAGON_EGG));
                return ret;
            }

        }).setRegistryName(new MagicBeesResourceLocation("essence_scob")));


        output = new ItemStack(ItemRegister.mysteriousMagnet);
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("mysteriousmagnet0"), null, output,
                " i ", "cSc", " d ",
                'i', Items.IRON_INGOT,
                'c', Items.COMPASS,
                'd', Items.DIAMOND,
                'S', getResource(EnumResourceType.DIMENSIONAL_SINGULARITY)
        );
        for (int level = 1; level <= Config.magnetMaxLevel; level++) {
            output = new ItemStack(ItemRegister.mysteriousMagnet, 1, level * 2);
            GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("mysteriousmagnet"+level), null, output,
                    " d ", "mSm", " B ",
                    'd', Items.DIAMOND,
                    'm', new ItemStack(ItemRegister.mysteriousMagnet, 1, (level - 1) * 2),
                    'B', Blocks.REDSTONE_BLOCK,
                    'S', getResource(EnumResourceType.DIMENSIONAL_SINGULARITY)
            );
        }

        EnumOreResourceType.registerRecipes(event.getRegistry());
    }

    private static void registerForestryRecipes(){
        registerCentrifugeRecipes();
        registerCarpenterRecipes();
    }

    private static void registerCentrifugeRecipes(){
        CombCentrifugeRecipe recipe;

        recipe = new CombCentrifugeRecipe(EnumCombType.MUNDANE);
        recipe.addProduct(beesWax, 0.90f);
        recipe.addProduct(honeyDrop, 0.60f);
        recipe.addProduct(magicWax, 0.10f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.MOLTEN);
        recipe.addProduct(refractoryWax, 0.86f);
        recipe.addProduct(honeyDrop, 0.087f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.FORGOTTEN);
        recipe.addProduct(getWax(EnumWaxType.AMNESIC), 0.50f);
        recipe.addProduct(getPropolis(EnumPropolis.PULSATING), 0.50f);
        recipe.addProduct(honeyDrop, 0.40f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.OCCULT);
        recipe.addProduct(magicWax, 1.0f);
        recipe.addProduct(honeyDrop, 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.OTHERWORLDLY);
        recipe.addProduct(beesWax, 0.50f);
        recipe.addProduct(magicWax, 0.22f);
        recipe.addProduct(honeyDrop, 1);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.PAPERY);
        recipe.addProduct(beesWax, 0.80f);
        recipe.addProduct(magicWax, 0.20f);
        recipe.addProduct(new ItemStack(Items.PAPER), 0.057f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.INTELLECT);
        recipe.addProduct(magicWax, 0.90f);
        recipe.addProduct(honeyDew, 0.40f);
        recipe.addProduct(getDrop(EnumDropType.INTELLECT), 0.10f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.FURTIVE);
        recipe.addProduct(beesWax, 0.90f);
        recipe.addProduct(getPropolis(EnumPropolis.NORMAL), 0.20f);
        recipe.addProduct(honeyDew, 0.35f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.SOUL);
        recipe.addProduct(getWax(EnumWaxType.SOUL), 0.95f);
        recipe.addProduct(honeyDew, 0.26f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TEMPORAL);
        recipe.addProduct(magicWax, 1);
        recipe.addProduct(getPollen(EnumPollenType.PHASED), 0.055f);
        recipe.addProduct(honeyDew, 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TRANSMUTED);
        recipe.addProduct(beesWax, 0.80f);
        recipe.addProduct(magicWax, 0.80f);
        recipe.addProduct(getPropolis(EnumPropolisType.UNSTABLE), 0.15f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.AIRY);
        recipe.addProduct(magicWax, 1);
        recipe.addProduct(new ItemStack(Items.FEATHER), 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.FIREY);
        recipe.addProduct(magicWax, 1);
        recipe.addProduct(new ItemStack(Items.BLAZE_POWDER), 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.WATERY);
        recipe.addProduct(magicWax, 1);
        recipe.addProduct(new ItemStack(Items.DYE), 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.EARTHY);
        recipe.addProduct(magicWax, 1);
        recipe.addProduct(new ItemStack(Items.CLAY_BALL), 0.60f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TE_DESTABILIZED);
        recipe.addProduct(getWax(EnumWaxType.MAGIC), 0.55f);
        recipe.addProduct(getDrop(EnumDropType.DESTABILIZED), 0.22f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TE_CARBON);
        recipe.addProduct(getWax(EnumWaxType.MAGIC), 0.55f);
        recipe.addProduct(getDrop(EnumDropType.CARBON), 0.22f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TE_LUX);
        recipe.addProduct(getWax(EnumWaxType.MAGIC), 0.55f);
        recipe.addProduct(getDrop(EnumDropType.LUX), 0.22f);
        recipe.register(20);

        recipe = new CombCentrifugeRecipe(EnumCombType.TE_ENDEARING);
        recipe.addProduct(getWax(EnumWaxType.MAGIC), 0.55f);
        recipe.addProduct(getDrop(EnumDropType.ENDEARING), 0.22f);
        recipe.register(20);
    }

    private static void registerCarpenterRecipes(){
        ItemStack input;
        ItemStack output;

        output = Utils.getApicultureBlocks().candle.getUnlitCandle(24);
        RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), ItemStack.EMPTY, output.copy(),
                " S ", "WWW", "WWW",
                'W', waxItem,
                'S', Items.STRING
        );

        output.setCount(6);
        input = Utils.getCoreItems().craftingMaterial.getSilkWisp();
        RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), ItemStack.EMPTY, output.copy(),
                "WSW",
                'W', waxItem,
                'S', input
        );

        output = resourceItem.getStackFromType(EnumResourceType.AROMATIC_LUMP, 2);
        RecipeManagers.carpenterManager.addRecipe(30, Fluids.FOR_HONEY.getFluid(1000), ItemStack.EMPTY, output.copy(),
                " P ", "JDJ", " P ",
                'P', pollen,
                'J', royaljelly,
                'D', getDrop(EnumDropType.ENCHANTED)
        );

        RecipeManagers.carpenterManager.addRecipe(30, Fluids.FOR_HONEY.getFluid(1000), ItemStack.EMPTY, output.copy(),
                " J ", "PDP", " J ",
                'P', pollen,
                'J', royaljelly,
                'D', getDrop(EnumDropType.ENCHANTED)
        );
    }

    private static ItemStack getWax(EnumWaxType wax){
        return waxItem.getStackFromType(wax);
    }

    private static ItemStack getDrop(EnumDropType drop){
        return dropItem.getStackFromType(drop);
    }

    private static ItemStack getPollen(EnumPollenType pollen){
        return pollenItem.getStackFromType(pollen);
    }

    private static ItemStack getPropolis(EnumPropolisType propolis){
        return propolisItem.getStackFromType(propolis);
    }

    private static ItemStack getResource(EnumResourceType resource){
        return resourceItem.getStackFromType(resource);
    }

    private static ItemStack getPropolis(EnumPropolis propolis){
        return Utils.getApicultureItems().propolis.get(propolis, 1);
    }

    private static class CombCentrifugeRecipe {

        private CombCentrifugeRecipe(EnumCombType comb){
            this.comb = comb;
            this.products = Maps.newHashMap();
        }

        private EnumCombType comb;
        private final Map<ItemStack, Float> products;

        protected void addProduct(ItemStack stack, float chance){
            products.put(stack.copy(), chance);
        }

        protected void register(int time){
            RecipeManagers.centrifugeManager.addRecipe(time, combItem.getStackFromType(comb), products);
        }

    }

}
