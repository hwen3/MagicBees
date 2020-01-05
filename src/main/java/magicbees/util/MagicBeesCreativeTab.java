package magicbees.util;

import com.google.common.base.Preconditions;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import magicbees.MagicBees;
import magicbees.bees.EnumBeeSpecies;
import magicbees.init.ItemRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 15-8-2017.
 */
public class MagicBeesCreativeTab extends CreativeTabs {

    public MagicBeesCreativeTab() {
        super(MagicBees.modid);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegister.magicFrame);
    }

    @Override
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
        super.displayAllRelevantItems(items);
        if (EnumBeeSpecies.values()[0].getIndividualDefinition() == null) {
            return;
        }
        for (EnumBeeType type : EnumBeeType.values()) {
            for (EnumBeeSpecies species : EnumBeeSpecies.values()) {
                if (!species.isActive()) {
                    continue;
                }
                IBee bee = species.getIndividual();
                if (bee.isSecret()) {
                    continue;
                }

                ItemStack beeStack = Preconditions.checkNotNull(BeeManager.beeRoot).getMemberStack(bee, type);
                if (!beeStack.isEmpty()) {
                    items.add(beeStack);
                }
            }
        }
    }

}
