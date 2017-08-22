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
import magicbees.integration.botania.BotaniaIntegrationConfig;
import magicbees.inventory.GuiHandler;
import magicbees.util.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        creativeTab = new MagicBeesCreativeTab();
        config = new ConfigHandler(new Configuration(event.getSuggestedConfigurationFile()));
        config.registerConfig(new BotaniaIntegrationConfig());
        config.registerConfig(new Config());
        config.registerConfig(new BotaniaIntegrationConfig());
        ItemRegister.init();
        BlockRegister.init();
        ModuleHandler.INSTANCE.preInit(event);
        if (FMLCommonHandler.instance().getSide().isClient()){
            MinecraftForge.EVENT_BUS.register(new ModelHandler());
        }
        loadTimer.endPhase(event);
        MinecraftForge.EVENT_BUS.register(new Object(){

            @SideOnly(Side.CLIENT)
            @SubscribeEvent(priority = EventPriority.LOWEST)
            @SuppressWarnings("all")
            public void afterAllModelsBaked(ModelBakeEvent event){
                ModelLoader modelLoader = event.getModelLoader();
                try {
                    Field fl = ModelLoader.class.getDeclaredField("loadingExceptions");
                    fl.setAccessible(true);
                    Map<ResourceLocation, Exception> exceptionMap = (Map<ResourceLocation, Exception>) fl.get(modelLoader);
                    File f = new File((File) FMLInjectionData.data()[6], "missing_json.txt");
                    if (!f.exists()){
                        f.createNewFile();
                    }
                    PrintStream ps = new PrintStream(new FileOutputStream(f));
                    Map<ResourceLocation, Exception> exceptionMap_ = exceptionMap.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey(new Comparator<ResourceLocation>() {

                                @Override
                                public int compare(ResourceLocation o1, ResourceLocation o2) {
                                    int i = o1.getResourcePath().compareTo(o2.getResourcePath());
                                    if (i == 0){
                                        String f = "", f1 = "";
                                        if (o1 instanceof ModelResourceLocation){
                                            f = ((ModelResourceLocation) o1).getVariant();
                                        }
                                        if (o2 instanceof ModelResourceLocation){
                                            f1 = ((ModelResourceLocation) o2).getVariant();
                                        }
                                        return f.compareTo(f1);
                                    }
                                    return i;
                                }

                            }))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (e1, e2) -> e1,
                                    LinkedHashMap::new
                            ));
                    for (Map.Entry<ResourceLocation, Exception> e : exceptionMap_.entrySet()){
                        if (!(e.getKey().getResourceDomain().equals(modid) || e.getKey().getResourcePath().contains(modid))){
                            continue;
                        }
                        ps.println("----------");
                        ps.println("ResourceLocation: "+e.getKey());
                        Throwable ex = e.getValue();
                        int i = 0;
                        while ((ex = ex.getCause()) != null){
                            if (ex instanceof FileNotFoundException){
                                ps.println("FileNotFound: "+ex.getMessage());
                                i++;
                            }
                            if (i >= 8){
                                //break;
                            }
                        }
                        ps.println("----------");
                    }

                    ps.close();
                } catch (Exception e1){
                    e1.printStackTrace();
                }
            }

        });
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception{
        loadTimer.startPhase(event);
        forestryCompatHandler.init(event);
        EnumBeeBranches.registerClassifications();
        AlleleRegister.init();
        RecipeRegister.init();
        config.reload();
        logger.info("Registering " + EnumBeeSpecies.values().length + " new bee species!");
        IndividualDefinitionRegistry.registerBees(EnumBeeSpecies.class);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
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
