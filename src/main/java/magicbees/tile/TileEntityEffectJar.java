package magicbees.tile;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IEffectData;
import forestry.core.network.IStreamable;
import forestry.core.network.PacketBufferForestry;
import forestry.core.network.packets.PacketActiveUpdate;
import forestry.core.network.packets.PacketTileStream;
import forestry.core.tiles.IActivatable;
import forestry.core.utils.NetworkUtil;
import magicbees.tile.logic.EffectJarHousing;
import magicbees.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by Elec332 on 5-4-2017.
 */
public class TileEntityEffectJar extends TileEntity implements ITickable, IActivatable, IStreamable {

	public TileEntityEffectJar() {
		super();
		this.beeSlots = new ItemStackHandler(1);
	}

	private GameProfile ownerName;
	private ItemStackHandler beeSlots;
	private ItemStack queenStack = ItemStack.EMPTY, droneQueen = null;
	private EffectJarHousing housingLogic = new EffectJarHousing(this);

	private IEffectData[] effectData = new IEffectData[2];
	private int throttle;
	private int currentBeeHealth;
	private int currentBeeColour;

	private boolean active = false;

	public void setOwner(EntityPlayer player) {
		this.ownerName = player.getGameProfile();
	}

	public ItemStackHandler getInventory() {
		return beeSlots;
	}

	public int getCurrentBeeColour() {
		return currentBeeColour;
	}

	public int getCurrentBeeHealth() {
		return currentBeeHealth;
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}

	public ItemStack getQueenStack() {
		return queenStack;
	}

	public ItemStack getDrone() {
		if (droneQueen == null){
			if (queenStack.isEmpty()){
				droneQueen = ItemStack.EMPTY;
				return droneQueen;
			}
			droneQueen = new ItemStack(Utils.getApicultureItems().beeDroneGE, 1, queenStack.getItemDamage());//droneStack.copy();
			droneQueen.setTagCompound(Preconditions.checkNotNull(queenStack.getTagCompound()).copy());
		}
		return droneQueen;
	}

	@Override
	public void update() {
		if (hasQueen()) {
			tickQueen();
		} else if (hasDrone()) {
			createQueenFromDrone();
		}
	}

	public void setQueenStack(ItemStack queenStack) {
		if (queenStack.isEmpty()){
			queenStack = ItemStack.EMPTY;
		}
		this.queenStack = queenStack;
		this.droneQueen = null;
	}

	public GameProfile getOwner() {
		return this.ownerName;
	}

	private void createQueenFromDrone() {
		ItemStack droneStack = this.beeSlots.getStackInSlot(0);
		if (BeeManager.beeRoot.isDrone(droneStack)) {
			IBee bee = Preconditions.checkNotNull(BeeManager.beeRoot.getMember(droneStack));
			if (droneStack.getCount() == 1) {
				this.beeSlots.setStackInSlot(0, ItemStack.EMPTY);
			} else {
				droneStack.shrink(1);
			}

			queenStack = new ItemStack(Utils.getApicultureItems().beeQueenGE, 1, droneStack.getItemDamage());//droneStack.copy();
			queenStack.setTagCompound(Preconditions.checkNotNull(droneStack.getTagCompound()).copy());
			queenStack.setCount(1);
			droneQueen = null;

			int current = bee.getHealth();
			int max = bee.getMaxHealth();
			currentBeeHealth = (current * 100) / max;
			currentBeeColour = bee.getGenome().getPrimary().getSpriteColour(0);
		}
		this.markDirty();
	}

	private boolean hasDrone() {
		return !beeSlots.getStackInSlot(0).isEmpty();
	}

	@Override
	@Nonnull
	public BlockPos getCoordinates() {
		return getPos();
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active){
		if (this.active != active && pos != null) {
			this.active = active;
			if (!world.isRemote) {
				NetworkUtil.sendNetworkPacket(new PacketActiveUpdate(this), pos, world);
			}
		}
	}

	@SuppressWarnings("all")
	private void tickQueen() {

		IBee queen = BeeManager.beeRoot.getMember(queenStack);
		if (queenStack == null || queen == null){
			throw new RuntimeException();
		}

		if (world.isRemote && active && this.getWorld().getWorldTime() % 4 == 0){
			this.effectData = queen.doFX(this.effectData, housingLogic);
			return;
		}

		setActive(housingLogic.canWork());

		if (!active){
			return;
		}


		currentBeeColour = queen.getGenome().getPrimary().getSpriteColour(0);

		this.effectData = queen.doEffect(this.effectData, housingLogic);

		// run the queen
		if (throttle > 550) {
			throttle = 0;
			queen.age(this.getWorld(), 0.26f);

			if (queen.getHealth() == 0) {
				this.queenStack = ItemStack.EMPTY;
				this.droneQueen = null;
				currentBeeHealth = 0;
				currentBeeColour = 0x0ffffff;
			} else {
				if (queenStack.getTagCompound() == null){
					queenStack.setTagCompound(new NBTTagCompound());
				}
				queen.writeToNBT(queenStack.getTagCompound());
			}
			currentBeeHealth = (queen.getHealth() * 100) / queen.getMaxHealth();
			this.markDirty();
			NetworkUtil.sendNetworkPacket(new PacketTileStream(this), pos, world);
		} else {
			throttle++;
		}
	}

	private boolean hasQueen() {
		return !queenStack.isEmpty();
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);
		beeSlots.deserializeNBT(tagRoot.getCompoundTag("beeSlots"));
		if (!tagRoot.hasKey("queenStack")){
			queenStack = droneQueen = ItemStack.EMPTY;
		} else {
			queenStack = new ItemStack(tagRoot.getCompoundTag("queenStack"));
			droneQueen = null;
		}
		this.currentBeeHealth = tagRoot.getInteger("currentBeeHealth");
		this.throttle = tagRoot.getInteger("throttle");
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound tagRoot) {
		super.writeToNBT(tagRoot);
		tagRoot.setTag("beeSlots", beeSlots.serializeNBT());
		if (!queenStack.isEmpty()){
			NBTTagCompound tag = new NBTTagCompound();
			queenStack.writeToNBT(tag);
			tagRoot.setTag("queenStack", tag);
		}
		tagRoot.setInteger("currentBeeHealth", this.currentBeeHealth);
		tagRoot.setInteger("throttle", this.throttle);
		return tagRoot;
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public ItemStack getDropStack(){
		return beeSlots.getStackInSlot(0);
	}

	@Override
	public void writeData(@Nonnull PacketBufferForestry data) {
		data.writeCompoundTag(writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readData(@Nonnull PacketBufferForestry data) throws IOException {
		readFromNBT(data.readCompoundTag());
	}

}
