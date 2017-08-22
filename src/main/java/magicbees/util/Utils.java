package magicbees.util;

import com.google.common.base.Preconditions;
import forestry.api.genetics.IAlleleSpeciesBuilder;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.blocks.BlockRegistryApiculture;
import forestry.apiculture.items.ItemRegistryApiculture;
import forestry.core.PluginCore;
import forestry.core.blocks.BlockRegistryCore;
import forestry.core.items.ItemRegistryCore;
import magicbees.elec332.corerepack.util.FMLUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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
		//if (!FMLUtil.developmentEnvironment) {
			builder.setIsSecret();
		//}
	}

	public static AxisAlignedBB getAABB(BlockPos pos, int range, boolean y){
		int xCoord = pos.getX(), yCoord = pos.getY(), zCoord = pos.getZ();
		return new AxisAlignedBB(xCoord - range, yCoord - (y ? range : 0), zCoord - range, xCoord + range + 1, yCoord + 1 + (y ? range : 0), zCoord + range + 1);
	}

	@SuppressWarnings("all")
	public static void setUnlocalizedName(Item item){
		item.setUnlocalizedName(item.getRegistryName().toString().replace(":", ".").toLowerCase());
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
		try {
			itemsA = Preconditions.checkNotNull((ItemRegistryApiculture) getStatic(PluginApiculture.class.getDeclaredField("items")));
			blocksA = Preconditions.checkNotNull((BlockRegistryApiculture) getStatic(PluginApiculture.class.getDeclaredField("blocks")));
			itemsC = Preconditions.checkNotNull((ItemRegistryCore) getStatic(PluginCore.class.getDeclaredField("items")));
			blocksC = Preconditions.checkNotNull((BlockRegistryCore) getStatic(PluginCore.class.getDeclaredField("blocks")));
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}

}
