package magicbees.integration.railcraft;

import magicbees.MagicBees;
import magicbees.api.ITransmutationHandler;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.util.ModNames;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;

/**
 * Created by Elec332 on 15-5-2017.
 */
@SuppressWarnings("deprecation")
@MagicBeesModule(owner = MagicBees.modid, name = "Railcraft Integration", modDependencies = ModNames.RAILCRAFT)
public class IntegrationRailcraft implements IMagicBeesModule {

	private Block quarried, abyssal;

	@Override
	public void init(IMagicBeesInitialisationEvent event){
		quarried = event.getBlock("brick_quarried");
		abyssal = event.getBlock( "brick_abyssal");
		MagicBees.transmutationController.addTransmutationHandler(new ITransmutationHandler() {

			@Override
			public boolean transmute(World world, BlockPos pos, ItemStack block, Biome biome) {
				BlockStone.EnumType type = null;
				boolean hT = false;
				if (block.getItem() == Item.getItemFromBlock(Blocks.STONE)){
					IBlockState state = Blocks.STONE.getStateFromMeta(block.getMetadata());
					type = state.getValue(BlockStone.VARIANT);
					hT = true;
				}
				if (block.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)){
					hT = true;
				}
				if (hT){
					IBlockState state = trySpawnRC(biome, type);
					if (state != null){
						world.setBlockState(pos, state, 3);
						return true;
					}
				}
				return false;
			}

		});
	}

	private IBlockState trySpawnRC(Biome biome, @Nullable BlockStone.EnumType type){
		int meta = type == null ? 5 : type == BlockStone.EnumType.STONE ? 2 : -1;
		if (meta < 0){
			return null;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)){
			return quarried.getStateFromMeta(meta);
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER) && !biome.getBiomeName().toLowerCase().contains("river")){
			return abyssal.getStateFromMeta(meta);
		}
		return null;
	}

}
