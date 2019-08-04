package magicbees.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import magicbees.api.ICrumblingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Elec332 on 13-2-2017.
 */
public class DefaultCrumblingHandler implements ICrumblingHandler {

    public DefaultCrumblingHandler() {
        this.crumbleMap = Maps.newHashMap();
    }

    private final Map<ItemStack, IBlockState> crumbleMap;

    @Override
    @SuppressWarnings("all")
    public void addCrumblingHandler(@Nonnull ItemStack before, @Nonnull ItemStack after) {
        if (after.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Block block = Block.getBlockFromItem(after.getItem());
        if (block == null || block == Blocks.AIR) {
            throw new IllegalArgumentException();
        }
        addCrumblingHandler(before, block.getStateFromMeta(after.getMetadata()));
    }

    @Override
    public void addCrumblingHandler(@Nonnull ItemStack before, @Nonnull IBlockState after) {
        if (before.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (ItemStack stack : crumbleMap.keySet()) {
            if (OreDictionary.itemMatches(before, stack, false)) {
                return;
            }
        }
        crumbleMap.put(before, Preconditions.checkNotNull(after));
    }

    @Override
    public boolean crumble(World world, BlockPos pos) {
        if (!world.isAirBlock(pos)) {
            IBlockState state = world.getBlockState(pos);
            ItemStack source = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));

            for (ItemStack stack : crumbleMap.keySet()) {
                if (OreDictionary.itemMatches(source, stack, false)) {
                    IBlockState target = crumbleMap.get(stack);
                    world.setBlockState(pos, target, 2);
                    return true;
                }
            }
        }
        return false;
    }

}
