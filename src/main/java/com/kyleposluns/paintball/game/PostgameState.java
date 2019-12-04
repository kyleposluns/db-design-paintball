package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.player.PlayerManager;


import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class PostgameState extends AbstractState {

  private UUID winner;

  PostgameState(PaintballPlugin plugin, PlayerManager players, UUID winner) {
    super(plugin, players);
    this.winner = winner;
  }

  @Override
  public void onEnter() {
    super.onEnter();

    Player player = Bukkit.getPlayer(this.winner);
    if (player != null) {
      Bukkit.broadcastMessage(
              ChatColor.GREEN + player.getName() + org.bukkit.ChatColor.GRAY + " has won the game!");

      player.teleport(plugin.getRespawnLocation());

      if (this.players.isInGame(player.getUniqueId())) {
        this.players.remove(player.getUniqueId());
      }

    }
  }

  @Override
  public <R> R accept(StateVisitor<R> visitor) {
    return visitor.visitPostGameState(this);
  }

  @Override
  public boolean isFinished() {
    return players.getActivePlayers().size() == 0;
  }

  @Override
  public AbstractState nextState() {
    try {
      return new PregameState(plugin, new DBPreferences(this.plugin.getConnection(), "EASY"),
              this.players);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error in making new Difficulty");
    }
  }

}
