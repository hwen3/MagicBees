package magicbees.elec332.corerepack.item;

import forestry.core.items.IColoredItem;
import forestry.core.proxy.Proxies;
import magicbees.util.Utils;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 21-8-2016.
 */
public class ItemEnumBased<E extends Enum<E> & IEnumItem> extends Item implements IColoredItem {

    public ItemEnumBased(ResourceLocation rl, Class<E> clazz) {
        super();
        setRegistryName(rl);
        Utils.setUnlocalizedName(this);
        this.clazz = clazz;
        this.values = clazz.getEnumConstants();
        this.setHasSubtypes(true);
        this.values[0].initializeItem(this);
        Proxies.common.registerItem(this);
    }

    @SuppressWarnings("all")
    protected final Class<E> clazz;
    protected final E[] values;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[][] textures;
    @SideOnly(Side.CLIENT)
    private IBakedModel[] models;

    public ItemStack getStackFromType(E type) {
        return getStackFromType(type, 1);
    }

    public ItemStack getStackFromType(E type, int amount) {
        return new ItemStack(this, amount, type.ordinal());
    }

    @Override
    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
        int i = stack.getItemDamage();
        return i >= values.length ? -1 : values[i].getColorFromItemStack(stack, tintIndex);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        for (E e : values) {
            if (e.shouldShow()) {
                subItems.add(getStackFromType(e));
            }
        }
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        E e = stack == null ? null : get(stack.getItemDamage());
        if (e == null) {
            return super.getUnlocalizedName(stack);
        }
        return e.getUnlocalizedName(stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        if (values.length <= super.getDamage(stack)) {
            stack.setItemDamage(0);
        }
        return super.getDamage(stack);
    }


    private E get(int i) {
        return i >= values.length ? null : values[i];
    }

}
