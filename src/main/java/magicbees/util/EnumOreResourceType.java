package magicbees.util;

import com.google.common.base.Preconditions;
import magicbees.init.ItemRegister;
import magicbees.item.types.EnumNuggetType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Elec332 on 15-5-2017.
 */
public enum EnumOreResourceType {

    IRON(new ItemStack(ItemRegister.ironNugget), "nuggetIron"),
    GOLD(new ItemStack(Items.GOLD_NUGGET), "nuggetGold"),
    COPPER(EnumNuggetType.COPPER, "nuggetCopper"),
    TIN(EnumNuggetType.TIN, "nuggetTin"),
    SILVER("nuggetSilver", "dustTinySilver"),
    LEAD("nuggetLead", "dustTinyLead"),
    ALUMINIUM("nuggetAluminium", "nuggetAluminum"),
    ARDITE("nuggetArdite"),
    COBALT("nuggetCobalt"),
    MANYULLYN("nuggetManyullyn"),
    OSMIUM("nuggetOsmium"),
    DIAMOND(EnumNuggetType.DIAMOND, "nuggetDiamond") {
        @Override
        public String getType() {
            return "gem";
        }

    },
    EMERALD(EnumNuggetType.EMERALD, "nuggetEmerald") {
        @Override
        public String getType() {
            return "gem";
        }

    },
    APATITE(EnumNuggetType.APATITE, "nuggetApatite") {
        @Override
        public String getType() {
            return "gem";
        }

    },
    SILICON("itemSilicon"),
    CERTUS("crystalCertusQuartz"),
    FLUIX("crystalFluix"),
    PLATINUM("nuggetPlatinum"),
    NICKEL("nuggetNickel", "nuggetFerrous"),
    BRONZE(EnumNuggetType.BRONZE, "nuggetBronze"),
    INVAR("nuggetInvar"),
    ELECTRUM("nuggetElectrum"),
    ;

    EnumOreResourceType(EnumNuggetType nugget, String... oreDictA) {
        this(nugget.getStack(), oreDictA);
    }

    EnumOreResourceType(String... oreDictA) {
        this((ItemStack) null, oreDictA);
    }

    EnumOreResourceType(@Nullable ItemStack stack, String... oreDictA) {
        if (oreDictA == null) {
            oreDictA = new String[0];
        }
        setStack(stack);
        this.oreDictA = oreDictA;
    }

    private String[] oreDictA;
    private ItemStack finalStack;

    public String getType() {
        return "ingot";
    }

    private void setStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            stack = null;
        }
        this.finalStack = stack;
    }

    public boolean enabled() {
        getStack();
        return finalStack != null;
    }

    public ItemStack getStack() {
        if (oreDictA != null) {
            primLoop:
            for (String oreDict : oreDictA) {
                List<ItemStack> l = OreDictionary.getOres(oreDict);
                if (!l.isEmpty()) {
                    for (ItemStack stack_ : l) {
                        if (!stack_.isEmpty()) {
                            setStack(stack_.copy());
                            break primLoop;
                        }
                    }
                }
            }
            oreDictA = null;
        }
        return finalStack == null ? ItemStack.EMPTY : finalStack;
    }

    public static void registerRecipes(IForgeRegistry<IRecipe> recipes) {
        for (EnumOreResourceType type : EnumOreResourceType.values()) {
            if (type.oreDictA.length > 0) {
                if (type.finalStack != null && !type.finalStack.isEmpty()) {
                    if (!(type.finalStack.getItem() == ItemRegister.ironNugget && Preconditions.checkNotNull(ItemRegister.ironNugget.getRegistryName()).getNamespace().equals("minecraft"))) {
                        for (String s : type.oreDictA) {
                            if (s.startsWith("nugget")) {
                                OreDictionary.registerOre(s, type.finalStack);
                            }
                        }
                        if (type.oreDictA[0].startsWith("nugget")) {
                            List<ItemStack> scks = OreDictionary.getOres(type.oreDictA[0].replace("nugget", type.getType()));
                            if (scks.size() > 0 && !scks.get(0).isEmpty()) {
                                ResourceLocation name = new MagicBeesResourceLocation(type.oreDictA[0] + "_to_block");
                                IRecipe recipe = new ShapedOreRecipe(null, scks.get(0), "XXX", "XXX", "XXX", 'X', type.oreDictA[0]).setRegistryName(name);
                                recipes.register(recipe);
                            }
                        }
                    }
                }
            }
        }
    }

}
