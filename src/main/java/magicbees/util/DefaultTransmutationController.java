package magicbees.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import magicbees.api.ITransmutationController;
import magicbees.api.ITransmutationHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Set;

/**
 * Created by Elec332 on 13-2-2017.
 */
public class DefaultTransmutationController implements ITransmutationController {

    public DefaultTransmutationController(ITransmutationHandler... handlers) {
        this.transmutationHandlers = Sets.newHashSet();
        if (handlers != null) {
            for (ITransmutationHandler handler : handlers) {
                addTransmutationHandler(handler);
            }
        }
    }

    private final Set<ITransmutationHandler> transmutationHandlers;

    @Override
    public boolean addTransmutationHandler(ITransmutationHandler transmutationHandler) {
        return transmutationHandlers.add(Preconditions.checkNotNull(transmutationHandler));
    }

    @Override
    public boolean transmute(World world, BlockPos pos) {
        if (!world.isAirBlock(pos)) {
            IBlockState state = world.getBlockState(pos);
            ItemStack source = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
            if (source.isEmpty()) {
                return false;
            }
            Biome biome = world.getBiome(pos);

            for (ITransmutationHandler transmutationHandler : transmutationHandlers) {
                if (transmutationHandler.transmute(world, pos, source, biome)) {
                    return true;
                }
            }

        }
        return false;
    }

}
