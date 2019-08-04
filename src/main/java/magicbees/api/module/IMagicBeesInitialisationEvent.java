package magicbees.api.module;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 14-8-2017.
 */
public interface IMagicBeesInitialisationEvent {

    @Nonnull
    Block getBlock(String name);

    @Nonnull
    Item getItem(String name);

    @Nonnull
    Block getBlock(String mod, String name);

    @Nonnull
    Item getItem(String mod, String name);

}
