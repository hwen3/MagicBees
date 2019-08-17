package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 4-3-2017.
 */
public enum EnumResourceType implements IEnumItem {

    LORE_FRAGMENT("fragment", true),
    AROMATIC_LUMP("lump", true),
    EXTENDED_FERTILIZER("fertilizer", true),
    SKULL_CHIP("skullChip", true),
    SKULL_FRAGMENT("skullFragment", true),
    DRAGON_DUST("dragonDust", true),
    DRAGON_CHUNK("dragonChunk", true),
    ESSENCE_FALSE_LIFE("essenceLife", true),
    ESSENCE_SHALLOW_GRAVE("essenceDeath", true),
    ESSENCE_LOST_TIME("essenceTime", true),
    ESSENCE_EVERLASTING_DURABILITY("essenceDurability", true),
    ESSENCE_SCORNFUL_OBLIVION("essenceOblivion", true),
    ESSENCE_FICKLE_PERMANENCE("essenceMutable", true),
    DIMENSIONAL_SINGULARITY("dimensionalSingularity", true),

    TC_DUST_AIR("TCairDust", false),
    TC_DUST_FIRE("TCfireDust", false),
    TC_DUST_WATER("TCwaterDust", false),
    TC_DUST_EARTH("TCearthDust", false),
    TC_DUST_ORDER("TCorderDust", false),
    TC_DUST_ENTROPY("TCchaosDust", false),
    ;

    EnumResourceType(String name, boolean show) {
        this.name = name;
        this.show = show;
    }

    private final String name;
    private final boolean show;

    @Override
    public void initializeItem(ItemEnumBased<? extends IEnumItem> itemEnumBased) {
        itemEnumBased.setCreativeTab(MagicBees.creativeTab);
    }

    @Override
    public boolean shouldShow() {
        return show || MagicBees.debug;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return new MagicBeesResourceLocation("items/" + name.toLowerCase());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return stack.getItem().getUnlocalizedName(null) + "." + name;
    }

}
