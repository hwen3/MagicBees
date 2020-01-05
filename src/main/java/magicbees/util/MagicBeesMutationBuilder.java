package magicbees.util;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.BeeMutation;

/**
 * Created by Elec332 on 5-1-2020
 */
public class MagicBeesMutationBuilder extends BeeMutation {

    public MagicBeesMutationBuilder(IAlleleBeeSpecies bee0, IAlleleBeeSpecies bee1, IAllele[] result, int chance) {
        super(bee0, bee1, result, chance);
    }

    public MagicBeesMutationBuilder requireResource(EnumOreResourceType ore) {
        super.requireResource(ore.getBlockName());
        return this;
    }

}
