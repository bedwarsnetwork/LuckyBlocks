package network.bedwars.luckyblocks.listener;

import network.bedwars.luckyblocks.LuckyBlocks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    LuckyBlocks.getInstance().getServer().getScheduler()
        .runTaskLater(LuckyBlocks.getInstance(),
            () -> event.getPlayer()
                .setResourcePack("http://direct.bedwars.network/lucky-block-ressourcepack.zip"),
            20L);
  }
}
