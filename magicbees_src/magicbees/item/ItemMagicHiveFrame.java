package magicbees.item;

import magicbees.item.types.HiveFrameType;
import magicbees.main.Config;
import magicbees.main.utils.TabThaumicBees;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;

public class ItemMagicHiveFrame extends Item implements IHiveFrame
{
	private HiveFrameType type;

	public ItemMagicHiveFrame(int itemID, HiveFrameType frameType)
	{
		super(itemID);
		this.type = frameType;
		this.setMaxDamage(this.type.maxDamage);
		this.setMaxStackSize(1);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setUnlocalizedName("frame" + frameType.getName());
		GameRegistry.registerItem(this, "frame" + frameType.getName());
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":frame" + type.getName());
    }
	
	// --------- IHiveFrame functions -----------------------------------------
	
	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear)
	{
		return this.doWear(housing.getWorld(), housing.getXCoord(), housing.getYCoord(), housing.getZCoord(), frame, wear);
	}
	
	private ItemStack doWear(World w, int x, int y, int z, ItemStack frame, int wear)
	{
		int damage = wear;
		
		if (ThaumcraftHelper.isActive())
		{
			int fluxMod = 1;
			// This throttles back the amount of aura consumed by the frame by storing a smidge of data on the item stack.
			if (this.type.auraPerUse > 0)
			{
				NBTTagCompound tag;
				if (frame.hasTagCompound())
				{
					tag = frame.getTagCompound();
				}
				else
				{
					tag = new NBTTagCompound();
					tag.setByte("wearTicks", (byte)0);
					frame.setTagCompound(tag);
				}
				
				int wearTicks = tag.getByte("wearTicks") + 1;
				
				if (wearTicks == this.type.wearTicksPerAura)
				{
					// Attempt to use aura for the frame.
					if (!ThaumcraftApi.decreaseClosestAura(w, x, y, z, this.type.auraPerUse, true))
					{
						// Insufficient aura, or no nearby node.
						damage = wear + this.type.auraPerUse * this.type.wearTicksPerAura;
						fluxMod = 1 + w.rand.nextInt(this.type.wearTicksPerAura);
					}
					wearTicks = 0;
							
					for (int i = 0; i < fluxMod; ++i)
					{
						ThaumcraftHelper.doFluxEffect(this.type, w, x, y, z);
					}		
				}
				
				tag.setByte("wearTicks", (byte)wearTicks);
			}
		}
		else
		{
			damage += 2;
		}
		
		frame.setItemDamage(frame.getItemDamage() + damage);
		
		if (frame.getItemDamage() >= frame.getMaxDamage())
		{
			// Break the frame.
			frame = null;
		}
		
		return frame;
	}
	
	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier)
	{
		return this.type.territoryMod;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return this.type.mutationMod;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return this.type.lifespanMod;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier)
	{
		return this.type.productionMod;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier)
	{
		return this.type.floweringMod;
	}

	@Override
	public boolean isSealed()
	{
		return this.type.isSealed;
	}

	@Override
	public boolean isSelfLighted()
	{
		return this.type.isLit;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		return this.type.isSunlit;
	}

	@Override
	public boolean isHellish()
	{
		return this.type.isHellish;
	}

}