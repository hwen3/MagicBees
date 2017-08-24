package magicbees.inventory.gui;

import magicbees.inventory.container.ContainerEffectJar;
import magicbees.tile.TileEntityEffectJar;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 16-8-2017.
 */
public class GuiEffectJar extends GuiContainer {

	public GuiEffectJar(ContainerEffectJar container) {
		super(container);
		this.jar = container.getJar();
	}

	private final TileEntityEffectJar jar;
	private static final ResourceLocation BACKGROUND = new MagicBeesResourceLocation("textures/inventory/jarscreen.png");
	private static final int BAR_HEIGHT = 40;

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int currentBeeColour = jar.getCurrentBeeColour();
		float r = ((currentBeeColour >> 16) & 255) / 255f;
		float g = ((currentBeeColour >> 8) & 255) / 255f;
		float b = (currentBeeColour & 255) / 255f;

		GlStateManager.color(r, g, b);
		this.mc.getTextureManager().bindTexture(BACKGROUND);

		int value = BAR_HEIGHT - (jar.getCurrentBeeHealth() * BAR_HEIGHT) / 100;
		drawTexturedModalRect(117, 50 - (BAR_HEIGHT - value), 176, 0, 10, BAR_HEIGHT - value);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BACKGROUND);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

}
