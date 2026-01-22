package walksy.customplayers.config;

import main.walksy.lib.api.WalksyLibConfig;
import main.walksy.lib.core.config.impl.LocalConfig;
import main.walksy.lib.core.config.local.Category;
import main.walksy.lib.core.config.local.Option;
import main.walksy.lib.core.config.local.options.BooleanOption;
import main.walksy.lib.core.config.local.options.groups.OptionGroup;
import main.walksy.lib.core.utils.PathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config implements WalksyLibConfig {

    public static boolean modEnabled = true;

    // Entry storage
    public static final ModelEntry PLAYER_ENTRY = new ModelEntry();
    public static final ModelEntry OTHER_ENTRY = new ModelEntry();

    private static final Map<Class<? extends Entity>, ModelEntry> ENTITY_ENTRIES = new LinkedHashMap<>();

    static {
        ENTITY_ENTRIES.put(PlayerEntity.class, PLAYER_ENTRY);
    }

    public static ModelEntry getEntry(Entity entity) {
        if (entity == null) return OTHER_ENTRY;

        if (entity.getType() == EntityType.PLAYER) {
            return PLAYER_ENTRY;
        }

        return OTHER_ENTRY;
    }

    public static ModelEntry getEntry(EntityType<?> type) {
        if (type == EntityType.PLAYER) {
            return PLAYER_ENTRY;
        }
        return OTHER_ENTRY;
    }

    private final Option<Boolean> modEnabledOption = BooleanOption.createBuilder("Mod Enabled", () -> modEnabled, modEnabled, newValue -> modEnabled = newValue)
        .build();

    private final Category generalCategory = Category.createBuilder("General")
        .group(OptionGroup.createBuilder("Global Options")
            .addOption(modEnabledOption)
            .build())
        .build();

    @Override
    public LocalConfig define() {
        return LocalConfig.createBuilder("Custom Players")
            .path(PathUtils.ofConfigDir("customplayers"))
            .category(generalCategory)
            .category(this.createModelCategory("Player", PLAYER_ENTRY))
            .build();
    }

    private Category createModelCategory(String name, ModelEntry entry) {
        Option<Boolean> showModelOption = BooleanOption.createBuilder("Show Model",
            () -> entry.showModel, entry.showModel, val -> entry.showModel = val).build();
        Option<Boolean> showModelIfInvisOption = BooleanOption.createBuilder("Show Model If Invisible",
            () -> entry.showModelIfInvis, entry.showModelIfInvis, val -> entry.showModelIfInvis = val).build();

        Option<Boolean> showArmorOption = BooleanOption.createBuilder("Show Armor",
            () -> entry.showArmor, entry.showArmor, val -> entry.showArmor = val).build();

        Option<Boolean> showMainHandStack = BooleanOption.createBuilder("Show Right Hand Item",
            () -> entry.showRightHand, entry.showRightHand, val -> entry.showRightHand = val).build();

        Option<Boolean> showOffHandStack = BooleanOption.createBuilder("Show Left Hand Item",
            () -> entry.showLeftHand, entry.showLeftHand, val -> entry.showLeftHand = val).build();

        return Category.createBuilder(name)
            .group(OptionGroup.createBuilder("General Body Options")
                .addOption(showModelOption)
                .addOption(showModelIfInvisOption)
                .build())
            .group(OptionGroup.createBuilder("Armor Options")
                .addOption(showArmorOption)
                .build())
            .group(OptionGroup.createBuilder("Hand Held Item Options")
                .addOption(showMainHandStack)
                .addOption(showOffHandStack)
                .build())
            .build();
    }

    public static class ModelEntry {
        public boolean showModel = true;
        public boolean showModelIfInvis = true;
        public boolean showArmor = true;
        public boolean showRightHand = true;
        public boolean showLeftHand = true;
    }
}
