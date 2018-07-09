package magicbees.init;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IEffectData;
import forestry.core.genetics.alleles.AlleleFloat;
import magicbees.MagicBees;
import magicbees.bees.allele.AlleleEffectCrumbling;
import magicbees.bees.allele.AlleleEffectTransmuting;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectPotion;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectSpawnMob;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleFlowerProvider;
import magicbees.elec332.corerepack.compat.forestry.bee.FlowerProvider;
import magicbees.util.MagicBeesResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created by Elec332 on 25-8-2016.
 */
public final class AlleleRegister {

    public static IAlleleFloat speedBlinding;
    public static AlleleFlowerProvider flowersBookshelf;
    public static IAlleleBeeEffect effectSlowSpeed, effectWithering, effectTransmuting, effectCrumbling, effectInvisibility,
            effectMoveSpeed, effectCleansing, effectDigSpeed;
    public static AlleleEffectSpawnMob spawnWolf, spawnBats, spawnCow, spawnChicken, spawnPig,
            spawnSheep, spawnCat, spawnHorse, spawnGhast, spawnSpider, spawnBlaze, spawnZombie;

    public static void init(){
        flowersBookshelf = new AlleleFlowerProvider(new MagicBeesResourceLocation("flowersBookshelf"), new FlowerProvider("flowersBookshelf"));
        flowersBookshelf.registerAcceptableFlower(Blocks.BOOKSHELF);

        effectSlowSpeed = new AlleleEffectPotion(new MagicBeesResourceLocation("effectSlowSpeed"), new PotionEffect(MobEffects.SLOWNESS, 3));
        effectWithering = new AlleleEffectPotion(new MagicBeesResourceLocation("effectWithering"), new PotionEffect(MobEffects.WITHER, 15));
        effectInvisibility = new AlleleEffectPotion(new MagicBeesResourceLocation("effectInvisibility"), new PotionEffect(MobEffects.INVISIBILITY, 10)).setBypassesArmour();
        effectMoveSpeed = new AlleleEffectPotion(new MagicBeesResourceLocation("moveSpeed"), new PotionEffect(MobEffects.SPEED, 15)).setBypassesArmour();
        effectDigSpeed = new AlleleEffectPotion(new MagicBeesResourceLocation("digSpeed"), new PotionEffect(MobEffects.HASTE, 15)).setBypassesArmour();

        effectCrumbling = new AlleleEffectCrumbling(new MagicBeesResourceLocation("crumbling"), MagicBees.crumblingHandler);
        effectTransmuting = new AlleleEffectTransmuting(new MagicBeesResourceLocation("effectTransmuting"), MagicBees.transmutationController);

        effectCleansing = new AlleleEffectThrottled(new MagicBeesResourceLocation("effectCleansing")) {

            @Override
            public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
                List<EntityPlayer> entityList = getEntitiesInRange(genome, housing, EntityPlayer.class);

                for (EntityPlayer player : entityList) {
                    player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
                }
                storedData.setInteger(0, 0);

                return storedData;
            }

            @Override
            @SuppressWarnings("all")
            public IEffectData validateStorage(IEffectData storedData) {
                if (storedData == null){
                    storedData = new EffectData(1, 0, 0);
                }
                return storedData;
            }

        }.setThrottle(200);

        spawnWolf = newMobEffect("Canine", false, "Wolf").setThrottle(650).setSpawnChance(40).setMaxMobsInArea(2);
        spawnBats = newMobEffect("Batty", false, "Bat").setThrottle(150);
        spawnCow = newMobEffect("Bovine", true, "Cow").setThrottle(640).setMaxMobsInArea(3);
        spawnChicken = newMobEffect("Chicken", true, "Chicken").setThrottle(20).setMaxMobsInArea(20);
        spawnPig = newMobEffect("Porcine", true, "Pig").setThrottle(350).setMaxMobsInArea(4);
        spawnSheep = newMobEffect("Sheep", false, "Sheep").setThrottle(450).setMaxMobsInArea(5);
        spawnCat = newMobEffect("Catty", false, "Ozelot").setThrottle(702).setSpawnChance(60).setMaxMobsInArea(2);
        spawnHorse = newMobEffect("Horse", false, "EntityHorse").setThrottle(450).setSpawnChance(59).setMaxMobsInArea(2);
        spawnGhast = newMobEffect("Ghastly", false, "Ghast").setThrottle(2060).setSpawnChance(10).setMaxMobsInArea(1);
        spawnSpider = newMobEffect("Spidery", false, "Spider").setThrottle(400).setSpawnChance(70).setMaxMobsInArea(4);
        spawnBlaze = newMobEffect("Ablaze", false, "Blaze").setThrottle(800).setSpawnChance(60).setMaxMobsInArea(2);
        spawnZombie = newMobEffect("Brainy", false, "Brainy").setAngryOnPlayers().setThrottle(800).setMaxMobsInArea(2);

        speedBlinding = new AlleleFloat(MagicBees.modid, "speed", "Blinding", 2.0f, false);
        AlleleManager.alleleRegistry.registerAllele(speedBlinding, EnumBeeChromosome.SPEED);

    }

    private static AlleleEffectSpawnMob newMobEffect(String name, boolean dominant, String mob){
        return new AlleleEffectSpawnMob(new MagicBeesResourceLocation(name), new ResourceLocation(mob));
    }

}
