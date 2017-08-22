package magicbees.init;

import magicbees.MagicBees;
import magicbees.block.BlockEffectJar;
import magicbees.block.BlockHive;
import magicbees.tile.TileEntityEffectJar;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Created by Elec332 on 4-3-2017.
 */
public final class BlockRegister {

    public static BlockHive hiveBlock;
    public static Block effectJar;

    public static void preInit(){
        hiveBlock = (BlockHive) new BlockHive().setRegistryName(new MagicBeesResourceLocation("hiveBlock")).setCreativeTab(MagicBees.creativeTab);
        effectJar = new BlockEffectJar().setCreativeTab(MagicBees.creativeTab).setRegistryName(new MagicBeesResourceLocation("effectJar"));

        Utils.setUnlocalizedName(effectJar);
        Utils.setUnlocalizedName(hiveBlock);

        registerTiles();
    }

    private static void registerTiles(){
        GameRegistry.registerTileEntity(TileEntityEffectJar.class, "TileEntityMagicBeesEffectJar");
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(hiveBlock, effectJar);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(new ItemBlock(hiveBlock), new ItemBlock(effectJar));
    }

}
