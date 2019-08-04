package magicbees.inventory.container;

import magicbees.tile.TileEntityEffectJar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 16-8-2017.
 */
public class ContainerEffectJar extends Container {

    public ContainerEffectJar(TileEntityEffectJar jar, EntityPlayer player) {
        this.jar = jar;

        this.addSlotToContainer(new SlotItemHandler(jar.getInventory(), 0, 80, 22));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(player.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 74 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(player.inventory, l, 8 + l * 18, 132));
        }
    }

    private final TileEntityEffectJar jar;

    public TileEntityEffectJar getJar() {
        return jar;
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        ItemStack ret = super.slotClick(slotId, dragType, clickTypeIn, player);
        if (!jar.getWorld().isRemote) {
            jar.markDirty();
        }
        return ret;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        BlockPos pos = jar.getPos();
        return playerIn.getDistance(pos.getX(), pos.getY(), pos.getZ()) < 8;
    }

}
