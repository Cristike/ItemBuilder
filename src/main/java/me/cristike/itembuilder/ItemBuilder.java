package me.cristike.itembuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemBuilder {
    /**
     * The type of the item.
     */
    @NotNull
    private Material material;

    /**
     * The name of the item.
     */
    @Nullable
    private String displayName;

    /**
     * The lore of the item.
     */
    @Nullable
    private List<String> lore;

    /**
     * The custom model data of the item.
     */
    private int customModelData = 0;

    /**
     * Whether the item can break or not.
     */
    private boolean unbreakable;

    /**
     * The enchantments of the item.
     */
    @NotNull
    private final HashMap<Enchantment, Integer> ENCHANTS = new HashMap<>();

    /**
     * The flags of the item.
     */
    @NotNull
    private final List<ItemFlag> FLAGS = new ArrayList<>();

    /**
     * Constructs a new ItemBuilder object with the given material.
     */
    public ItemBuilder(@NotNull Material material) {
        this.material = material;
    }

    /**
     * Constructs a new ItemBuilder object with the given material and display name.
     */
    public ItemBuilder(@NotNull Material material, @Nullable String displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    /**
     * Constructs a new ItemBuilder object with the given material and display name.
     * It also runs the color functions if specified.
     */
    public ItemBuilder(@NotNull Material material, @Nullable String displayName, boolean color) {
        this.material = material;
        this.displayName = displayName;

        if (!color) return;
        colorDisplayName();
    }

    /**
     * Constructs a new ItemBuilder object with the given material, display name and lore.
     */
    public ItemBuilder(@NotNull Material material, @Nullable String displayName, @Nullable List<String> lore) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
    }

    /**
     * Constructs a new ItemBuilder object with the given material, display name and lore.
     * It also runs the color functions if specified.
     */
    public ItemBuilder(@NotNull Material material, @Nullable String displayName, @Nullable List<String> lore, boolean color) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;

        if (!color) return;
        colorDisplayName();
        colorLore();
    }

    /**
     * Constructs a new ItemBuilder object from the given ItemStack.
     */
    public ItemBuilder(@NotNull ItemStack item) {
        this.material = item.getType();

        if (item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        this.displayName = (meta.hasDisplayName()) ? meta.getDisplayName() : null;
        this.lore = (meta.hasLore()) ? meta.getLore() : null;
        this.customModelData = meta.getCustomModelData();
        this.unbreakable = meta.isUnbreakable();
        this.ENCHANTS.putAll(meta.getEnchants());
        this.FLAGS.addAll(meta.getItemFlags());
    }

    /**
     * Builds a new ItemStack.
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);
        meta.setUnbreakable(unbreakable);
        ENCHANTS.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        FLAGS.forEach(meta::addItemFlags);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Builds a new ItemStack with the given amount.
     */
    public ItemStack build(int amount) {
        if (amount < 1) amount = 1;

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);
        meta.setUnbreakable(unbreakable);
        ENCHANTS.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        FLAGS.forEach(meta::addItemFlags);

        item.setItemMeta(meta);
        item.setAmount(Math.min(material.getMaxStackSize(), amount));

        return item;
    }

    /**
     * Changes the color codes into their appropriate form.
     */
    public void colorDisplayName() {
        if (displayName == null) return;
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    /**
     * Replaces the given target with the given replacement from the display name.
     */
    public void replaceDisplayName(@NotNull String target, @NotNull String replacement) {
        if (displayName == null) return;
        displayName = displayName.replace(target, replacement);
    }

    /**
     * Replaces the given targets with the given replacements from the display name.
     */
    public void replaceDisplayName(@NotNull String ... targetsAndReplacements) {
        if (displayName == null) return;
        if (targetsAndReplacements.length % 2 != 0) return;

        for (int i = 0; i < targetsAndReplacements.length - 1; i += 2)
            displayName = displayName.replace(targetsAndReplacements[i], targetsAndReplacements[i + 1]);
    }

    /**
     * Changes the color codes into their appropriate form and
     * replaces the given target with the given replacement from the display name.
     */
    public void colorAndReplaceDisplayName(@NotNull String target, @NotNull String replacement) {
        if (displayName == null) return;
        displayName = ChatColor.translateAlternateColorCodes('&', displayName.replace(target, replacement));
    }

    /**
     * Changes the color codes into their appropriate form and
     * replaces the given targets with the given replacements from the display name.
     */
    public void colorAndReplaceDisplayName(@NotNull String ... targetsAndReplacements) {
        if (displayName == null) return;
        if (targetsAndReplacements.length % 2 != 0) return;

        for (int i = 0; i < targetsAndReplacements.length - 1; i += 2)
            displayName = displayName.replace(targetsAndReplacements[i], targetsAndReplacements[i + 1]);
        displayName = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    /**
     * Changes the color codes into their appropriate form for each line of the lore.
     */
    public void colorLore() {
        if (lore == null) return;
        if (lore.isEmpty()) return;

        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
    }

    /**
     * Replaces the given target with the given replacement for each line of the lore.
     */
    public void replaceLore(@NotNull String target, @NotNull String replacement) {
        if (lore == null) return;
        if (lore.isEmpty()) return;

        lore.replaceAll(line -> line.replace(target, replacement));
    }

    /**
     * Replaces the given targets with the given replacements for each line of the lore.
     */
    public void replaceLore(@NotNull String ... targetsAndReplacements) {
        if (lore == null) return;
        if (targetsAndReplacements.length % 2 != 0) return;
        if (lore.isEmpty()) return;

        lore.replaceAll(line -> {
            String result = line;
            for (int i = 0; i < targetsAndReplacements.length - 1; i += 2)
                result = result.replace(targetsAndReplacements[i], targetsAndReplacements[i + 1]);
            return result;
        });
    }

    /**
     * Replaces the given target with the given replacement
     * and changes the color codes into their appropriate form for each line of the lore.
     */
    public void colorAndReplaceLore(@NotNull String target, @NotNull String replacement) {
        if (lore == null) return;
        if (lore.isEmpty()) return;

        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line.replace(target, replacement)));
    }

    /**
     * Replaces the given targets with the given replacements
     * and changes the color codes into their appropriate form for each line of the lore.
     */
    public void colorAndReplaceLore(@NotNull String ... targetsAndReplacements) {
        if (lore == null) return;
        if (targetsAndReplacements.length % 2 != 0) return;
        if (lore.isEmpty()) return;

        lore.replaceAll(line -> {
            String result = line;
            for (int i = 0; i < targetsAndReplacements.length - 1; i += 2)
                result = ChatColor.translateAlternateColorCodes('&', result.replace(targetsAndReplacements[i], targetsAndReplacements[i + 1]));
            return result;
        });
    }

    /**
     * Checks if the given enchantment exists on the item.
     */
    public boolean hasEnchant(Enchantment enchantment) {
        return ENCHANTS.containsKey(enchantment);
    }

    /**
     * Returns a list of the enchantments.
     */
    @NotNull
    public List<Enchantment> getEnchantments() {
        return new ArrayList<>(ENCHANTS.keySet());
    }

    /**
     * Returns the level of the given enchantment from the item.
     */
    public int getEnchantLevel(@NotNull Enchantment enchantment) {
        return ENCHANTS.getOrDefault(enchantment, 0);
    }

    /**
     * Adds the given enchantment to the item.
     */
    public void addEnchant(@NotNull Enchantment enchantment, int level) {
        if (level < 1) level = 1;
        ENCHANTS.put(enchantment, level);
    }

    /**
     * Removes the given enchantment from the item.
     */
    public void removeEnchant(@NotNull Enchantment enchantment) {
        ENCHANTS.remove(enchantment);
    }

    /**
     * Removes all enchantments from the item.
     */
    public void clearEnchants() {
        ENCHANTS.clear();
    }

    /**
     * Checks if the item has the given flag.
     */
    public boolean hasFlag(@NotNull ItemFlag flag) {
        return FLAGS.contains(flag);
    }

    /**
     * Adds the given flag to the item.
     */
    public void addFlag(@NotNull ItemFlag flag) {
        FLAGS.add(flag);
    }

    /**
     * Adds the given flags to the item.
     */
    public void addFlags(@NotNull ItemFlag ... flags) {
        Collections.addAll(FLAGS, flags);
    }

    /**
     * Removes the given flag from the item.
     */
    public void removeFlag(@NotNull ItemFlag flag) {
        FLAGS.remove(flag);
    }

    /**
     * Removes the given flags from the item.
     */
    public void removeFlags(@NotNull ItemFlag ... flags) {
        for (ItemFlag flag : flags)
            FLAGS.remove(flag);
    }

    /**
     * Removes all the flags from the item.
     */
    public void clearFlags() {
        FLAGS.clear();
    }

    /**
     * Getter for material.
     */
    @NotNull
    public Material material() {
        return material;
    }

    /**
     * Setter for material;
     */
    public void material(@NotNull Material m) {
        material = m;
    }

    /**
     * Setter for material from a String.
     */
    public void material(@NotNull String s) {
        try {
            material = Material.valueOf(s);
        } catch (Exception ignored) {}
    }

    /**
     * Getter for displayName.
     */
    @Nullable
    public String displayName() {
        return displayName;
    }

    /**
     * Setter for displayName.
     */
    public void displayName(@Nullable String dn) {
        displayName = dn;
    }

    /**
     * Getter for lore.
     */
    @Nullable
    public List<String> lore() {
        return lore;
    }

    /**
     * Setter for lore.
     */
    public void lore(@Nullable List<String> l) {
        lore = l;
    }

    /**
     * Getter for customModelData.
     */
    public int customModelData() {
        return customModelData;
    }

    /**
     * Setter for customModelData.
     */
    public void customModelData(int cmd) {
        customModelData = cmd;
    }

    /**
     * Getter for unbreakable.
     */
    public boolean unbreakable() {
        return unbreakable;
    }

    /**
     * Setter for unbreakable.
     */
    public void unbreakable(boolean u) {
        unbreakable = u;
    }

    /**
     * Returns a copy of the flags list.
     */
    @NotNull
    public List<ItemFlag> flags() {
        return new ArrayList<>(FLAGS);
    }
}
