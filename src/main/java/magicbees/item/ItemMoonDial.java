package magicbees.item;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.util.MoonPhase;
import magicbees.util.Config;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Elec332 on 4-3-2017.
 */
public class ItemMoonDial extends Item {

    public ItemMoonDial() {
        super();
        setRegistryName(new MagicBeesResourceLocation("moondial"));
        Utils.setUnlocalizedName(this);
        setCreativeTab(MagicBees.creativeTab);
    }

    private static final ResourceLocation[] textureLocs;

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        if (Config.moonDialShowsPhaseInText && !stack.isEmpty() && stack.getItem() == this && player != null){
            tooltip.add("\u00A77" + MoonPhase.getMoonPhase(player).getLocalizedName());
        }
    }

    static {
        textureLocs = new ResourceLocation[MoonPhase.values().length];
        for (int i = 0; i < textureLocs.length; i++) {
            textureLocs[i] = new MagicBeesResourceLocation("items/moondial."+i);
        }
    }

}
