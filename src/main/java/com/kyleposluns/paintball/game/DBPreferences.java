package com.kyleposluns.paintball.game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBPreferences implements GamePreferences {

  private final Wave initial;

  private final long time;

  private final double health;

  private final double paintballDamage;

  private final double monsterDamage;

  public DBPreferences(Connection connection, String difficulty) throws SQLException {
      Statement s = connection.createStatement();
      ResultSet resSet = s.executeQuery("SELECT * FROM Preferences WHERE ");
  }

  @Override
  public Wave getInitialWave() {
    return null;
  }

  @Override
  public long getTimeToUndoBlockPaint() {
    return 0;
  }

  @Override
  public double getInitialPlayerHealth() {
    return 0;
  }

  @Override
  public double getPaintballDamage() {
    return 0;
  }

  @Override
  public double getMonsterDamage() {
    return 0;
  }
}
