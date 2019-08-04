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
public enum EnumPollenType implements IEnumItem {

    UNUSUAL(new Color(0xD8417B), new Color(0xA03059)),
    PHASED(new Color(0x4974B4), new Color(0x456BA5));

    EnumPollenType(Color firstColor, Color secondColor) {
        this.firstColor = firstColor.getRGB();
        this.secondColor = secondColor.getRGB();
    }

    private final int firstColor, secondColor;

    @Override
    public void initializeItem(ItemEnumBased<? extends IEnumItem> item) {
        item.setCreativeTab(MagicBees.creativeTab);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? firstColor : secondColor;
    }

    @Override
    public ResourceLocation[] getTextures() {
        return textures;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return null;
    }

    private static final ResourceLocation[] textures;

    static {
        textures = new ResourceLocation[]{
                new ResourceLocation("forestry", "items/pollen.0"), new ResourceLocation("forestry", "items/pollen.1")
        };
    }

}
