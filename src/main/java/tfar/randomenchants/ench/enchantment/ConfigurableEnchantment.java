package tfar.randomenchants.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import tfar.randomenchants.Config;

public class ConfigurableEnchantment extends Enchantment {

	private Config.Restriction config;
	private final boolean isCurse;

	protected ConfigurableEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots, boolean curse, Config.Restriction defaultRestriction) {
		super(rarityIn, typeIn, slots);
		config = defaultRestriction;
		isCurse = curse;
	}

	/**
	 *
	 * @param stack the item being enchanted
	 * @return if the enchantment can be applied at all
	 */
	@Override
	public boolean canApply(ItemStack stack) {
		return config != Config.Restriction.DISABLED && super.canApply(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return config != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean isAllowedOnBooks() {
		return config == Config.Restriction.NORMAL;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return isCurse() || config == Config.Restriction.ANVIL;
	}

	public boolean isCurse() {
		return isCurse;
	}

	/**
	 * Checks if the enchantment can be sold by villagers in their trades.
	 */
	public boolean canVillagerTrade() {
		return config != Config.Restriction.DISABLED;
	}

	/**
	 * Checks if the enchantment can be applied to loot table drops.
	 */
	public boolean canGenerateInLoot() {
		return config != Config.Restriction.DISABLED;
	}

	public void setRestriction(Config.Restriction restriction) {
		config = restriction;
	}
}
