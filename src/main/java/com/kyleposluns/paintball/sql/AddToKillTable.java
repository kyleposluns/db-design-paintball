package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.util.UUID;

public class AddToKillTable extends AbstractSQLCommand {

  public AddToKillTable(Connection conn, int wave, UUID playID, String mon) {
    super(conn);
    this.command =
            "CALL AddKillTable( " + "'" + playID.toString() + "' " + ", " + "'" + mon + "' " + "," +
                    " " + wave +
            ");";
  }
}
