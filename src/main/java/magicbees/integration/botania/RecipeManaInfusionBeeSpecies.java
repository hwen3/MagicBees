package magicbees.integration.botania;

import forestry.api.apiculture.*;
import forestry.api.genetics.IChromosome;
import forestry.apiculture.genetics.BeeGenome;
import forestry.core.genetics.Chromosome;
import magicbees.bees.EnumBeeSpecies;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeManaInfusion;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class RecipeManaInfusionBeeSpecies extends RecipeManaInfusion {

	public RecipeManaInfusionBeeSpecies(EnumBeeSpecies outSpecies, EnumBeeSpecies inSpecies, int mana, EnumBeeType type) {
		super(BeeManager.beeRoot.getMemberStack(outSpecies.getIndividual(), type), BeeManager.beeRoot.getMemberStack(inSpecies.getIndividual(), type), mana);
		outputSpecies = outSpecies.getSpecies();
		inputSpecies = inSpecies.getSpecies();
		beeType = type;
		outputCache = super.getOutput();
	}

	private IAlleleBeeSpecies outputSpecies;
	private IAlleleBeeSpecies inputSpecies;
	private EnumBeeType beeType;
	private ItemStack outputCache;

	@Override
	public boolean matches(ItemStack stack) {
		if (!BeeManager.beeRoot.isMember(stack, beeType)) {
			return false;
		}

		IBee bee = BeeManager.beeRoot.getMember(stack);
		if (bee != null && bee.getGenome().getPrimary().equals(inputSpecies)) {
			IChromosome[] chromosomes = bee.getGenome().getChromosomes();
			chromosomes[EnumBeeChromosome.SPECIES.ordinal()] = new Chromosome(outputSpecies);
			outputCache = BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.getBee(new BeeGenome(chromosomes)), beeType);
			outputCache.setCount(1);
			return true;
		}

		return false;
	}

	@Override
	public ItemStack getOutput() {
		ItemStack currentOutput = outputCache;
		outputCache = super.getOutput();
		return currentOutput;
	}

}