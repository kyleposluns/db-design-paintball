package com.kyleposluns.paintball.state;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PregameState extends State {


  private int counter;

  private final int countdown;

  private final int requiredPlayers;

  private Map<UUID, PaintballTeam> playerTeams;

  public PregameState(Set<UUID> players, int requiredPlayers, int countdown) {
    super(players);
    this.requiredPlayers = requiredPlayers;
    this.countdown = countdown;
    this.playerTeams = new HashMap<>();
    this.counter = 0;
  }

  @Override
  public void onExit(PaintballPlugin plugin) {
    super.onExit(plugin);

  }

  @Override
  public void eachSecond() {
    if (this.counter % 15 == 0 || (this.counter % this.countdown) <= 5) {
      Bukkit.broadcastMessage(ChatColor.GREEN + String
          .format("%s seconds remaining until the game is starting!",
              this.counter % this.countdown));
    }
    this.counter = this.counter + 1;

  }

  @Override
  public boolean isFinished() {
    return this.counter % this.countdown == 0 && this.players.size() == this.requiredPlayers;
  }

  @Override
  public State nextState() {
    return null;
  }


  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.players.add(event.getPlayer().getUniqueId());
  }

}
