package magicbees.bees.mutation;

import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import magicbees.elec332.corerepack.util.MoonPhase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Elec332 on 23-8-2016.
 */
public class MoonPhaseMutationBonus extends AbstractMoonPhaseMutationCondition {

    public MoonPhaseMutationBonus(MoonPhase moonPhaseStart, MoonPhase moonPhaseEnd, float bonus) {
        super(moonPhaseStart, moonPhaseEnd);
        this.bonus = bonus;
    }

    private final float bonus;

    @Override
    public float getChance(World world, BlockPos blockPos, IAllele iAllele, IAllele iAllele1, IGenome iGenome, IGenome iGenome1, IClimateProvider iClimateProvider) {
        return isBetweenPhases(world) ? bonus : 1;
    }

}
