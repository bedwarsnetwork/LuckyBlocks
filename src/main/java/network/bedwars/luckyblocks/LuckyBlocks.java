package network.bedwars.luckyblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import network.bedwars.luckyblocks.listener.BlockListener;
import network.bedwars.luckyblocks.listener.PlayerListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuckyBlocks extends JavaPlugin {

  public static Random random = new Random();
  @Getter
  private static LuckyBlocks instance;
  @Getter
  private static List<ItemStack> luckyItems;
  @Getter
  private static List<ItemStack> luckyPotions;

  public void onEnable() {
    instance = this;

    LuckyBlocks.luckyPotions = generateLuckyPotions();
    Collections.shuffle(LuckyBlocks.luckyPotions, new Random());

    LuckyBlocks.luckyItems = generateLuckyItems();
    Collections.shuffle(LuckyBlocks.luckyItems, new Random());

    this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

  }

  public void onDisable() {
  }

  public boolean isBedwarsEnabled() {
    return LuckyBlocks.getInstance().getServer().getPluginManager().isPluginEnabled("BedwarsRel");
  }


  private List<ItemStack> generateLuckyPotions() {
    List<ItemStack> items = new ArrayList<>();

    int potionTypeCount = 0;

    List<PotionEffectType> positivePotionEffects =
        Arrays.asList(PotionEffectType.ABSORPTION, PotionEffectType.DAMAGE_RESISTANCE,
            PotionEffectType.FIRE_RESISTANCE, PotionEffectType.HEALTH_BOOST,
            PotionEffectType.INCREASE_DAMAGE, PotionEffectType.INVISIBILITY,
            PotionEffectType.REGENERATION, PotionEffectType.SATURATION, PotionEffectType.SPEED);

    List<PotionEffectType> negativePotionEffects = Arrays.asList(PotionEffectType.BLINDNESS,
        PotionEffectType.CONFUSION, PotionEffectType.GLOWING, PotionEffectType.HUNGER,
        PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING,
        PotionEffectType.WEAKNESS, PotionEffectType.WITHER);

    while (potionTypeCount < 3) {
      List<PotionEffectType> potionEffects = new ArrayList<>();
      if (potionTypeCount == 0) {
        potionEffects.addAll(positivePotionEffects);

      } else if (potionTypeCount == 1) {
        potionEffects.addAll(negativePotionEffects);
      } else if (potionTypeCount == 2) {
        potionEffects.addAll(positivePotionEffects);
        potionEffects.addAll(negativePotionEffects);
      }
      int potionCount = 0;
      while (potionCount < 50) {
        ItemStack potion;
        if (potionTypeCount == 1) {
          potion = new ItemStack(Material.SPLASH_POTION, 1);
        } else {
          potion = new ItemStack(Material.POTION, 1);
        }
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        int potionEffectAmount = LuckyBlocks.random.nextInt(7 - 1) + 1;
        int potionEffectCount = 0;
        while (potionEffectCount < potionEffectAmount) {
          int potionEffectValuesSize = potionEffects.size();
          int potionEffectAmplifier = LuckyBlocks.random.nextInt(5);
          int potionEffectDuration = (LuckyBlocks.random.nextInt(7 - 1) * 10) * 20;

          PotionEffectType potionEffectType =
              potionEffects.get(LuckyBlocks.random.nextInt(potionEffectValuesSize - 1) + 1);

          potionMeta.addCustomEffect(
              new PotionEffect(potionEffectType, potionEffectDuration, potionEffectAmplifier),
              true);

          if (potionTypeCount == 0) {
            potionMeta.setDisplayName("Lucky Potion");
          } else if (potionTypeCount == 1) {
            potionMeta.setDisplayName("Unlucky Potion");
          } else if (potionTypeCount == 2) {
            potionMeta.setDisplayName("??? Potion");
            potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
          }

          potionEffectCount++;
        }
        potion.setItemMeta(potionMeta);
        items.add(potion);
        potionCount++;
      }
      potionTypeCount++;
    }

    return items;
  }

  private List<ItemStack> generateLuckyItems() {
    List<ItemStack> luckyItems = new ArrayList<>();

    List<Enchantment> toolEnchantments =
        Arrays.asList(Enchantment.DAMAGE_ALL, Enchantment.DIG_SPEED, Enchantment.DURABILITY,
            Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK);
    List<ItemStack> tools =
        Arrays.asList(new ItemStack(Material.WOOD_SWORD, 1), new ItemStack(Material.GOLD_SWORD, 1),
            new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.DIAMOND_SWORD, 1),
            new ItemStack(Material.WOOD_PICKAXE, 1), new ItemStack(Material.GOLD_PICKAXE, 1),
            new ItemStack(Material.IRON_PICKAXE, 1), new ItemStack(Material.DIAMOND_PICKAXE, 1));
    List<Enchantment> armorEnchantments =
        Arrays.asList(Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS,
            Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_PROJECTILE, Enchantment.THORNS);
    List<ItemStack> armor = Arrays.asList(new ItemStack(Material.DIAMOND_BOOTS, 1),
        new ItemStack(Material.DIAMOND_LEGGINGS, 1), new ItemStack(Material.DIAMOND_CHESTPLATE, 1),
        new ItemStack(Material.DIAMOND_HELMET, 1));
    List<Enchantment> bowEnchantments = Arrays.asList(Enchantment.ARROW_DAMAGE,
        Enchantment.ARROW_FIRE, Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK);

    int itemTypeCount = 0;
    while (itemTypeCount < 3) {
      List<Enchantment> enchantments = new ArrayList<>();
      List<ItemStack> items = new ArrayList<>();
      if (itemTypeCount == 0) {
        enchantments.addAll(toolEnchantments);
        items.addAll(tools);

      } else if (itemTypeCount == 1) {
        enchantments.addAll(armorEnchantments);
        items.addAll(armor);
      } else if (itemTypeCount == 2) {
        enchantments.addAll(bowEnchantments);
        items.add(new ItemStack(Material.BOW, 1));
      }
      int itemCount = 0;
      while (itemCount < 50) {
        ItemStack item = items.get(LuckyBlocks.random.nextInt(items.size())).clone();
        ItemMeta itemMeta = item.getItemMeta();
        int enchantmentAmount = LuckyBlocks.random.nextInt(5 - 1) + 1;
        int enchantmentAmountCount = 0;
        while (enchantmentAmountCount < enchantmentAmount) {
          int enchantmentValuesSize = enchantments.size();
          int enchantmentLevel = LuckyBlocks.random.nextInt(7 - 1) + 1;

          itemMeta.addEnchant(enchantments.get(
              LuckyBlocks.random.nextInt(enchantmentValuesSize - 1) + 1),
              enchantmentLevel, true);
          enchantmentAmountCount++;
        }
        item.setItemMeta(itemMeta);
        luckyItems.add(item);
        itemCount++;
      }
      itemTypeCount++;
    }

    return luckyItems;
  }
}
