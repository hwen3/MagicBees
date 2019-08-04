package magicbees.api.module;

/**
 * Created by Elec332 on 24-8-2017.
 */
public interface IConfigRegistry {

    void registerConfig(IConfiguration config);

    void registerCategoryComment(String category, String comment);

}
