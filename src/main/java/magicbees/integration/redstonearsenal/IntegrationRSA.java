package magicbees.integration.redstonearsenal;

import magicbees.MagicBees;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.bees.BeeIntegrationInterface;
import magicbees.util.ModNames;
import net.minecraft.item.ItemStack;

/**
 * Created by Elec332 on 19-5-2017.
 */
@MagicBeesModule(owner = MagicBees.modid, name = "RedstoneArsenal Integration", modDependencies = ModNames.RSA)
public class IntegrationRSA implements IMagicBeesModule {

	@Override
	public void init(IMagicBeesInitialisationEvent event){
		BeeIntegrationInterface.itemRSAFluxedElectrumNugget = new ItemStack(event.getItem("material"), 1, 64);
		BeeIntegrationInterface.blockRSAFluxedElectrum = event.getBlock("storage").getDefaultState();
	}

}
