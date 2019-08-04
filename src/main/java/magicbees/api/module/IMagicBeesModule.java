package magicbees.api.module;

/**
 * Created by Elec332 on 14-8-2017.
 */
public interface IMagicBeesModule {

    default void preInit() {
    }

    default void registerConfig(IConfigRegistry registry) {
    }

    void init(IMagicBeesInitialisationEvent event);

    default void postInit() {
    }

}
