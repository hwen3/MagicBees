package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 19-12-2016.
 */
public enum EnumDropType implements IEnumItem {

    ENCHANTED(0x6e1c6d, 0xff8fff),
    INTELLECT(0x25914D, 0x18E072),
    DESTABILIZED(0xCC002C, 0x6B0118),
    CARBON(0x454545, 0x0F0F0F),
    LUX(0xF5F3A4, 0xC9C87D),
    ENDEARING(0x12E3D9, 0x069E97);

    EnumDropType(int c1, int c2){
        this.firstColor = c1;
        this.secondColor = c2;
    }

    private final int firstColor, secondColor;

    @Override
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? firstColor : secondColor;
    }

    @Override
    public void initializeItem(ItemEnumBased<? extends IEnumItem> itemEnumBased) {
        itemEnumBased.setCreativeTab(MagicBees.creativeTab);
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
        String drop = "honey_drop";
        textures = new ResourceLocation[]{
                new ResourceLocation("forestry", "items/" + drop + ".0"), new ResourceLocation("forestry", "items/" + drop + ".1")
        };
    }

}
