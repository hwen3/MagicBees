package magicbees.api.module;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 12-8-2017.
 */
public interface IConfiguration {

	default public void init(IConfigRegistry configHandler){
	}

	public void reload(Configuration config);

}
