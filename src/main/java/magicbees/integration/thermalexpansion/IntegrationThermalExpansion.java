package magicbees.integration.thermalexpansion;

import com.google.common.base.Preconditions;
import magicbees.MagicBees;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.bees.BeeIntegrationInterface;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectSpawnMob;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.init.ItemRegister;
import magicbees.item.types.EnumDropType;
import magicbees.util.ModNames;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Created by Elec332 on 16-5-2017.
 */
@MagicBeesModule(owner = MagicBees.modid, name = "ThermalExpansion Integration", modDependencies = ModNames.THERMALEXPANSION)
public class IntegrationThermalExpansion implements IMagicBeesModule {

	@Override
	public void init(IMagicBeesInitialisationEvent event){
		ItemEnumBased<EnumDropType> drops = Preconditions.checkNotNull(ItemRegister.dropItem);
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.CARBON), FluidRegistry.getFluid("coal"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.DESTABILIZED), FluidRegistry.getFluid("redstone"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.ENDEARING), FluidRegistry.getFluid("ender"));
		addCrucibleRecipe(drops.getStackFromType(EnumDropType.LUX), FluidRegistry.getFluid("glowstone"));
		BeeIntegrationInterface.effectSpawnBasalz = new AlleleEffectSpawnMob(BeeIntegrationInterface.te_spawnBasalz_name, new ResourceLocation("thermalfoundation:basalz"));
		BeeIntegrationInterface.effectSpawnBlitz = new AlleleEffectSpawnMob(BeeIntegrationInterface.te_spawnBlitz_name, new ResourceLocation("thermalfoundation:blitz"));
		BeeIntegrationInterface.effectSpawnBlizz = new AlleleEffectSpawnMob(BeeIntegrationInterface.te_spawnBlizz_name, new ResourceLocation("thermalfoundation:blizz"));
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
