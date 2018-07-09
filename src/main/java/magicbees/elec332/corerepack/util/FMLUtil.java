package magicbees.elec332.corerepack.util;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.annotation.Nullable;

/**
 * Created by Elec332 on 17-10-2016.
 */
public class FMLUtil {
    public static Class<?> loadClass(String clazz) throws ClassNotFoundException {
        return Class.forName(clazz, true, Loader.instance().getModClassLoader());
    }

    @Nullable
    public static ModContainer findMod(String modId){
        for (ModContainer mc : Loader.instance().getActiveModList()){
            if (mc.getModId().equals(modId)){
                return mc;
            }
        }
        return null;
    }

}
