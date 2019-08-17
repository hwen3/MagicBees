package magicbees.integration.arsmagica;

/**
 * Created by Elec332 on 16-5-2017.
 */
public class IntegrationArsMagica {

    /*
        Normal recipes:

        input = ItemInterface.getItemStack("apatite");
		output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER, 4);
		GameRegistry.addShapelessRecipe(output,
			new ItemStack(ArsMagicaHelper.essence, 1, ArsMagicaHelper.EssenceType.EARTH.ordinal()),
			input, input
		);
		GameRegistry.addShapelessRecipe(output,
			new ItemStack(ArsMagicaHelper.essence, 1, ArsMagicaHelper.EssenceType.PLANT.ordinal()),
			input, input
		);

		Centrifuge recipes:

		output = newMap();
		output.put(Config.wax.getStackForType(WaxType.MAGIC),  0.85f);
		output.put(new ItemStack(ArsMagicaHelper.itemResource),  0.10f);
		output.put(new ItemStack(ArsMagicaHelper.itemResource),  0.024f);
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_ESSENCE), output);
		output = newMap();
		output.put(ItemInterface.getItemStack("beeswax"),  0.5f);
		output.put(ItemInterface.getItemStack("refractoryWax"),  0.5f);
		output.put(ItemInterface.getItemStack("honeydew"),  0.65f);
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_POTENT), output);


     */

}
