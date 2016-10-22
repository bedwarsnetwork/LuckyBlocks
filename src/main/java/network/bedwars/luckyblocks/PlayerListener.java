package network.bedwars.luckyblocks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.rpapi.ResourcePackAPI;
import org.inventivetalent.rpapi.ResourcePackStatusEvent;
import org.inventivetalent.rpapi.Status;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
      @Override
      public void run() {
        event.getPlayer().sendMessage(
            ChatColor.GRAY + "Das Resource-Pack für Lucky Bedwars wird heruntergeladen...");
        ResourcePackAPI.setResourcepack(event.getPlayer(),
            "http://direct.bedwars.network/lucky-block-ressourcepack.zip");
      }
    }, 20L);
  }

  @EventHandler
  public void onResourcePackStatus(ResourcePackStatusEvent event) {
    event.getPlayer().sendMessage(event.getStatus().toString());
    if (event.getStatus() == Status.ACCEPTED) {
      event.getPlayer().sendMessage(
          ChatColor.GRAY + "Das Resource-Pack für Lucky Bedwars wird heruntergeladen...");
    }
    if (event.getStatus() == Status.SUCCESSFULLY_LOADED) {
      event.getPlayer().sendMessage(ChatColor.GREEN
          + "Das Ressource-Pack für Lucky Bedwars wurde erfolgreich installiert...");
    }
    if (event.getStatus() == Status.DECLINED) {
      event.getPlayer().sendMessage(ChatColor.RED
          + "Die Installation des Resource-Packs für Lucky Bedwars wurde von dir abgelehnt. Lucky Blöcke werden bei dir daher als Schwamm dargestellt.");
    }
    if (event.getStatus() == Status.SUCCESSFULLY_LOADED) {
      event.getPlayer().sendMessage(ChatColor.RED
          + "Die Installation des Resource-Packs für Lucky Bedwars ist fehlgeschlagen. Lucky Blöcke werden bei dir daher als Schwamm dargestellt.");
    }
  }
}
