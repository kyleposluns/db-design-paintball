package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractSQLCommand implements SQLCommand {

  Connection conn;
  String command;

  AbstractSQLCommand(Connection conn) {
    this.conn = conn;
  }

  public void run() throws SQLException {
    Statement statement = conn.createStatement();
    statement.execute(this.command);
  }

}
