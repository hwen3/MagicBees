package magicbees.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 13-2-2017.
 */
public interface ICrumblingHandler {

    void addCrumblingHandler(@Nonnull ItemStack before, @Nonnull ItemStack after);

    void addCrumblingHandler(@Nonnull ItemStack before, @Nonnull IBlockState after);

    boolean crumble(World world, BlockPos pos);

}
