package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by Elec332 on 13-2-2017.
 */
public enum EnumWaxType implements IEnumItem{

    MAGIC(new Color(0xd242df)),
    SOUL(new Color(0x967C63)){

        @Override
        public ResourceLocation getTextureLocation() {
            return TEXTURE;
        }

    },
    AMNESIC(new Color(0x856DFF))

    ;

    EnumWaxType(Color color){
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
        return TEXTURE_S;
    }

    private static final ResourceLocation TEXTURE, TEXTURE_S;

    static {
        TEXTURE = new MagicBeesResourceLocation("items/wax.0");
        TEXTURE_S = new MagicBeesResourceLocation("items/wax.1");
    }

}
