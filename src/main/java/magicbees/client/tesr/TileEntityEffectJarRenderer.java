package magicbees.client.tesr;

import magicbees.tile.TileEntityEffectJar;
import magicbees.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

/**
 * Created by Elec332 on 16-8-2017.
 */
public class TileEntityEffectJarRenderer extends TileEntitySpecialRenderer<TileEntityEffectJar> {

	@Override
	public void render(TileEntityEffectJar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (!Config.fancyJarRenderer || !Minecraft.getMinecraft().gameSettings.fancyGraphics){
			return;
		}
		ItemStack stack = te.getDrone();//BeeManager.beeRoot.getMemberStack(EnumBeeSpecies.DIAMOND.getIndividual(), EnumBeeType.QUEEN);
		RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5f, y + 0.36f, z + 0.5f);
		GlStateManager.scale(0.6F, 0.6F, 0.6F);
		int angle = (int) (te.getWorld().getWorldTime() % 360);
		GlStateManager.rotate(angle * 3, 0, 1, 0);
		GlStateManager.translate(0, Math.cos(Math.toRadians(angle)) * 0.1, 0);
		//GlStateManager.disableAlpha();
		GlStateManager.color(1, 1, 1, 1);
		RenderHelper.enableStandardItemLighting();
		IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, te.getWorld(), null);
		IBakedModel transformedModel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
		itemRenderer.renderItem(stack, transformedModel);
		RenderHelper.disableStandardItemLighting();
		//GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}

}
