package magicbees.integration.botania;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import magicbees.util.WorldGenBeeSpeciesCache;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.subtile.SubTileFunctional;

import java.util.Random;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class SubTileHiveacynth extends SubTileFunctional {

	public static final String NAME = "hiveacynth";
	private static final int RANGE = 3;
	private static final int COST = 15000;

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (supertile.getWorld().isRemote) {
			return;
		}

		if (readyToProduceProduct()) {
			mana -= getFinalCost();
			Random r = supertile.getWorld().rand;
			EnumBeeType beeType = EnumBeeType.DRONE;

			IBee bee = WorldGenBeeSpeciesCache.getRandomWorldgenSpecies(r, r.nextDouble() < BotaniaIntegrationConfig.hiveacynthRainResistRate);

			if (r.nextDouble() < BotaniaIntegrationConfig.hiveacynthPrincessSpawnRate) {
				beeType = EnumBeeType.PRINCESS;

				if (BotaniaIntegrationConfig.hiveacynthPristineRate < r.nextDouble()) {
					bee.setIsNatural(false);
				}
			}

			ItemStack stack = BeeManager.beeRoot.getMemberStack(bee, beeType);

			EntityItem entity = new EntityItem(supertile.getWorld(), supertile.getPos().getX() - RANGE + r.nextInt(RANGE * 2 + 1), supertile.getPos().getY() + 1, supertile.getPos().getZ() - RANGE + r.nextInt(RANGE * 2 + 1), stack);
			entity.motionX = 0;
			entity.motionY = 0;
			entity.motionZ = 0;
			getWorld().spawnEntity(entity);
		}
	}

	private boolean readyToProduceProduct() {
		return redstoneSignal == 0 && ticksExisted % 200 == 0 && mana >= getFinalCost();
	}

	private int getFinalCost() {
		return (int)(COST * BotaniaIntegrationConfig.hiveacynthManaMultiplier);
	}

	@Override
	public boolean acceptsRedstone() {
		return true;
	}

	@Override
	public int getColor() {
		return 0x0071C6;
	}

	@Override
	public int getMaxMana() {
		return getFinalCost();
	}

}