package magicbees.integration.thermalexpansion;

import com.google.common.base.Preconditions;
import elec332.core.api.module.ElecModule;
import elec332.core.item.ItemEnumBased;
import magicbees.MagicBees;
import magicbees.init.ItemRegister;
import magicbees.item.types.EnumDropType;
import magicbees.util.ModNames;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * Created by Elec332 on 16-5-2017.
 */
@ElecModule(owner = MagicBees.modid, name = "ThermalExpansion Integration", modDependencies = ModNames.THERMALEXPANSION)
public class IntegrationThermalExpansion {

	@ElecModule.EventHandler
	public void init(FMLPostInitializationEvent event){
		ItemEnumBased<EnumDropType> drops = Preconditions.checkNotNull(ItemRegister.dropItem);
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.CARBON), FluidRegistry.getFluid("coal"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.DESTABILIZED), FluidRegistry.getFluid("redstone"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.ENDEARING), FluidRegistry.getFluid("ender"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.LUX), FluidRegistry.getFluid("glowstone"));
	}

	private static void addCrucibleRecipe(ItemStack in, Fluid out){
		addCrucibleRecipe(in, new FluidStack(Preconditions.checkNotNull(out), 50));
	}

	private static void addCrucibleRecipe(ItemStack in, FluidStack out){
		NBTTagCompound main = new NBTTagCompound();
		main.setInteger("energy", 4000);
		main.setTag("input", Preconditions.checkNotNull(in).writeToNBT(new NBTTagCompound()));
		main.setTag("output", Preconditions.checkNotNull(out).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage(ModNames.THERMALEXPANSION, "addcruciblerecipe", main);
	}

}
