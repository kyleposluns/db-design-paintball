package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.player.PaintballPlayer;
import com.kyleposluns.paintball.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PostgameState extends State {

  private PaintballPlayer winner;

  PostgameState(PaintballPlugin plugin, PlayerManager players, PaintballPlayer winner) {
    super(plugin, players);
    this.winner = winner;
  }

  @Override
  public void onEnter() {
    super.onEnter();
    Bukkit.broadcastMessage(
        ChatColor.GREEN + this.winner.getName() + org.bukkit.ChatColor.GRAY + " has won the game!");

    Player player = Bukkit.getPlayer(this.winner.getUniqueId());
    if (player != null) {
      player.teleport(plugin.getRespawnLocation());
    }
    this.players.purge();
  }

  @Override
  <R> R accept(StateVisitor visitor) {
    return visitor.visitPostGameState(this);
  }

  @Override
  public boolean isFinished() {
    return players.getAllPlayers() == 0;
  }

  @Override
  public State nextState() {
    return new PregameState(plugin, this.players);
  }
}
