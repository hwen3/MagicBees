package magicbees.init;

import magicbees.MagicBees;
import magicbees.block.BlockEffectJar;
import magicbees.block.BlockEnchantedEarth;
import magicbees.block.BlockHive;
import magicbees.tile.TileEntityEffectJar;
import magicbees.tile.TileHive;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-3-2017.
 */
public final class BlockRegister {

    public static BlockHive hiveBlock;
    public static Block effectJar;
    public static Block enchantedEarth;

    public static void preInit(){
        hiveBlock = (BlockHive) new BlockHive().setRegistryName(new MagicBeesResourceLocation("hiveBlock")).setCreativeTab(MagicBees.creativeTab);
        effectJar = new BlockEffectJar().setRegistryName(new MagicBeesResourceLocation("effectJar")).setCreativeTab(MagicBees.creativeTab);
        enchantedEarth = new BlockEnchantedEarth().setRegistryName(new MagicBeesResourceLocation("enchanted_earth")).setCreativeTab(MagicBees.creativeTab);

        Utils.setUnlocalizedName(effectJar);
        Utils.setUnlocalizedName(hiveBlock);
        Utils.setUnlocalizedName(enchantedEarth);

        registerTiles();
    }

    private static void registerTiles(){
        GameRegistry.registerTileEntity(TileEntityEffectJar.class, "TileEntityMagicBeesEffectJar");
        GameRegistry.registerTileEntity(TileHive.class, "tileMagicBeesHive");
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().register(hiveBlock);
        event.getRegistry().register(effectJar);
        event.getRegistry().register(enchantedEarth);
    }

    @SubscribeEvent
    @SuppressWarnings("all")
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(new ItemBlock(hiveBlock){

            @Override
            public String getUnlocalizedName(ItemStack stack) {
                return hiveBlock.getUnlocalizedName(stack);
            }

            @Override
            public int getMetadata(int damage) {
                return damage;
            }

        }.setHasSubtypes(true).setRegistryName(hiveBlock.getRegistryName()), createItem(effectJar));
        event.getRegistry().register(createItem(enchantedEarth));
    }

    @SuppressWarnings("all")
    private static Item createItem(Block block){
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

}
