package magicbees.api.module;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by Elec332 on 12-8-2017.
 */
public interface IConfiguration {

    default void init(IConfigRegistry configHandler) {
    }

    void reload(Configuration config);

}
