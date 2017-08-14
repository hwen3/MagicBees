package magicbees.util;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 12-8-2017.
 */
public interface IConfiguration {

	public void init(ConfigHandler configHandler);

	public void reload(Configuration config);

}
