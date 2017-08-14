package magicbees.init;

import com.google.common.base.Preconditions;
import magicbees.bees.EnumBeeModifiers;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.item.*;
import magicbees.item.types.*;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by Elec332 on 4-3-2017.
 */
public final class ItemRegister {

    public static ItemEnumBased<EnumCombType> combItem;
    public static ItemEnumBased<EnumDropType> dropItem;
    public static ItemEnumBased<EnumPollenType> pollenItem;
    public static ItemEnumBased<EnumPropolisType> propolisItem;
    public static ItemEnumBased<EnumWaxType> waxItem;
    public static ItemEnumBased<EnumResourceType> resourceItem;
    public static ItemEnumBased<EnumNuggetType> orePartItem;
    //frames
    public static Item magicFrame, resilientFrame, gentleFrame, metabolicFrame, necroticFrame, temporalFrame, oblivionFrame;
    public static Item mysteriousMagnet, moonDial, ironNugget;
    public static Item manasteelgrafter, manasteelScoop;

    public static void init(){
        combItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("beeComb"), EnumCombType.class));
        dropItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("drop"), EnumDropType.class));
        pollenItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("pollen"), EnumPollenType.class));
        propolisItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("propolis"), EnumPropolisType.class));
        waxItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("wax"), EnumWaxType.class));
        resourceItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("resource"), EnumResourceType.class));
        orePartItem = register(new ItemEnumBased<>(new MagicBeesResourceLocation("orepart"), EnumNuggetType.class));

        magicFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.MAGIC));
        resilientFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.RESILIENT));
        gentleFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.GENTLE));
        metabolicFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.METABOLIC));
        necroticFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.NECROTIC));
        temporalFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.TEMPORAL));
        oblivionFrame = register(new ItemMagicBeesFrame(EnumBeeModifiers.OBLIVION));

        mysteriousMagnet = register(new ItemMysteriousMagnet());
        moonDial = register(new ItemMoonDial());

        manasteelgrafter = register(new ItemManaSteelGrafter());
        manasteelScoop = register(new ItemManaSteelScoop());

        fixIronNuggetStuff();

        for (EnumCombType comb : EnumCombType.values()){
            OreDictionary.registerOre("beeComb", combItem.getStackFromType(comb));
        }

    }

    @SuppressWarnings("all")
    public static <K extends IForgeRegistryEntry<?>> K register(K object) {
        return (K) GameData.register_impl((IForgeRegistryEntry)object);
    }

    private static void fixIronNuggetStuff(){
        ironNugget = Preconditions.checkNotNull(Items.IRON_NUGGET);
    }

}
