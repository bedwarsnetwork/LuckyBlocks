package network.bedwars.luckyblocks;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockListener implements Listener {

  static Logger logger = Main.logger;

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.SPONGE || !Main.getInstance().isBedwarsEnabled()) {
      return;
    }

    event.getBlock().setType(Material.AIR);
    int eventType = Main.random.nextInt(4);
    if (eventType == 0) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          Main.luckyPotions.get(Main.random.nextInt(Main.luckyPotions.size() - 1) + 1));
    } else if (eventType == 1) {
      getUnluckyEffect(event);
    } else if (eventType == 2) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          Main.luckyItems.get(Main.random.nextInt(Main.luckyItems.size() - 1) + 1));
    } else if (eventType == 3) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          Main.luckyItems.get(Main.random.nextInt(Main.luckyItems.size() - 1) + 1));
    }
  }


  public void getUnluckyEffect(BlockBreakEvent event) {
    Player player = event.getPlayer();
    int eventType = Main.random.nextInt(4);
    if (eventType == 0) {
      player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(),
          player.getLocation().getZ(), Float.valueOf("5.0"), false, false);
    } else if (eventType == 1) {
      player.setHealth(0);
    } else if (eventType == 2) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 100));
    } else if (eventType == 3) {
      player.getInventory().clear();
    }
  }

  public void getLuckyEffect(BlockBreakEvent event) {
    Player player = event.getPlayer();
    int eventType = Main.random.nextInt(4);
    if (eventType == 0) {
      int knockbackLevel = Main.random.nextInt(9);
      ItemStack stick = new ItemStack(Material.STICK, 1);
      ItemMeta stickMeta = stick.getItemMeta();
      stickMeta.addEnchant(Enchantment.KNOCKBACK, knockbackLevel, true);
      stick.setItemMeta(stickMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), stick);
    } else if (eventType == 1) {
      int amount = (Main.random.nextInt(10) + 1) * 5;
      ItemStack luckyBlock = new ItemStack(Material.SPONGE, amount);
      ItemMeta luckyBlockMeta = luckyBlock.getItemMeta();
      luckyBlockMeta.setDisplayName("Lucky Block");
      luckyBlock.setItemMeta(luckyBlockMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), luckyBlock);
    } else if (eventType == 2) {
      int amount = (Main.random.nextInt(10) + 1) * 5;
      ItemStack gold = new ItemStack(Material.SPONGE, amount);
      ItemMeta goldMeta = gold.getItemMeta();
      goldMeta.setDisplayName("&6Gold");
      gold.setItemMeta(goldMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), gold);
    } else if (eventType == 3) {
      player.setHealth(player.getMaxHealth());
    }
  }
}
