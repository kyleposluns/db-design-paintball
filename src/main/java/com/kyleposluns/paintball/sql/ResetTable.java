package com.kyleposluns.paintball.sql;

import java.sql.Connection;

public class ResetTable extends AbstractSQLCommand {

  ResetTable(Connection conn) {
    super(conn);
    this.command= "Call ResetTable()";

  }
}
