package magicbees.util;

import com.google.common.base.Preconditions;
import forestry.api.genetics.IAlleleSpeciesBuilder;
import forestry.apiculture.blocks.BlockRegistryApiculture;
import forestry.apiculture.items.ItemRegistryApiculture;
import forestry.core.blocks.BlockRegistryCore;
import forestry.core.items.ItemRegistryCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Created by Elec332 on 15-5-2017.
 */
public class Utils {

	public static ItemRegistryApiculture getApicultureItems(){
		return itemsA;
	}

	public static BlockRegistryApiculture getApicultureBlocks(){
		return blocksA;
	}

	public static ItemRegistryCore getCoreItems(){
		return itemsC;
	}

	public static BlockRegistryCore getCoreBlocks(){
		return blocksC;
	}

	@Nonnull
	public static Block getBlock(String mod, String name){
		return Preconditions.checkNotNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(mod, name)));
	}

	@Nonnull
	public static Item getItem(String mod, String name){
		return Preconditions.checkNotNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, name)));
	}

	public static void setSecret(IAlleleSpeciesBuilder builder){
		if (/*!FMLUtil.developmentEnvironment ||*/ !Config.showAllBees) {
			builder.setIsSecret();
		}
	}

	public static AxisAlignedBB getAABB(BlockPos pos, int range, boolean y){
		int xCoord = pos.getX(), yCoord = pos.getY(), zCoord = pos.getZ();
		return new AxisAlignedBB(xCoord - range, yCoord - (y ? range : 0), zCoord - range, xCoord + range + 1, yCoord + 1 + (y ? range : 0), zCoord + range + 1);
	}

	public static void setUnlocalizedName(Item item){
		item.setUnlocalizedName(getUnlocalizedName(item));
	}

	public static void setUnlocalizedName(Block block){
		block.setUnlocalizedName(getUnlocalizedName(block));
	}

	@SuppressWarnings("all")
	public static String getUnlocalizedName(IForgeRegistryEntry<?> object){
		return object.getRegistryName().toString().replace(":", ".").toLowerCase();
	}

	private static Object getStatic(Field field) throws IllegalAccessException {
		field.setAccessible(true);
		return field.get(null);
	}

	private static final ItemRegistryApiculture itemsA;
	private static final BlockRegistryApiculture blocksA;
	private static final ItemRegistryCore itemsC;
	private static final BlockRegistryCore blocksC;

	static {
		Class apiculture;
		Class core;
		try {
			//Test for old forestry version.
			apiculture = Class.forName("forestry.apiculture.PluginApiculture");
			core = Class.forName("forestry.core.PluginCore");
		}catch (ClassNotFoundException e){
			try {
				apiculture = Class.forName("forestry.apiculture.ModuleApiculture");
				core = Class.forName("forestry.core.ModuleCore");
			}catch (ClassNotFoundException ex){
				throw new RuntimeException(ex);
			}
		}
		try {
			itemsA = Preconditions.checkNotNull((ItemRegistryApiculture) getStatic(apiculture.getDeclaredField("items")));
			blocksA = Preconditions.checkNotNull((BlockRegistryApiculture) getStatic(apiculture.getDeclaredField("blocks")));
			itemsC = Preconditions.checkNotNull((ItemRegistryCore) getStatic(core.getDeclaredField("items")));
			blocksC = Preconditions.checkNotNull((BlockRegistryCore) getStatic(core.getDeclaredField("blocks")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
