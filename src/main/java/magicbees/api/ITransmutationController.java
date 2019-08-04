package magicbees.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Elec332 on 13-2-2017.
 */
public interface ITransmutationController {

    boolean addTransmutationHandler(ITransmutationHandler transmutationHandler);

    boolean transmute(World world, BlockPos pos);

}
