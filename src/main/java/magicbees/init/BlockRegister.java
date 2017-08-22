package magicbees.init;

import magicbees.MagicBees;
import magicbees.block.BlockEffectJar;
import magicbees.block.BlockHive;
import magicbees.tile.TileEntityEffectJar;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Created by Elec332 on 4-3-2017.
 */
public final class BlockRegister {

    public static BlockHive hiveBlock;
    public static Block effectJar;

    public static void init(){
        hiveBlock = new BlockHive().register(new MagicBeesResourceLocation("hiveBlock"));
        hiveBlock.setCreativeTab(MagicBees.creativeTab);
        effectJar = new BlockEffectJar().setRegistryName(new MagicBeesResourceLocation("effectJar"));
        ForgeRegistries.BLOCKS.register(effectJar);
        effectJar.setCreativeTab(MagicBees.creativeTab);
        ForgeRegistries.ITEMS.register(new ItemBlock(effectJar).setCreativeTab(MagicBees.creativeTab).setRegistryName(effectJar.getRegistryName()));
        registerTiles();
    }

    private static void registerTiles(){
        GameRegistry.registerTileEntity(TileEntityEffectJar.class, "TileEntityMagicBeesEffectJar");
    }

}
