package magicbees.integration.botania;

import com.google.common.collect.Lists;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import magicbees.bees.EnumBeeSpecies;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeElvenTrade;

import java.util.List;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class RecipeElvenTradeBeeSpecies extends RecipeElvenTrade {

    public RecipeElvenTradeBeeSpecies(EnumBeeSpecies inSpecies, EnumBeeSpecies outSpecies) {
        super(new ItemStack[]{BeeManager.beeRoot.getMemberStack(outSpecies.getIndividual(), EnumBeeType.DRONE)}, BeeManager.beeRoot.getMemberStack(inSpecies.getIndividual(), EnumBeeType.DRONE));
        inputSpecies = inSpecies.getSpecies();
    }

    private IAlleleBeeSpecies inputSpecies;

    @Override
    public boolean matches(List<ItemStack> stacks, boolean removeMatched) {
        List<ItemStack> matchedStacks = Lists.newArrayList();
        boolean found = false;

        for (ItemStack inStack : stacks) {
            if (inStack == null) {
                continue;
            }

            if (BeeManager.beeRoot.isMember(inStack, EnumBeeType.DRONE)) {
                IBee bee = BeeManager.beeRoot.getMember(inStack);
                if (bee != null && bee.getGenome().getPrimary().equals(inputSpecies)) {
                    matchedStacks.add(inStack);
                    found = true;
                    break;
                }
            }
        }

        if (removeMatched) {
            for (ItemStack stack : matchedStacks) {
                stacks.remove(stack);
            }
        }

        return found;
    }

}