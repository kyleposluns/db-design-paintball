package com.kyleposluns.paintball.command;

import com.kyleposluns.paintball.game.GameLogicState;
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

public class VoteCommand implements CommandExecutor {

  private State state;

  public VoteCommand(State state) {
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

    SubmitVoteVisitor voteVisitor = new SubmitVoteVisitor(player.getUniqueId(), args[0].toLowerCase());

    if (this.state.accept(voteVisitor)) {
      player.sendMessage(ChatColor.GREEN + "Your vote has been recorded for " + args[0]);
    } else {
      player.sendMessage(ChatColor.RED + "The vote was not counted.");
    }


    return true;
  }

  class SubmitVoteVisitor implements StateVisitor<Boolean> {

    private final String vote;

    private final UUID playerId;

    SubmitVoteVisitor(UUID playerId, String vote) {
      this.playerId = playerId;
      this.vote = vote;
    }


    @Override
    public Boolean visitPregameState(PregameState state) {
      try {
        return state.vote(this.playerId, this.vote);
      } catch (IllegalStateException e) {
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
