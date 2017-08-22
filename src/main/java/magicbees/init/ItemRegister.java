package magicbees.init;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import magicbees.bees.EnumBeeModifiers;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.item.*;
import magicbees.item.types.*;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.Map;

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
    public static ItemMagicBeesFrame magicFrame, resilientFrame, gentleFrame, metabolicFrame, necroticFrame, temporalFrame, oblivionFrame;
    public static Item moonDial, ironNugget;
    public static Item manasteelgrafter, manasteelScoop;
    public static ItemMysteriousMagnet mysteriousMagnet;

    private static Map<EnumBeeModifiers, ItemMagicBeesFrame> frames;

    public static void preInit(){
        combItem = new ItemEnumBased<>(new MagicBeesResourceLocation("beeComb"), EnumCombType.class);
        dropItem = new ItemEnumBased<>(new MagicBeesResourceLocation("drop"), EnumDropType.class);
        pollenItem = new ItemEnumBased<>(new MagicBeesResourceLocation("pollen"), EnumPollenType.class);
        propolisItem = new ItemEnumBased<>(new MagicBeesResourceLocation("propolis"), EnumPropolisType.class);
        waxItem = new ItemEnumBased<>(new MagicBeesResourceLocation("wax"), EnumWaxType.class);
        resourceItem = new ItemEnumBased<>(new MagicBeesResourceLocation("resource"), EnumResourceType.class);
        orePartItem = new ItemEnumBased<>(new MagicBeesResourceLocation("orepart"), EnumNuggetType.class);

        Map<EnumBeeModifiers, ItemMagicBeesFrame> framez = Maps.newHashMap();
        framez.put(EnumBeeModifiers.MAGIC, magicFrame = new ItemMagicBeesFrame(EnumBeeModifiers.MAGIC));
        framez.put(EnumBeeModifiers.RESILIENT, resilientFrame = new ItemMagicBeesFrame(EnumBeeModifiers.RESILIENT));
        framez.put(EnumBeeModifiers.GENTLE, gentleFrame = new ItemMagicBeesFrame(EnumBeeModifiers.GENTLE));
        framez.put(EnumBeeModifiers.METABOLIC, metabolicFrame = new ItemMagicBeesFrame(EnumBeeModifiers.METABOLIC));
        framez.put(EnumBeeModifiers.NECROTIC, necroticFrame = new ItemMagicBeesFrame(EnumBeeModifiers.NECROTIC));
        framez.put(EnumBeeModifiers.TEMPORAL, temporalFrame = new ItemMagicBeesFrame(EnumBeeModifiers.TEMPORAL));
        framez.put(EnumBeeModifiers.OBLIVION, oblivionFrame = new ItemMagicBeesFrame(EnumBeeModifiers.OBLIVION));
        frames = Collections.unmodifiableMap(framez);

        mysteriousMagnet = new ItemMysteriousMagnet();
        moonDial = new ItemMoonDial();

        manasteelgrafter = new ItemManaSteelGrafter();
        manasteelScoop = new ItemManaSteelScoop();

        fixIronNuggetStuff();

    }

    public static void registerOreDictionary(){
        for (EnumCombType comb : EnumCombType.values()){
            OreDictionary.registerOre("beeComb", combItem.getStackFromType(comb));
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(combItem, dropItem, pollenItem, propolisItem, waxItem, resourceItem, orePartItem);
        event.getRegistry().registerAll(mysteriousMagnet, moonDial);
        event.getRegistry().registerAll(manasteelgrafter, manasteelScoop);
        for (Item item : getBeeFrames().values()){
            event.getRegistry().register(item);
        }



    }

    public static Map<EnumBeeModifiers, ItemMagicBeesFrame> getBeeFrames() {
        return frames;
    }

    private static void fixIronNuggetStuff(){
        ironNugget = Preconditions.checkNotNull(Items.IRON_NUGGET);
    }

}
