package magicbees.api.module;

/**
 * Created by Elec332 on 24-8-2017.
 */
public interface IConfigRegistry {

	public void registerConfig(IConfiguration config);

	public void registerCategoryComment(String category, String comment);

}
