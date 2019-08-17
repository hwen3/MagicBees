package magicbees.util;

import com.google.common.collect.Maps;
import forestry.api.recipes.RecipeManagers;
import magicbees.item.types.EnumCombType;
import magicbees.item.types.EnumPropolisType;
import net.minecraft.item.ItemStack;

import java.util.Map;

import static magicbees.init.ItemRegister.combItem;
import static magicbees.init.ItemRegister.propolisItem;

/**
 * Created by Elec332 on 13-2-2017
 */
public class CentrifugeRecipe {

    public CentrifugeRecipe(EnumCombType comb) {
        this(combItem.getStackFromType(comb));
    }

    public CentrifugeRecipe(EnumPropolisType propolis) {
        this(propolisItem.getStackFromType(propolis));
    }

    public CentrifugeRecipe(ItemStack stack) {
        this.out = stack.copy();
        this.products = Maps.newHashMap();
    }

    private final ItemStack out;
    private final Map<ItemStack, Float> products;

    public void addProduct(ItemStack stack, float chance) {
        products.put(stack.copy(), chance);
    }

    public void register(int time) {
        RecipeManagers.centrifugeManager.addRecipe(time, out, products);
    }

}