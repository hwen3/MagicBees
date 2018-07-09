package magicbees.integration.botania;

import com.google.common.collect.Lists;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import magicbees.MagicBees;
import magicbees.api.ITransmutationHandler;
import magicbees.api.module.IConfigRegistry;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.bees.BeeIntegrationInterface;
import magicbees.bees.EnumBeeSpecies;
import magicbees.bees.allele.AlleleEffectTransmuting;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleFlowerProvider;
import magicbees.init.ItemRegister;
import magicbees.util.DefaultTransmutationController;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.ModNames;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.api.recipe.RecipePetals;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibItemNames;

/**
 * Created by Elec332 on 15-5-2017.
 */
@MagicBeesModule(owner = MagicBees.modid, name = "Botania Integration", modDependencies = ModNames.BOTANIA)
public class IntegrationBotania implements IMagicBeesModule {

	private Block livingRock, dreamWood;
	private Item itemPetal;

	@Override
	public void preInit(){

		BotaniaAPI.registerSubTile(SubTileBeegonia.NAME, SubTileBeegonia.class);
		BotaniaAPI.registerSubTileSignature(SubTileBeegonia.class, new BotaniaSignature(SubTileBeegonia.NAME));
		BotaniaAPI.addSubTileToCreativeMenu(SubTileBeegonia.NAME);

		BotaniaAPI.registerSubTile(SubTileHiveacynth.NAME, SubTileHiveacynth.class);
		BotaniaAPI.registerSubTileSignature(SubTileHiveacynth.class, new BotaniaSignature(SubTileHiveacynth.NAME));
		BotaniaAPI.addSubTileToCreativeMenu(SubTileHiveacynth.NAME);

		BotaniaAPI.registerSubTile(SubTileHibeescus.NAME, SubTileHibeescus.class);
		BotaniaAPI.registerSubTileSignature(SubTileHibeescus.class, new BotaniaSignature(SubTileHibeescus.NAME));
		BotaniaAPI.addSubTileToCreativeMenu(SubTileHibeescus.NAME);
		if (FMLCommonHandler.instance().getSide().isClient()){
			cl();
		}
	}

	@Override
	public void registerConfig(IConfigRegistry registry) {
		registry.registerConfig(new BotaniaIntegrationConfig());
	}

	@SideOnly(Side.CLIENT)
	private void cl(){
		ModelResourceLocation mrl;

		mrl = new ModelResourceLocation(new MagicBeesResourceLocation("beegonia"), "normal");
		BotaniaAPIClient.registerSubtileModel(SubTileBeegonia.class, mrl);
		mrl = new ModelResourceLocation(new MagicBeesResourceLocation("hibeescus"), "normal");
		BotaniaAPIClient.registerSubtileModel(SubTileHibeescus.class, mrl);
		mrl = new ModelResourceLocation(new MagicBeesResourceLocation("hiveacynth"), "normal");
		BotaniaAPIClient.registerSubtileModel(SubTileHiveacynth.class, mrl);
	}

	@Override
	public void init(IMagicBeesInitialisationEvent event){
		IBlockState livingWood = BeeIntegrationInterface.livingWood = event.getBlock("livingwood").getDefaultState();
		livingRock = event.getBlock("livingrock");
		dreamWood = event.getBlock("dreamwood");
		Block mysticalFlower = event.getBlock("flower");
		BeeIntegrationInterface.effectDreaming = new AlleleEffectTransmuting(BeeIntegrationInterface.bot_dreaming_name, new DefaultTransmutationController((ITransmutationHandler) (world, pos, block, biome) -> {

			int[] oreIDs = OreDictionary.getOreIDs(block);
			for (int oreId : oreIDs) {
				if (oreId == OreDictionary.getOreID("logWood")) {
					world.setBlockState(pos, livingWood, 3);
					return true;
				} else if (oreId == OreDictionary.getOreID("stone")) {
					world.setBlockState(pos, livingRock.getDefaultState(), 3);
					return true;
				} else if (oreId == OreDictionary.getOreID("livingwood")) {
					world.setBlockState(pos, dreamWood.getDefaultState(), 3);
					return true;
				}
			}
			return false;

		}));
		BeeIntegrationInterface.flowersBotania = new AlleleFlowerProvider(BeeIntegrationInterface.bot_flowers_name, new FlowerProviderBotania("flowersBotania", mysticalFlower));
		BeeIntegrationInterface.flowersBotania.registerAcceptableFlower(mysticalFlower);

		BeeIntegrationInterface.itemPetal = itemPetal = event.getItem("petal");
		BeeIntegrationInterface.itemPastureSeed = event.getItem("grassSeeds");
		BeeIntegrationInterface.seedTypes = 9;

	}

	@Override
	public void postInit(){

		ResourceLocation manaSteelScoopRecipe = new MagicBeesResourceLocation("manasteelscoop");
		ResourceLocation manaSteelGrafterRecipe = new MagicBeesResourceLocation("manasteelgrafter");

		CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped("twt", "tmt", " t ", 'm', new ItemStack(ModItems.manaResource, 1, 0), 'w', Blocks.WOOL, 't', Items.STICK);
		ForgeRegistries.RECIPES.register(new ShapedRecipes("", primer.width, primer.height, primer.input, new ItemStack(ItemRegister.manasteelScoop)).setRegistryName(manaSteelScoopRecipe));

		primer = CraftingHelper.parseShaped("  m", " t ", "t  ", 'm', new ItemStack(ModItems.manaResource, 1, 0), 't', Items.STICK);
		ForgeRegistries.RECIPES.register(new ShapedRecipes("", primer.width, primer.height, primer.input, new ItemStack(ItemRegister.manasteelgrafter)).setRegistryName(manaSteelGrafterRecipe));

		RecipeManaInfusion infusionBeeBotanical = new RecipeManaInfusionBeeSpecies(EnumBeeSpecies.BOT_BOTANIC, EnumBeeSpecies.BOT_ROOTED, 55000, EnumBeeType.DRONE);
		BotaniaAPI.manaInfusionRecipes.add(infusionBeeBotanical);

		RecipeManaInfusion infusionBeeVazbee = new RecipeManaInfusionBeeSpecies(EnumBeeSpecies.BOT_VAZBEE, EnumBeeSpecies.BOT_FLORAL, 167392, EnumBeeType.PRINCESS);
		infusionBeeVazbee.setAlchemy(true);
		BotaniaAPI.manaInfusionRecipes.add(infusionBeeVazbee);

		RecipeElvenTrade tradeBeeAelfheim = new RecipeElvenTradeBeeSpecies(EnumBeeSpecies.BOT_DREAMING, EnumBeeSpecies.BOT_ALFHEIM);
		BotaniaAPI.elvenTradeRecipes.add(tradeBeeAelfheim);

		LexiconEntry entryManasteelForestryTools = new BotaniaLexiconEntry("magicbees.botania.lexicon.manasteelTools.title", BotaniaAPI.categoryTools);
		entryManasteelForestryTools.setIcon(new ItemStack(ItemRegister.manasteelScoop));
		entryManasteelForestryTools.addPage(BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.manasteelTools.0"));
		entryManasteelForestryTools.addPage(BotaniaAPI.internalHandler.craftingRecipePage("magicbees.botania.lexicon.manasteelScoop", manaSteelScoopRecipe));
		entryManasteelForestryTools.addPage(BotaniaAPI.internalHandler.craftingRecipePage("magicbees.botania.lexicon.manasteelGrafter", manaSteelGrafterRecipe));

		LexiconEntry entryBeeTrade = new BotaniaLexiconEntry("magicbees.botania.lexicon.beeAlfheim.title", BotaniaAPI.categoryAlfhomancy);
		entryBeeTrade.setIcon(BeeManager.beeRoot.getMemberStack(EnumBeeSpecies.BOT_ALFHEIM.getIndividual(), EnumBeeType.PRINCESS));
		entryBeeTrade.setKnowledgeType(BotaniaAPI.elvenKnowledge);
		entryBeeTrade.addPage(BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.beeAlfheim.0"));
		entryBeeTrade.addPage(BotaniaAPI.internalHandler.elvenTradesPage("magicbees.botania.lexicon.beeAlfheim.trade", tradeBeeAelfheim));

		int manaPowder = Lists.newArrayList(LibItemNames.MANA_RESOURCE_NAMES).indexOf("manaPowder");
		int redstoneRoot = Lists.newArrayList(LibItemNames.MANA_RESOURCE_NAMES).indexOf("redstoneRoot");
		Item itemManaResource = ModItems.manaResource;

		RecipePetals beegoniaRecipe = BotaniaAPI.registerPetalRecipe(BotaniaAPI.internalHandler.getSubTileAsStack(SubTileBeegonia.NAME),
				new ItemStack(itemPetal),
				new ItemStack(itemPetal, 1, 4),
				new ItemStack(itemPetal, 1, 4),
				new ItemStack(itemPetal, 1, 15),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, manaPowder)
		);
		LexiconEntry entryBeegonia = new BotaniaLexiconEntry("tile.botania:flower." + SubTileBeegonia.NAME + ".name", BotaniaAPI.categoryGenerationFlowers);
		entryBeegonia.setLexiconPages(
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.beegonia.0"),
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.beegonia.1"),
				BotaniaAPI.internalHandler.petalRecipePage("magicbees.botania.lexicon.beegonia.crafting", beegoniaRecipe),
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.beegonia.2")
		);

		RecipePetals hiveacynthRecipe = BotaniaAPI.registerPetalRecipe(BotaniaAPI.internalHandler.getSubTileAsStack(SubTileHiveacynth.NAME),
				new ItemStack(itemPetal, 1, 3),
				new ItemStack(itemPetal, 1, 9),
				new ItemStack(itemPetal, 1, 9),
				new ItemStack(itemPetal, 1, 11),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(itemManaResource, 1, redstoneRoot)
		);
		LexiconEntry entryHiveacynth = new BotaniaLexiconEntry("tile.botania:flower." + SubTileHiveacynth.NAME + ".name", BotaniaAPI.categoryFunctionalFlowers);
		entryHiveacynth.setLexiconPages(BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.hiveacynth.0"),
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.hiveacynth.1"),
				BotaniaAPI.internalHandler.petalRecipePage("magicbees.botania.lexicon.hiveacynth.crafting", hiveacynthRecipe)
		);

		RecipePetals hibeescusRecipe = BotaniaAPI.registerPetalRecipe(BotaniaAPI.internalHandler.getSubTileAsStack(SubTileHibeescus.NAME),
				new ItemStack(itemPetal, 1, 1),
				new ItemStack(itemPetal, 1, 2),
				new ItemStack(itemPetal, 1, 2),
				new ItemStack(itemPetal, 1, 2),
				new ItemStack(itemPetal, 1, 14),
				new ItemStack(itemManaResource, 1, manaPowder),
				new ItemStack(ModItems.rune, 1, 0),
				new ItemStack(ModItems.rune, 1, 5),
				new ItemStack(ModItems.rune, 1, 14),
				new ItemStack(ModItems.rune, 1, 11),
				new ItemStack(ModItems.rune, 1, 15),
				new ItemStack(itemManaResource, 1, redstoneRoot)
		);
		LexiconEntry entryHibeescus = new BotaniaLexiconEntry("tile.botania:flower." + SubTileHibeescus.NAME + ".name", BotaniaAPI.categoryFunctionalFlowers);
		entryHibeescus.setLexiconPages(BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.hibeescus.0"),
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.hibeescus.1"),
				BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.hibeescus.2"),
				BotaniaAPI.internalHandler.petalRecipePage("magicbees.botania.lexicon.hibeescus.crafting", hibeescusRecipe)
		);

		//todo
		//LexiconEntry entryManaApiaryBooster = new BotaniaLexiconEntry("tile.manaAuraProvider.name", BotaniaAPI.categoryDevices);
		//entryManaApiaryBooster.setLexiconPages(
		//		BotaniaAPI.internalHandler.textPage("magicbees.botania.lexicon.manaBooster.0"),
		//		BotaniaAPI.internalHandler.craftingRecipePage("magicbees.botania.lexicon.manaBooster.crafting", manaBoosterRecipe)
		//);
	}

}
