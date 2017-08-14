package magicbees.tile.logic;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorLogic;
import forestry.api.core.IErrorState;
import magicbees.tile.TileEntityEffectJar;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 5-4-2017.
 *
 * Simulates enough of a bee house to fire the bee effects. =D
 *
 * @author MysteriousAges, mezz
 */
public class EffectJarHousing implements IBeeHousing {

	public EffectJarHousing(TileEntityEffectJar entity) {
		this.jarEntity = entity;
		this.biome = entity.getWorld().getBiome(entity.getPos());
		this.inventory = new JarBeeHousingInventory(entity);
		this.beekeepingLogic = BeeManager.beeRoot.createBeekeepingLogic(this);
	}

	private static final IErrorLogic errorLogic = new JarErrorLogic();
	private static final Iterable<IBeeListener> beeListeners = ImmutableSet.of(new DefaultBeeListener());
	private static final Iterable<IBeeModifier> beeModifiers = ImmutableSet.of(new JarHousingModifier());

	private final IBeekeepingLogic beekeepingLogic;
	private final TileEntityEffectJar jarEntity;
	private final Biome biome;
	private final IBeeHousingInventory inventory;

	@Override
	@Nonnull
	public World getWorldObj() {
		return jarEntity.getWorld();
	}

	@Override
	@Nonnull
	public BlockPos getCoordinates() {
		return jarEntity.getPos();
	}

	@Override
	@Nonnull
	public Biome getBiome() {
		return biome;
	}

	@Override
	@Nonnull
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromBiome(biome, getWorldObj(), getCoordinates());
	}

	@Override
	@Nonnull
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(biome.getRainfall());
	}

	@Override
	public int getBlockLightValue() {
		return getWorldObj().getLight(getCoordinates().up());
	}

	@Override
	public boolean canBlockSeeTheSky() {
		return getWorldObj().canBlockSeeSky(getCoordinates().up());
	}

	@Override
	public boolean isRaining() {
		return getWorldObj().isRainingAt(getCoordinates());
	}

	@Override
	public GameProfile getOwner() {
		return jarEntity.getOwner();
	}

	@Override
	@Nonnull
	public Vec3d getBeeFXCoordinates() {
		BlockPos pos = getCoordinates();
		return new Vec3d(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
	}

	@Override
	@Nonnull
	public IErrorLogic getErrorLogic() {
		return errorLogic;
	}

	@Override
	@Nonnull
	public IBeeHousingInventory getBeeInventory() {
		return inventory;
	}

	@Override
	@Nonnull
	public IBeekeepingLogic getBeekeepingLogic() {
		return beekeepingLogic;
	}

	@Override
	@Nonnull
	public Iterable<IBeeModifier> getBeeModifiers() {
		return beeModifiers;
	}

	@Override
	@Nonnull
	public Iterable<IBeeListener> getBeeListeners() {
		return beeListeners;
	}

	@SuppressWarnings("all")
	private static class JarBeeHousingInventory implements IBeeHousingInventory {

		private final TileEntityEffectJar jarEntity;

		public JarBeeHousingInventory(TileEntityEffectJar jarEntity) {
			this.jarEntity = jarEntity;
		}

		@Override
		public ItemStack getQueen() {
			return jarEntity.getQueenStack();
		}

		@Override
		public ItemStack getDrone() {
			return ItemStack.EMPTY;
		}

		@Override
		public void setQueen(ItemStack itemStack) {
			jarEntity.setQueenStack(itemStack);
		}

		@Override
		public void setDrone(ItemStack itemstack) {
		}

		@Override
		public boolean addProduct(ItemStack product, boolean all) {
			return false;
		}

	}

	@SuppressWarnings("all")
	private static class JarErrorLogic implements IErrorLogic {

		@Override
		public boolean setCondition(boolean condition, IErrorState errorState) {
			return condition;
		}

		@Override
		public boolean contains(IErrorState state) {
			return false;
		}

		@Override
		public boolean hasErrors() {
			return false;
		}

		@Override
		public void clearErrors() {

		}

		@Override
		public void writeData(PacketBuffer data) {

		}

		@Override
		public void readData(PacketBuffer data) {

		}

		@Override
		public ImmutableSet<IErrorState> getErrorStates() {
			return ImmutableSet.of();
		}

	}

	@SuppressWarnings("all")
	private static class JarHousingModifier implements IBeeModifier {

		@Override
		public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
			return 0.9f;
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return 0f;
		}

		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return 0f;
		}

		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			return 0f;
		}

		@Override
		public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
			return 0f;
		}

		@Override
		public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
			return 0f;
		}

		@Override
		public boolean isSealed() {
			return true;
		}

		@Override
		public boolean isSelfLighted() {
			return true;
		}

		@Override
		public boolean isSunlightSimulated() {
			return true;
		}

		@Override
		public boolean isHellish() {
			return false;
		}

	}
}
