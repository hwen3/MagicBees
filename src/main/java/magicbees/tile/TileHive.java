package magicbees.tile;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.BeeDefinition;
import magicbees.elec332.corerepack.compat.forestry.bee.BlockHive;
import magicbees.elec332.corerepack.compat.forestry.bee.IHiveEnum;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 19-6-2018
 */
public class TileHive extends forestry.apiculture.tiles.TileHive {

    @Override
    @Nonnull
    @SuppressWarnings("all")
    public IBee getContainedBee() {
        IBee sr = super.getContainedBee();
        if (sr == null || sr.getGenome() == BeeDefinition.FOREST.getGenome()){
            return BeeManager.beeRoot.getBee(ObjectUtils.firstNonNull(getBeeFromBlock(), BeeDefinition.FOREST.getGenome()));
        }
        return sr;
    }

    @Nullable
    @SuppressWarnings("all")
    public IBeeGenome getBeeFromBlock(){
        if (world.isBlockLoaded(pos)) {
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block instanceof BlockHive<?>) {
                IHiveEnum hiveType = blockState.getValue(((BlockHive<?>) block).getProperty());
                IAllele[] template = BeeManager.beeRoot.getTemplate(hiveType.getBeeUid());
                if (template != null) {
                    return BeeManager.beeRoot.templateAsGenome(template);
                }
            }
        }
        return null;
    }

}
