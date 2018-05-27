package magicbees;

import magicbees.api.ICrumblingHandler;
import magicbees.api.ITransmutationController;
import magicbees.bees.EnumBeeBranches;
import magicbees.bees.EnumBeeHives;
import magicbees.bees.EnumBeeSpecies;
import magicbees.client.ModelHandler;
import magicbees.elec332.corerepack.compat.forestry.ForestryCompatHandler;
import magicbees.elec332.corerepack.compat.forestry.IndividualDefinitionRegistry;
import magicbees.elec332.corerepack.util.LoadTimer;
import magicbees.init.AlleleRegister;
import magicbees.init.BlockRegister;
import magicbees.init.ItemRegister;
import magicbees.init.RecipeRegister;
import magicbees.inventory.GuiHandler;
import magicbees.util.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Elec332 on 16-8-2016.
 */
@Mod(modid = MagicBees.modid, name = MagicBees.modName, dependencies = "required-after:forestry@[5.6.0,)",
        acceptedMinecraftVersions = "[1.12,)", useMetadata = true)
public class MagicBees {

    public static final String modid = "magicbees";
    public static final String modName = "MagicBees";

    @Mod.Instance(modid)
    public static MagicBees instance;
    public static Logger logger;
    private static LoadTimer loadTimer;
    public static CreativeTabs creativeTab;
    private static ConfigHandler config;
    private ForestryCompatHandler forestryCompatHandler;

    public static ICrumblingHandler crumblingHandler;
    public static ITransmutationController transmutationController;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = LogManager.getLogger(modName);
        loadTimer = new LoadTimer(logger, modName);
        loadTimer.startPhase(event);
        forestryCompatHandler = new ForestryCompatHandler();
        forestryCompatHandler.preInit(event);

        crumblingHandler = new DefaultCrumblingHandler();
        transmutationController = new DefaultTransmutationController();
        creativeTab = new MagicBeesCreativeTab();
        config = new ConfigHandler(new Configuration(event.getSuggestedConfigurationFile()));

        MinecraftForge.EVENT_BUS.register(new ItemRegister());
        MinecraftForge.EVENT_BUS.register(new BlockRegister());
        MinecraftForge.EVENT_BUS.register(new RecipeRegister());

        BlockRegister.preInit();
        ItemRegister.preInit();

        ModuleHandler.INSTANCE.preInit(event);
        ModuleHandler.INSTANCE.registerConfig(config);

        config.registerConfig(new Config());

        if (FMLCommonHandler.instance().getSide().isClient()){
            MinecraftForge.EVENT_BUS.register(new ModelHandler());
        }

        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception{
        loadTimer.startPhase(event);
        forestryCompatHandler.init(event);

        EnumBeeBranches.registerClassifications();
        AlleleRegister.init();
        ItemRegister.registerOreDictionary();
        RecipeRegister.init();

        config.reload();

        ModuleHandler.INSTANCE.init();

        logger.info("Registering " + EnumBeeSpecies.values().length + " new bee species!");
        IndividualDefinitionRegistry.registerBees(EnumBeeSpecies.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        loadTimer.startPhase(event);
        forestryCompatHandler.postInit(event);

        for (EnumBeeHives h : EnumBeeHives.values()){
            h.registerDrops();
        }

        WorldGenBeeSpeciesCache.populateSpeciesListRarity();

        ModuleHandler.INSTANCE.postInit();

        loadTimer.endPhase(event);
    }

}
