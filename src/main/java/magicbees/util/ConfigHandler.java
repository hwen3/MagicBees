package magicbees.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraftforge.common.config.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * Created by Elec332 on 12-8-2017.
 */
public final class ConfigHandler {

	public ConfigHandler(Configuration config){
		this.config = Preconditions.checkNotNull(config);
		this.configs = Sets.newHashSet();
		this.categoryComments = Maps.newHashMap();
		this.reloadBlocked = false;
		this.lock = false;
	}

	private final Configuration config;
	private final Set<IConfiguration> configs;
	private final Map<String, String> categoryComments;
	private boolean reloadBlocked, lock;

	public void registerConfig(IConfiguration config){
		if (lock){
			return;
		}
		this.reloadBlocked = true;
		if (configs.add(config)){
			config.init(this);
		}
		this.reloadBlocked = false;
	}

	public void registerCategoryComment(String category, String comment){
		if (lock){
			throw new IllegalStateException("Config comments have to be registered in config init!");
		}
		if (categoryComments.containsKey(category)){
			throw new IllegalArgumentException("Comment for category "+category+" has already been registered.");
		}
		categoryComments.put(category, comment);
	}

	public void reload(){
		if (reloadBlocked){
			return;
		}
		if (!lock){
			lock = true;
		}
		config.load();
		for (Map.Entry<String, String> entry : categoryComments.entrySet()){
			config.addCustomCategoryComment(entry.getKey(), entry.getValue());
		}
		for (IConfiguration c : configs){
			c.reload(config);
		}
		if (config.hasChanged()){
			config.save();
		}
	}

}
