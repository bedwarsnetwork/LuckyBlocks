package network.bedwars.luckyblocks.listener;

import io.github.bedwarsrel.BedwarsRel.Game.Game;
import java.util.ArrayList;
import java.util.List;
import network.bedwars.luckyblocks.LuckyBlocks;
import org.bukkit.ChatColor;
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

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.getBlock().getType() != Material.SPONGE || !LuckyBlocks.getInstance()
        .isBedwarsEnabled()) {
      return;
    }

    io.github.bedwarsrel.BedwarsRel.Main bedwarsPlugin = (io.github.bedwarsrel.BedwarsRel.Main) LuckyBlocks
        .getInstance().getServer().getPluginManager().getPlugin("BedwarsRel");

    Game game = bedwarsPlugin.getGameManager().getGameOfPlayer(event.getPlayer());

    if (game == null) {
      return;
    }
    event.getBlock().setType(Material.AIR);
    int eventType = LuckyBlocks.random.nextInt(4 + 1);
    if (eventType == 0) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          LuckyBlocks.getInstance().getLuckyPotions().get(
              LuckyBlocks.random.nextInt(LuckyBlocks.getInstance().getLuckyPotions().size() - 1)
                  + 1));
    } else if (eventType == 1) {
      getUnluckyEffect(event, game);
    } else if (eventType == 2) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          LuckyBlocks.getInstance().getLuckyItems()
              .get(LuckyBlocks.random.nextInt(LuckyBlocks.getInstance().getLuckyItems().size() - 1)
                  + 1));
    } else if (eventType == 3) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
          LuckyBlocks.getInstance().getLuckyItems()
              .get(LuckyBlocks.random.nextInt(LuckyBlocks.getInstance().getLuckyItems().size() - 1)
                  + 1));
    } else if (eventType == 4) {
      getLuckyEffect(event, game);
    }
  }


  private void getUnluckyEffect(BlockBreakEvent event, Game game) {
    Player player = event.getPlayer();
    int eventType = LuckyBlocks.random.nextInt(9 + 1);
    if (eventType == 0) {
      game.setPlayerDamager(player, null);
      player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(),
          player.getLocation().getZ(), Float.valueOf("5.0"), false, false);
    } else if (eventType == 1) {
      game.setPlayerDamager(player, null);
      player.setHealth(0);
      player.sendMessage(ChatColor.RED + "Der LuckyBlock hat dich getötet.");
    } else if (eventType == 2) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 10));
    } else if (eventType == 3) {
      player.getInventory().clear();
      player.sendMessage(ChatColor.RED + "Der LuckyBlock hat dein Inventar gelöscht.");
    } else if (eventType == 4) {
      int length = (LuckyBlocks.random.nextInt(10) + 1) * 20;
      game.setPlayerDamager(player, null);
      player.setFireTicks(length);
    } else if (eventType == 5) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 10));
    } else if (eventType == 6) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 10));
    } else if (eventType == 7) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 15, 10));
      player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 15, 10));
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 15, 10));
    } else if (eventType == 8) {
      Player randomPlayer = getRandomTeamPlayer(game, player);
      if (randomPlayer != null) {
        game.setPlayerDamager(player, null);
        randomPlayer.setHealth(0);
        game.broadcast(
            randomPlayer.getDisplayName() + ChatColor.RESET + " wurde durch den LuckyBlock von "
                + event.getPlayer().getDisplayName() + ChatColor.RESET + " getötet.");
      }
    } else if (eventType == 9) {
      Player randomPlayer = getRandomTeamPlayer(game, player);
      if (randomPlayer != null) {
        randomPlayer.setHealth(0);
        game.broadcast("Das Inventar von " + randomPlayer.getDisplayName() + ChatColor.RESET
            + " wurde durch den LuckyBlock von " + event.getPlayer().getDisplayName()
            + ChatColor.RESET + " gelöscht.");
      }
    }
  }

  private void getLuckyEffect(BlockBreakEvent event, Game game) {
    Player player = event.getPlayer();
    int eventType = LuckyBlocks.random.nextInt(9 + 1);
    if (eventType == 0) {
      int knockbackLevel = LuckyBlocks.random.nextInt(9);
      ItemStack stick = new ItemStack(Material.STICK, 1);
      ItemMeta stickMeta = stick.getItemMeta();
      stickMeta.addEnchant(Enchantment.KNOCKBACK, knockbackLevel, true);
      stick.setItemMeta(stickMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), stick);
    } else if (eventType == 1) {
      int amount = (LuckyBlocks.random.nextInt(10) + 1) * 5;
      ItemStack luckyBlock = new ItemStack(Material.SPONGE, amount);
      ItemMeta luckyBlockMeta = luckyBlock.getItemMeta();
      luckyBlockMeta.setDisplayName("Lucky Block");
      luckyBlock.setItemMeta(luckyBlockMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), luckyBlock);
    } else if (eventType == 2) {
      int amount = (LuckyBlocks.random.nextInt(10) + 1) * 5;
      ItemStack gold = new ItemStack(Material.GOLD_INGOT, amount);
      ItemMeta goldMeta = gold.getItemMeta();
      goldMeta.setDisplayName(ChatColor.YELLOW + "Gold");
      gold.setItemMeta(goldMeta);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), gold);
    } else if (eventType == 3) {
      player.setHealth(player.getMaxHealth());
      player.sendMessage(ChatColor.GREEN + "Dein LuckyBlock hat dich geheilt.");
    } else if (eventType == 4) {
      int amount = (LuckyBlocks.random.nextInt(10) + 1) * 5;
      ItemStack snowball = new ItemStack(Material.SNOW_BALL, amount);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), snowball);
    } else if (eventType == 5) {
      int amount = (LuckyBlocks.random.nextInt(10) + 1) * 5;
      ItemStack arrow = new ItemStack(Material.ARROW, amount);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), arrow);
    } else if (eventType == 6) {
      int amount = (LuckyBlocks.random.nextInt(10) + 1) * 5;
      ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, amount);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), apple);
    } else if (eventType == 7) {
      ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 1);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), enderpearl);
    } else if (eventType == 8) {
      Player randomPlayer = getRandomPlayer(game, false);
      if (randomPlayer != null) {
        game.setPlayerDamager(player, null);
        randomPlayer.setHealth(0);
        game.broadcast(
            randomPlayer.getDisplayName() + ChatColor.RESET + " wurde durch den LuckyBlock von "
                + player.getDisplayName() + ChatColor.RESET + " getötet.");
      }
    } else if (eventType == 9) {
      Player randomPlayer = getRandomPlayer(game, false);
      if (randomPlayer != null) {
        randomPlayer.setHealth(0);
        game.broadcast("Das Inventar von " + randomPlayer.getDisplayName() + ChatColor.RESET
            + " wurde durch den LuckyBlock von " + player.getDisplayName() + ChatColor.RESET
            + " gelöscht.");
      }
    }
  }

  private Player getRandomPlayer(Game game, Boolean all) {
    ArrayList<Player> players = game.getPlayers();
    ArrayList<Player> spectators = new ArrayList<>();
    if (!all) {
      players.removeAll(game.getTeamPlayers());
    }

    for (Player player : players) {
      if (game.isSpectator(player)) {
        spectators.add(player);
      }
    }
    players.removeAll(spectators);

    if (players.size() > 0) {
      int selectedPlayer = LuckyBlocks.random.nextInt(players.size());
      return players.get(selectedPlayer);
    }
    return null;
  }

  private Player getRandomTeamPlayer(Game game, Player player) {
    List<Player> players = game.getPlayerTeam(player).getPlayers();
    ArrayList<Player> spectators = new ArrayList<>();
    players.remove(player);
    for (Player aPlayer : players) {
      if (game.isSpectator(aPlayer)) {
        spectators.add(player);
      }
    }
    players.removeAll(spectators);

    if (players.size() > 0) {
      int selectedPlayer = LuckyBlocks.random.nextInt(players.size());
      return players.get(selectedPlayer);
    }
    return null;
  }
}
