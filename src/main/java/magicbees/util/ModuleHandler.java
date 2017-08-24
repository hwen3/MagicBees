package magicbees.util;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import magicbees.MagicBees;
import magicbees.api.module.IMagicBeesInitialisationEvent;
import magicbees.api.module.IMagicBeesModule;
import magicbees.api.module.MagicBeesModule;
import magicbees.api.module.IConfigRegistry;
import magicbees.api.module.IConfiguration;
import magicbees.elec332.corerepack.util.FMLUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Elec332 on 14-8-2017.
 */
public enum ModuleHandler {

	INSTANCE;

	ModuleHandler(){
		this.modules = HashMultimap.create();
	}

	private final Multimap<ModContainer, Triple<IMagicBeesModule, String, List<String>>> modules;

	public void preInit(FMLPreInitializationEvent event){
		moduleLoop:
		for (ASMDataTable.ASMData disc : event.getAsmData().getAll(MagicBeesModule.class.getName())) {
			Map<String, Object> data = disc.getAnnotationInfo();
			String mod = (String) data.get("owner");
			ModContainer owner = FMLUtil.findMod(mod);
			if (owner == null) {
				MagicBees.logger.error("Found module without owner!");
				continue;
			}
			String name = (String) data.get("name");
			String deps_ = (String) data.get("modDependencies");
			String[] deps = Strings.isNullOrEmpty(deps_) ? new String[0] : deps_.split(";");
			List<String> lDeps = Lists.newArrayList();
			if (deps.length != 0) {
				for (String dep : deps) {
					if (Strings.isNullOrEmpty(dep)){
						continue;
					}
					if (!Loader.isModLoaded(dep)) {
						MagicBees.logger.info("Mod " + dep + " not detected, not activating module " + name);// + " (Owned by: " + owner.getModId() + ")");
						continue moduleLoop;
					}
					lDeps.add(dep);
				}
			}
			try {
				Class<?> c = FMLUtil.loadClass(disc.getClassName());
				Object obj = c.newInstance();
				if (!(obj instanceof IMagicBeesModule)){
					MagicBees.logger.error("Module "+name+" is not instanceof IMagicBeesModule, skipping...");
					continue;
				}
				IMagicBeesModule module = (IMagicBeesModule) obj;
				modules.put(owner, Triple.of(module, name, lDeps));
			} catch (Exception e){
				throw new RuntimeException(e);
			}
		}
		runEvent("Preinitializing", data -> data.getLeft().preInit());
	}

	public void registerConfig(final IConfigRegistry configRegistry){
		Set<IConfiguration> configRegistrySet = Sets.newHashSet();
		final IConfigRegistry registry = new IConfigRegistry() {

			@Override
			public void registerConfig(IConfiguration config) {
				if (configRegistrySet.add(config)){
					config.init(this);
				}
			}

			@Override
			public void registerCategoryComment(String category, String comment) {
				configRegistry.registerCategoryComment(category, comment);
			}

		};
		runEvent(null, data -> data.getLeft().registerConfig(registry));
		configRegistry.registerConfig(config -> {

			Configuration wrapped = new Configuration(config.getConfigFile()){

				@Override
				public ConfigCategory getCategory(String category) {
					return config.getCategory("integration." + category);
				}

				@Override
				public void save(){
				}

				@Override
				public void load(){
				}

			};
			configRegistrySet.forEach(config_ -> config_.reload(wrapped));

		});
	}

	public void init(){
		final MBIE event = new MBIE();
		runEvent("Initializing", data -> {

			List<String> deps = data.getRight();
			if (deps.size() == 1){
				event.mod = deps.get(0);
			} else {
				event.mod = null;
			}
			data.getLeft().init(event);

		});
	}

	public void postInit(){
		runEvent("Postinitializing", data -> data.getLeft().postInit());
	}

	private void runEvent(String s, Consumer<Triple<IMagicBeesModule, String, List<String>>> run){
		for (ModContainer mc : modules.keySet()){
			if (s != null) {
				MagicBees.logger.info(s + " modules for mod " + mc.getModId() + "...");
			}
			int modules = 0;
			for (Triple<IMagicBeesModule, String, List<String>> moduleData : this.modules.get(mc)){
				if (s != null) {
					MagicBees.logger.info("   " + s + " module " + moduleData.getMiddle());
				}
				run.accept(moduleData);
				modules++;
			}
			if (s != null) {
				MagicBees.logger.info(s.replace("ing", "ed ") + modules + " module" + (modules > 1 ? "s" : "") + " for mod " + mc.getModId());
			}
		}
	}

	private class MBIE implements IMagicBeesInitialisationEvent {

		private String mod;

		@Nonnull
		@Override
		public Block getBlock(String name) {
			if (Strings.isNullOrEmpty(mod)){
				throw new IllegalArgumentException();
			}
			return getBlock(mod, name);
		}

		@Nonnull
		@Override
		public Item getItem(String name) {
			if (Strings.isNullOrEmpty(mod)){
				throw new IllegalArgumentException();
			}
			return getItem(mod, name);
		}

		@Nonnull
		@Override
		public Block getBlock(String mod, String name) {
			return Utils.getBlock(mod, name);
		}

		@Nonnull
		@Override
		public Item getItem(String mod, String name) {
			return Utils.getItem(mod, name);
		}

	}

}
