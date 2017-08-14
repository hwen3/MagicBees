package magicbees.bees.mutation;

import forestry.api.climate.IClimateProvider;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutationCondition;
import magicbees.elec332.corerepack.util.MoonPhase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 23-8-2016.
 */
public abstract class AbstractMoonPhaseMutationCondition implements IMutationCondition {

    public AbstractMoonPhaseMutationCondition(MoonPhase moonPhaseStart, MoonPhase moonPhaseEnd){
        this.moonPhaseStart = moonPhaseStart;
        this.moonPhaseEnd = moonPhaseEnd;
        this.two = moonPhaseStart != moonPhaseEnd;
    }

    protected final MoonPhase moonPhaseStart, moonPhaseEnd;
    private final boolean two;

    protected boolean isBetweenPhases(World world){
        return MoonPhase.getMoonPhase(world).isBetween(this.moonPhaseStart, this.moonPhaseEnd);
    }

    @Override
    public abstract float getChance(World world, BlockPos blockPos, IAllele iAllele, IAllele iAllele1, IGenome iGenome, IGenome iGenome1, IClimateProvider iClimateProvider);

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public String getDescription() {
        if (two) {
            return String.format(I18n.translateToLocal("research.bonusPhase"), moonPhaseStart.getLocalizedName(), moonPhaseEnd.getLocalizedName());
        } else {
            return String.format(I18n.translateToLocal("research.bonusPhaseSingle"), moonPhaseStart.getLocalizedName());
        }
    }

}
