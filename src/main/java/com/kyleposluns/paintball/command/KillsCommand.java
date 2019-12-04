package com.kyleposluns.paintball.command;

import com.kyleposluns.paintball.command.DifficultyCommand.DifficultyVisitor;
import com.kyleposluns.paintball.game.GameLogicState;
import com.kyleposluns.paintball.game.KillHandler;
import com.kyleposluns.paintball.game.PostgameState;
import com.kyleposluns.paintball.game.PregameState;
import com.kyleposluns.paintball.game.State;
import com.kyleposluns.paintball.game.StateVisitor;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillsCommand implements CommandExecutor {

  private State state;

  public KillsCommand(State state) {
    this.state = state;
  }


  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }
    if (args.length != 0) {
      return false;
    }

    Player player = (Player) sender;

    player.sendMessage(ChatColor.LIGHT_PURPLE + "The most amount of kills you have is: " + state
        .accept(new KillFinder(player.getUniqueId())));

    return true;
  }


  private class KillFinder implements StateVisitor<Integer> {

    private final UUID player;

    KillFinder(UUID player) {
      this.player = player;
    }

    @Override
    public Integer visitPregameState(PregameState state) {
      return -1;
    }

    @Override
    public Integer visitGameLogicState(GameLogicState state) {
      return state.getKillHandler().getKills(this.player);
    }

    @Override
    public Integer visitPostGameState(PostgameState state) {
      return -1;
    }
  }
}
