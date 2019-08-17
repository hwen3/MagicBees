package magicbees.integration.thaumcraft;

import com.google.common.base.Preconditions;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.items.EnumPropolis;
import magicbees.item.types.EnumCombType;
import magicbees.item.types.EnumPropolisType;
import magicbees.item.types.EnumResourceType;
import magicbees.item.types.EnumWaxType;
import magicbees.util.CentrifugeRecipe;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.util.Arrays;

import static magicbees.init.ItemRegister.*;

/**
 * Created by Elec332 on 17-8-2019
 */
public class ThaumcraftRecipes {

    static void addRecipes() {
        registerNormalRecipes();
        registerCentrifugeRecipes();
        registerCarpenterRecipes();
    }

    private static void registerNormalRecipes() {
        ItemStack lf = resourceItem.getStackFromType(EnumResourceType.LORE_FRAGMENT);
        GameRegistry.addShapedRecipe(new MagicBeesResourceLocation("lore_fragment"), null, new ItemStack(ItemsTC.amber), "lll", "lll", "lll", 'l', lf);

        Arrays.stream(EnumResourceType.values()).filter(r -> r.toString().startsWith("TC_DUST_")).forEach(dust -> {
            String aspect = dust.toString().replace("TC_DUST_", "").toLowerCase();
            Aspect aspect1;
            try {
                aspect1 = (Aspect) Aspect.class.getDeclaredField(aspect.toUpperCase()).get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Aspect " + aspect + " not found.");
            }
            ItemCrystalEssence crystalEssence = (ItemCrystalEssence) ItemsTC.crystalEssence;
            ItemStack result = new ItemStack(crystalEssence);
            crystalEssence.setAspects(result, new AspectList().add(Preconditions.checkNotNull(aspect1), 8));
            GameData.register_impl(new ShapelessRecipes("", result, NonNullList.withSize(4, Ingredient.fromStacks(resourceItem.getStackFromType(dust)))).setRegistryName(new MagicBeesResourceLocation("tc_shard_" + aspect)));
        });
    }

    private static void registerCentrifugeRecipes() {
        CentrifugeRecipe recipe;
        ItemStack magicWax = waxItem.getStackFromType(EnumWaxType.MAGIC);
        ItemStack propolis = Utils.getApicultureItems().propolis.get(EnumPropolis.NORMAL, 1);

        recipe = new CentrifugeRecipe(EnumCombType.TC_AIR);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.FEATHER), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.AIR), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumCombType.TC_FIRE);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.BLAZE_POWDER), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.FIRE), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumCombType.TC_WATER);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getMetadata()), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.WATER), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumCombType.TC_EARTH);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.CLAY_BALL), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.EARTH), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumCombType.TC_ORDER);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.REDSTONE), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.ORDER), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumCombType.TC_ENTROPY);
        recipe.addProduct(magicWax, 1f);
        recipe.addProduct(new ItemStack(Items.GUNPOWDER), 0.6f);
        recipe.addProduct(getPropolis(EnumPropolisType.ENTROPY), 0.8f);
        recipe.register(20);

        recipe = new CentrifugeRecipe(EnumPropolisType.AIR);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_AIR), 0.9f);
        recipe.register(8);

        recipe = new CentrifugeRecipe(EnumPropolisType.FIRE);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_FIRE), 0.9f);
        recipe.register(8);

        recipe = new CentrifugeRecipe(EnumPropolisType.WATER);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_WATER), 0.8f);
        recipe.register(8);

        recipe = new CentrifugeRecipe(EnumPropolisType.EARTH);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_EARTH), 0.8f);
        recipe.register(8);

        recipe = new CentrifugeRecipe(EnumPropolisType.ORDER);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_ORDER), 0.8f);
        recipe.register(8);

        recipe = new CentrifugeRecipe(EnumPropolisType.ENTROPY);
        recipe.addProduct(propolis, 1f);
        recipe.addProduct(getResource(EnumResourceType.TC_DUST_ENTROPY), 0.8f);
        recipe.register(8);
    }

    private static void registerCarpenterRecipes() {
        RecipeManagers.carpenterManager.addRecipe(200, new FluidStack(FluidRegistry.WATER, 1000), ItemStack.EMPTY, new ItemStack(thaumaturgeBackpackT2),
                "WXW", "WTW", "WWW",
                'X', Items.DIAMOND,
                'W', Utils.getCoreItems().craftingMaterial.getWovenSilk(),
                'T', new ItemStack(thaumaturgeBackpackT1)
        );
    }

    private static ItemStack getPropolis(EnumPropolisType propolis) {
        return propolisItem.getStackFromType(propolis);
    }

    private static ItemStack getResource(EnumResourceType resource) {
        return resourceItem.getStackFromType(resource);
    }


}
