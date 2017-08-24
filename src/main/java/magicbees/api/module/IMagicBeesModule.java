package magicbees.api.module;

import magicbees.api.module.config.IConfigRegistry;

/**
 * Created by Elec332 on 14-8-2017.
 */
public interface IMagicBeesModule {

	default public void preInit(){
	}

	default public void registerConfig(IConfigRegistry registry){
	}

	public void init(IMagicBeesInitialisationEvent event);

	default public void postInit(){
	}

}
