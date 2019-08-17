package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by Elec332 on 19-12-2016.
 */
public enum EnumPropolisType implements IEnumItem {

    UNSTABLE(new Color(0xEFB492)),

    AIR(new Color(0xA19E10)),
    FIRE(new Color(0x95132F)),
    WATER(new Color(0x1054A1)),
    EARTH(new Color(0x00a000)),
    ORDER(new Color(0xDDDDFF)),
    ENTROPY(new Color(0x555577));

    EnumPropolisType(Color color) {
        this.color = color.getRGB();
    }

    private final int color;

    @Override
    public void initializeItem(ItemEnumBased<? extends IEnumItem> itemEnumBased) {
        itemEnumBased.setCreativeTab(MagicBees.creativeTab);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        return color;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return TEXTURE;
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation("forestry", "items/propolis.0");

}
