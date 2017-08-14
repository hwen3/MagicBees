package magicbees.bees.mutation;

import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import magicbees.elec332.corerepack.util.MoonPhase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Elec332 on 11-2-2017.
 */
public class MoonPhaseMutationRestriction extends AbstractMoonPhaseMutationCondition {

    public MoonPhaseMutationRestriction(MoonPhase phase) {
        this(phase, phase);
    }

    public MoonPhaseMutationRestriction(MoonPhase start, MoonPhase end) {
        super(start, end);
    }

    @Override
    public float getChance(World world, BlockPos blockPos, IAllele iAllele, IAllele iAllele1, IGenome iGenome, IGenome iGenome1, IClimateProvider iClimateProvider) {
        return isBetweenPhases(world) ? 1 : 0;
    }

}
