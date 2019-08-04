package magicbees.integration.jei;

import magicbees.bees.EnumBeeHives;
import magicbees.init.BlockRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

/**
 * Created by Elec332 on 6-1-2018.
 */
@JEIPlugin
public class MagicBeesJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        for (EnumBeeHives hiveType : EnumBeeHives.values()) {
            addDescription(registry, new ItemStack(BlockRegister.hiveBlock, 1, hiveType.ordinal()), "hive." + hiveType.toString().toLowerCase());
        }
    }

    public static void addDescription(IModRegistry registry, ItemStack stack, String descUnlName) {
        registry.addIngredientInfo(stack.copy(), ItemStack.class, "magicbees.jei.description." + descUnlName);
    }

}
