package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.player.PlayerManager;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
    }
  }

  @Override
  public <R> R accept(StateVisitor<R> visitor) {
    return visitor.visitPostGameState(this);
  }

  @Override
  public boolean isFinished() {
    return players.getAllPlayers() == 0;
  }

  @Override
  public AbstractState nextState() {
    return new PregameState(plugin, null);
  }
}
