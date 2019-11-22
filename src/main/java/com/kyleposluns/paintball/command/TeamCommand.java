package com.kyleposluns.paintball.command;

import com.kyleposluns.paintball.game.GameLogicState;
import com.kyleposluns.paintball.game.PostgameState;
import com.kyleposluns.paintball.game.PregameState;
import com.kyleposluns.paintball.game.State;
import com.kyleposluns.paintball.game.StateVisitor;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {


  private State state;

  public TeamCommand(State state) {
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

    PaintballTeam.Builder builder = new PaintballTeam.Builder();
    if (args[0].startsWith("#")) {
      try {
        int hex = Integer.parseInt(args[0].substring(1), 16);
        Color color = Color.fromRGB(hex);
        builder.color(color);
      } catch (NumberFormatException nfe) {
        return false;
      }
    } else {
      String name = args[0];
      builder.byName(name);
    }

    try {

      PaintballTeam team = this.state.accept(
          new SetTeamVisitor(player.getUniqueId(), builder.build()));
      player.sendMessage(team.getChatColor() + "Welcome to the show!");
    } catch (IllegalArgumentException iae) {
      player.sendMessage(ChatColor.RED + "could not find a matching team!");
      return false;
    } catch (IllegalStateException iae) {
      player.sendMessage(ChatColor.RED + "Cannot set team during this state!");
      return false;
    }

    return true;
  }

  static class SetTeamVisitor implements StateVisitor<PaintballTeam> {

    private UUID playerId;

    private PaintballTeam team;

    public SetTeamVisitor(UUID playerId, PaintballTeam team) {
      this.playerId = playerId;
      this.team = team;
    }

    @Override
    public PaintballTeam visitPregameState(PregameState state) {
      state.addToTeam(this.playerId, this.team);
      return this.team;
    }

    @Override
    public PaintballTeam visitGameLogicState(GameLogicState state) {
      throw new IllegalStateException("Cannot set team during this state");
    }

    @Override
    public PaintballTeam visitPostGameState(PostgameState state) {
      throw new IllegalStateException("Cannot set team during this state");
    }
  }

}
