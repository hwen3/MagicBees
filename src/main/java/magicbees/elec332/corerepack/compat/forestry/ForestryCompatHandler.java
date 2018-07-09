package magicbees.elec332.corerepack.compat.forestry;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.ForestryAPI;
import forestry.api.core.Tabs;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import magicbees.elec332.corerepack.compat.forestry.bee.ForestryBeeEffects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Elec332 on 14-8-2016.
 */
public class ForestryCompatHandler  {

    public static IBeeRoot beeRoot;
    private static CreativeTabs tabBees;

    public static CreativeTabs getForestryBeeTab() {
        return tabBees;
    }

    public void preInit(FMLPreInitializationEvent event){
        beeRoot = BeeManager.beeRoot;
        tabBees = Tabs.tabApiculture;
    }

    public void init(FMLInitializationEvent event){
        if (ForestryAPI.enabledModules.contains(new ResourceLocation("forestry", "apiculture"))) {
            ForestryAlleles.dummyLoad();
            ForestryBeeEffects.init();
            return; //Just to make sure
        }
        throw new RuntimeException("MagicBees requires the Apiculture plugin from forestry!");
    }

    public void postInit(FMLPostInitializationEvent event){
        IndividualDefinitionRegistry.locked = true;
        for (IIndividualTemplate iIndividualTemplate : IndividualDefinitionRegistry.templates){
            if (iIndividualTemplate.isActive()) {
                iIndividualTemplate.registerMutations();
            } else {
                IAlleleSpecies allele;
                try {
                    allele = ((IGenomeTemplate)iIndividualTemplate.getGenomeTemplateType().newInstance()).getSpecies(iIndividualTemplate.getAlleles());
                } catch (Exception e){
                    System.out.println("Error invocating class: "+iIndividualTemplate.getGenomeTemplateType().getCanonicalName());
                    System.out.println("Attempting backup method to fetch species allele...");
                    allele = (IAlleleSpecies) iIndividualTemplate.getAlleles()[0];
                }
                AlleleManager.alleleRegistry.blacklistAllele(allele.getUID());
            }
        }
        IndividualDefinitionRegistry.registeredClasses.clear();
        IndividualDefinitionRegistry.registeredTemplates.clear();
    }

}
