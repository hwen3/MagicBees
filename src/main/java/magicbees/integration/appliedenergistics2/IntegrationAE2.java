package magicbees.integration.appliedenergistics2;

import magicbees.MagicBees;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.bees.BeeIntegrationInterface;
import magicbees.util.ModNames;

/**
 * Created by Elec332 on 19-5-2017.
 */
@MagicBeesModule(owner = MagicBees.modid, name = "AE2 Integration", modDependencies = ModNames.AE2)
public class IntegrationAE2 implements IMagicBeesModule {

	@Override
	public void init(IMagicBeesInitialisationEvent event){
		BeeIntegrationInterface.aeSkyStone = event.getBlock("sky_stone_block").getDefaultState();
	}

}
