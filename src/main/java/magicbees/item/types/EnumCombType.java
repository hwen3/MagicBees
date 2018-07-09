package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by Elec332 on 23-8-2016.
 */
public enum EnumCombType implements IEnumItem {

    MUNDANE(true, new Color(0xFF9859), new Color(0xFFC58E)),
    MOLTEN(true, new Color(0xcc3333), new Color(0x1E160E)),
    OCCULT(true, new Color(0x9872FF), new Color(0x2D2D2D)),
    OTHERWORLDLY(true, new Color(0x3EE0D8), new Color(0x3A3820)),
    TRANSMUTED(true, new Color(0xE5425D), new Color(0x323291)),

    PAPERY(true, new Color(0xBCA664), new Color(0x35332E)),
    SOUL(true, new Color(0x7F7171), new Color(0x876D53)),
    FURTIVE(false, new Color(0xB7ACB7), new Color(0x636363)),
    INTELLECT(false, new Color(0x0092e9), new Color(0x618fff)),
    TEMPORAL(false, new Color(0x2F9381), new Color(0x773C31)),
    FORGOTTEN(false, new Color(0xB191D8), new Color(0x35443B)),

    AIRY(false, new Color(0xffff7e), new Color(0x606308)),
    FIREY(false, new Color(0xff3C01), new Color(0x5B0D10)),
    WATERY(false, new Color(0x0090ff), new Color(0x102F6B)),
    EARTHY(false, new Color(0x00a000), new Color(0x043004)),

    TE_DESTABILIZED(false, new Color(0xCC002C), new Color(0x6B0118)),
    TE_CARBON(false, new Color(0x454545), new Color(0x0F0F0F)),
    TE_LUX(false, new Color(0xF5F3A4), new Color(0xC9C87D)),
    TE_ENDEARING(false, new Color(0x12E3D9), new Color(0x069E97)),

    TC_AIR(false, new Color(0xFFDF14), new Color(0x707019)),
    TC_FIRE(false, new Color(0xE21802), new Color(0x3F0E12)),
    TC_WATER(false, new Color(0x00B6FF), new Color(0x0F373D)),
    TC_EARTH(false, new Color(0x28D328), new Color(0x333004)),
    TC_ORDER(false, new Color(0xDDDDFF), new Color(0x9D9DB5)),
    TC_ENTROPY(false, new Color(0x555577), new Color(0x2D2D56))
    ;

    EnumCombType(boolean shouldShow, Color firstColor, Color secondColor){
        this.shouldShow = shouldShow;
        this.firstColor = firstColor.getRGB();
        this.secondColor = secondColor.getRGB();
    }

    private final boolean shouldShow;
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
    public boolean shouldShow() {
        return shouldShow;
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
        String comb = "bee_combs";
        textures = new ResourceLocation[]{
            new ResourceLocation("forestry", "items/" + comb + ".0"), new ResourceLocation("forestry", "items/" + comb + ".1")
        };
    }

}
