package magicbees.util;

import magicbees.elec332.corerepack.compat.forestry.IIndividualBranch;
import magicbees.elec332.corerepack.compat.forestry.bee.BeeGenomeTemplate;

import java.awt.*;

/**
 * Created by Elec332 on 19-8-2016.
 */
public interface IMagicBeesBranch extends IIndividualBranch<BeeGenomeTemplate> {

    public boolean enabled();

    public Color getSecondaryColor();

}
