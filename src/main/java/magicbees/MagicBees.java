package magicbees;

import magicbees.api.ICrumblingHandler;
import magicbees.api.ITransmutationController;
import magicbees.bees.EnumBeeHives;
import magicbees.bees.EnumBeeSpecies;
import magicbees.elec332.corerepack.compat.forestry.ForestryCompatHandler;
import magicbees.elec332.corerepack.compat.forestry.IndividualDefinitionRegistry;
import magicbees.elec332.corerepack.util.LoadTimer;
import magicbees.init.AlleleRegister;
import magicbees.init.BlockRegister;
import magicbees.init.ItemRegister;
import magicbees.init.RecipeRegister;
import magicbees.integration.botania.BotaniaIntegrationConfig;
import magicbees.util.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 16-8-2016.
 */
@Mod(modid = MagicBees.modid, name = MagicBees.modName, dependencies = "required-after:forestry",
        acceptedMinecraftVersions = "[1.10.2,)", useMetadata = true)
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
        creativeTab = new CreativeTabs(modid) {

            @Override
            @Nonnull
            public ItemStack getTabIconItem() {
                return new ItemStack(ItemRegister.magicFrame);
            }

        };
        config = new ConfigHandler(new Configuration(event.getSuggestedConfigurationFile()));
        config.registerConfig(new BotaniaIntegrationConfig());
        config.registerConfig(new Config());
        config.registerConfig(new BotaniaIntegrationConfig());
        ModuleHandler.INSTANCE.preInit(event);
        loadTimer.endPhase(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception{
        loadTimer.startPhase(event);
        forestryCompatHandler.init(event);
        AlleleRegister.init();
        ItemRegister.init();
        BlockRegister.init();
        RecipeRegister.init();
        config.reload();
        logger.info("Registering " + EnumBeeSpecies.values().length + " new bee species!");
        IndividualDefinitionRegistry.registerBees(EnumBeeSpecies.class);

        ModuleHandler.INSTANCE.init();
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
