package com.kyleposluns.paintball.sql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class AddPlayer extends AbstractSQLCommand {

  public AddPlayer(Connection conn, UUID p) {
    super(conn);
    this.command = "CALL addPlayer(" + Bukkit.getPlayer(p).getName() + ", " + p +  ")";
  }


}
