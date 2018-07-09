package magicbees.item;

import magicbees.MagicBees;
import magicbees.util.Config;
import magicbees.util.MagicBeesResourceLocation;
import magicbees.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Elec332 on 4-3-2017.
 */
public class ItemMysteriousMagnet extends Item {

    public ItemMysteriousMagnet() {
        super();
        setRegistryName(new MagicBeesResourceLocation("mysteriousMagnet"));
        Utils.setUnlocalizedName(this);
        this.setNoRepair();
        this.setHasSubtypes(true);
        this.setCreativeTab(MagicBees.creativeTab);
    }

    private static final float FUDGE_FACTOR = 0.2f;

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String s = String.format(I18n.translateToLocal("misc.level"), getMagnetLevel(stack));
        if (isMagnetActive(stack)) {
            tooltip.add(String.format(I18n.translateToLocal("misc.magnetActive"), s));
        } else {
            tooltip.add(String.format(I18n.translateToLocal("misc.magnetInactive"), s));
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof EntityLivingBase) {
            if (isMagnetActive(itemStack) && entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                float radius = getRadius(itemStack) - FUDGE_FACTOR;
                AxisAlignedBB bounds = player.getEntityBoundingBox().expand(radius, radius, radius).expand(-radius, -radius, -radius);

                if (!world.isRemote) {
                    bounds.expand(FUDGE_FACTOR, FUDGE_FACTOR, FUDGE_FACTOR);

                    if (7 <= getMagnetLevel(itemStack)) {
                        List<EntityArrow> arrows = world.getEntitiesWithinAABB(EntityArrow.class, bounds);

                        for (EntityArrow arrow : arrows) {
                            if ((arrow.pickupStatus == EntityArrow.PickupStatus.ALLOWED || world.rand.nextFloat() < 0.3f) && arrow.shootingEntity != entity) {
                                EntityItem replacement = new EntityItem(world, arrow.posX, arrow.posY, arrow.posZ, new ItemStack(Items.ARROW));
                                world.spawnEntity(replacement);
                            }
                            world.removeEntity(arrow);
                        }
                    }
                }

                List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bounds);

                for (EntityItem e : list) {
                    if (e.age >= 10) {
                        double x = player.posX - e.posX;
                        double y = player.posY - e.posY;
                        double z = player.posZ - e.posZ;

                        double length = Math.sqrt(x * x + y * y + z * z) * 2;

                        x = x / length + player.motionX / 2;
                        y = y / length + player.motionY / 2;
                        z = z / length + player.motionZ / 2;

                        e.motionX = x;
                        e.motionY = y;
                        e.motionZ = z;
                        e.isAirBorne = true;

                        if (e.collidedHorizontally) {
                            e.motionY += 1;
                        }

                        if (world.rand.nextFloat() < 0.2f) {
                            float pitch = 0.85f - world.rand.nextFloat() * 3f / 10f;
                            if(Config.magnetSound) {
                                world.playSound(e.posX, e.posY, e.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 0.6f, pitch, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab)){
            return;
        }
        for (int i = 0; i <= getMaximumLevel(); i++) {
            subItems.add(new ItemStack(this, 1, i * 2));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isMagnetActive(stack);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            toggleActive(stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private void toggleActive(ItemStack itemStack) {
        itemStack.setItemDamage(itemStack.getItemDamage() ^ 1);
    }

    public boolean isMagnetActive(ItemStack itemStack) {
        return itemStack.getItem() == this && isMagnetActive(itemStack.getItemDamage());
    }

    private boolean isMagnetActive(int damage) {
        return (damage & 0x01) == 1;
    }

    private int getMaximumLevel() {
        return Config.magnetMaxLevel;
    }

    private int getMagnetLevel(ItemStack itemStack) {
        return itemStack.getItemDamage() >> 1;
    }

    private float getRadius(ItemStack itemStack) {
        return Config.magnetBaseRange + (Config.magnetLevelMultiplier * getMagnetLevel(itemStack));
    }

}
