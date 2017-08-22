package magicbees.item;

import forestry.api.apiculture.IBeeModifier;
import magicbees.MagicBees;
import magicbees.bees.EnumBeeModifiers;
import magicbees.elec332.corerepack.compat.forestry.bee.IDefaultHiveFrame;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

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
        this.modifier = modifier;
    }

    private final EnumBeeModifiers modifier;

    @Override
    @Nonnull
    public IBeeModifier getBeeModifier() {
        return modifier;
    }

}
