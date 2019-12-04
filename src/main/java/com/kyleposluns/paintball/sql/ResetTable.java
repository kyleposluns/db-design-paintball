package com.kyleposluns.paintball.sql;

import java.sql.Connection;

public class ResetTable extends AbstractSQLCommand {

  public ResetTable(Connection conn) {
    super(conn);
    this.command= "Call ResetTable()";

  }
}
