package magicbees.inventory;

import magicbees.inventory.container.ContainerEffectJar;
import magicbees.inventory.gui.GuiEffectJar;
import magicbees.tile.TileEntityEffectJar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Elec332 on 16-8-2017.
 */
public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case ContainerId.EFFECT_JAR:
                if (!(tile instanceof TileEntityEffectJar)) {
                    return null;
                }
                return new ContainerEffectJar((TileEntityEffectJar) tile, player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Container container = getServerGuiElement(ID, player, world, x, y, z);
        switch (ID) {
            case ContainerId.EFFECT_JAR:
                if (!(container instanceof ContainerEffectJar)) {
                    return null;
                }
                return new GuiEffectJar((ContainerEffectJar) container);
            default:
                return null;
        }
    }

}
