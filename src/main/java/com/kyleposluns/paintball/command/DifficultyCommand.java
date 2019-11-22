package com.kyleposluns.paintball.command;

import com.kyleposluns.paintball.game.DefaultGamePreferences;
import com.kyleposluns.paintball.game.GameLogicState;
import com.kyleposluns.paintball.game.GamePreferences;
import com.kyleposluns.paintball.game.PostgameState;
import com.kyleposluns.paintball.game.PregameState;
import com.kyleposluns.paintball.game.State;
import com.kyleposluns.paintball.game.StateVisitor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DifficultyCommand implements CommandExecutor {

  private State state;

  public DifficultyCommand(State state) {
    this.state = state;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label,
      String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }
    if (args.length != 1) {
      return false;
    }

    Player player = (Player) sender;

    if (state.accept(new DifficultyVisitor(args[0]))) {
      player.sendMessage(ChatColor.GREEN + "Difficulty has been changed to " + args[0]);
    } else {
      player.sendMessage(ChatColor.RED + "Difficulty not changed.");
    }


    return true;
  }

  class DifficultyVisitor implements StateVisitor<Boolean> {

    private String difficulty;

    DifficultyVisitor(String difficulty) {
      this.difficulty = difficulty;
    }


    @Override
    public Boolean visitPregameState(PregameState state) {
      try {
        DefaultGamePreferences preferences = DefaultGamePreferences
            .valueOf(this.difficulty.toUpperCase());
        state.difficulty(preferences);
        return true;
      } catch (IllegalArgumentException e) {
        return false;
      }
    }

    @Override
    public Boolean visitGameLogicState(GameLogicState state) {
      return false;
    }

    @Override
    public Boolean visitPostGameState(PostgameState state) {
      return false;
    }
  }

}
