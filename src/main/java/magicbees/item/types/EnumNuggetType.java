package magicbees.item.types;

import magicbees.MagicBees;
import magicbees.elec332.corerepack.item.IEnumItem;
import magicbees.elec332.corerepack.item.ItemEnumBased;
import magicbees.init.ItemRegister;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 15-5-2017.
 */
public enum EnumNuggetType implements IEnumItem {

	DIAMOND,
	EMERALD,
	APATITE,
	COPPER,
	TIN,
	BRONZE;

	public ItemStack getStack(){
		return ItemRegister.orePartItem.getStackFromType(this);
	}

	@Override
	public void initializeItem(ItemEnumBased<? extends IEnumItem> itemEnumBased) {
		itemEnumBased.setCreativeTab(MagicBees.creativeTab);
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return new MagicBeesResourceLocation("items/part"+toString().toLowerCase());
	}

}
