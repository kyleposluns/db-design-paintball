package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class AddKills extends AbstractSQLCommand {

  public AddKills(Connection conn, UUID id, int kills) {
    super(conn);
    this.command = "CALL addKills(" + id + ", " + kills + ")";
  }
}
