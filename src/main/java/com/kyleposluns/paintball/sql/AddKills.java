package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class AddKills extends AbstractSQLCommand {

  private int numKills;
  private final UUID id;

  public AddKills(Connection conn, UUID id) {
    super(conn);
    this.command = "CALL addKills(" + id + ", " + 0 + ")";
    this.id = id;
    this.numKills = 0;
  }

  public void increment() {
    numKills++;
  }

  @Override
  public void run() {
    this.command = "CALL addKills(" + id + ", " + this.numKills + ")";
  }

}
