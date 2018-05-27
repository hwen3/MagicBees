package magicbees.item;

import forestry.api.apiculture.IBeeModifier;
import forestry.core.utils.Translator;
import magicbees.MagicBees;
import magicbees.bees.EnumBeeModifiers;
import magicbees.elec332.corerepack.compat.forestry.bee.IDefaultHiveFrame;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Elec332 on 22-8-2016.
 */
public class ItemMagicBeesFrame extends Item implements IDefaultHiveFrame {

    public ItemMagicBeesFrame(EnumBeeModifiers modifier) {
        super();
        setRegistryName(new MagicBeesResourceLocation("frames."+modifier.name().toLowerCase()));
        Utils.setUnlocalizedName(this);
        setMaxDamage(modifier.getMaxDamage());
        setCreativeTab(MagicBees.creativeTab);
        setMaxStackSize(1);
        this.modifier = modifier;
    }

    private final EnumBeeModifiers modifier;

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        modifier.addInformation(stack, world, tooltip, advanced.isAdvanced());
        if (!stack.isItemDamaged()) {
            tooltip.add(Translator.translateToLocalFormatted("item.for.durability", stack.getMaxDamage()));
        }
    }


    @Override
    @Nonnull
    public IBeeModifier getBeeModifier() {
        return modifier;
    }

    @Override
    public IBeeModifier getBeeModifier(ItemStack frame)
    {
        return modifier;
    }
}
