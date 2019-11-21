package com.kyleposluns.paintball.sql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddPlayer extends AbstractSQLCommand {

  AddPlayer(Connection conn, Player p) {
    super(conn);
    this.command = "CALL addPlayer(" + p.getName() + ", " + p.getUniqueId() +  ")";
  }


}
