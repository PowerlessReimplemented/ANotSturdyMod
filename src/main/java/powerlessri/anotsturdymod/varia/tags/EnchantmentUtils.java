package powerlessri.anotsturdymod.varia.tags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class EnchantmentUtils {

    private EnchantmentUtils() {
    }


    public static boolean hasEnchantment(ItemStack stack, Enchantment target, int level) {
        for (Map.Entry<Enchantment, Integer> enchant : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            if(enchant.getKey() == target && (enchant.getValue() == level || level == -1)) {
                return true;
            }
        }
        return false;
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
