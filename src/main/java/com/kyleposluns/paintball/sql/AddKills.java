package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.sql.SQLException;
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

    numKills = numKills + 1;
  }

  @Override
  public void run() {
    System.out.println("Kills added");
    System.out.println(this.numKills);
    System.out.println(id);
    this.command = "CALL addKills(" +  this.numKills + ", " + "'" + id.toString() + "'"  + ")";
    try {
      super.run();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
