package magicbees.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import magicbees.bees.EnumBeeSpecies;
import magicbees.elec332.corerepack.compat.forestry.ForestryAlleles;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class WorldGenBeeSpeciesCache {

	private static List<Pair<IAlleleBeeSpecies, Double>> worldgenSpeciesWeights = Lists.newArrayList();
	private static double worldgenSpeciesWeightsTotal = 0.0D;

	public static IBee getRandomWorldgenSpecies(Random r, boolean rainResist) {
		Collections.shuffle(worldgenSpeciesWeights);
		double value = r.nextDouble() * worldgenSpeciesWeightsTotal;
		IAlleleBeeSpecies species = worldgenSpeciesWeights.get(0).getLeft();

		for (Pair<IAlleleBeeSpecies, Double> t : worldgenSpeciesWeights) {
			value -= t.getRight();
			if (value <= 0.0D) {
				species = t.getLeft();
				break;
			}
		}

		IAllele[] allelez = BeeManager.beeRoot.getTemplate(species.getUID());
		Preconditions.checkNotNull(allelez);
		IAllele[] alleles = Arrays.copyOf(allelez, allelez.length);

		if (rainResist){
			alleles[EnumBeeChromosome.TOLERATES_RAIN.ordinal()] = ForestryAlleles.TRUE_RECESSIVE;
		}

		return BeeManager.beeRoot.getBee(BeeManager.beeRoot.templateAsGenome(alleles));
	}

	public static void addWorldgenSpeciesWeight(IAlleleBeeSpecies species, double d) {
		Preconditions.checkNotNull(species);
		Preconditions.checkArgument(d > 0);
		worldgenSpeciesWeights.add(Pair.of(species, d));
		worldgenSpeciesWeightsTotal += d;
	}

	public static void populateSpeciesListRarity() {
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Forest"), 20.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Meadows"), 20.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Tropical"), 10.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Modest"), 16.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Wintry"), 10.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.getForestrySpecies("Ended"), 0.5D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.MYSTICAL.getSpecies(), 20.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.UNUSUAL.getSpecies(), 20.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.SORCEROUS.getSpecies(), 13.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.ATTUNED.getSpecies(), 6.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.INFERNAL.getSpecies(), 10.0D);
		addWorldgenSpeciesWeight(EnumBeeSpecies.OBLIVION.getSpecies(), 1.0D);
	}

}
