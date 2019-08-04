package magicbees.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/**
 * Created by Elec332 on 13-2-2017.
 */
public interface ITransmutationHandler {

    boolean transmute(World world, BlockPos pos, ItemStack block, Biome biome);

}
