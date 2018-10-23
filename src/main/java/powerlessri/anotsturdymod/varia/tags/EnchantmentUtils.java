package powerlessri.anotsturdymod.varia.tags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class EnchantmentUtils {

    private EnchantmentUtils() {
    }


    public static boolean hasEnchantment(ItemStack stack, Enchantment target, int level) {
        int enchantLevel = getEnchantLevel(stack, target);
        return enchantLevel == level || (enchantLevel != 0 && level == -1);
    }

    public static int getEnchantLevel(ItemStack stack, Enchantment target) {
        for (Map.Entry<Enchantment, Integer> enchant : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            if (enchant.getKey() == target) {
                return enchant.getValue();
            }
        }
        return 0;
    }

}
