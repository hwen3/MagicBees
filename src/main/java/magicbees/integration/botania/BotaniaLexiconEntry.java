package magicbees.integration.botania;

import net.minecraft.util.text.translation.I18n;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;

/**
 * Created by Elec332 on 18-5-2017.
 */
public class BotaniaLexiconEntry extends LexiconEntry implements IAddonEntry {

    public BotaniaLexiconEntry(String unlocalizedName, LexiconCategory category) {
        super(unlocalizedName, category);
        BotaniaAPI.addEntry(this, category);
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getSubtitle() {
        return I18n.translateToLocal("magicbees.botania.lexicon.subtitle");
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public String getTagline() {
        return unlocalizedName.replace(".name", ".lore");
    }

}
