package com.kyleposluns.paintball.game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.bukkit.entity.EntityType;

public class DBPreferences implements GamePreferences {

  private final Wave initial;

  private final long time;

  private final double health;

  private final double paintballDamage;

  private final double monsterDamage;

  public DBPreferences(Connection connection, String difficulty) throws SQLException {
    Statement s = connection.createStatement();
    ResultSet prefs =
            s.executeQuery("SELECT * FROM Preferences WHERE difficulty = " + difficulty +";");
    int SQheaLth = 0;
    int pbd = 0;
    int md = 0;
    int wave = 0;
    int ratio = 0;
    double monSpeed = 0;
    int monHealth = 0;
    while (prefs.next()) {
      wave = prefs.getInt("Wave");
      SQheaLth = prefs.getInt("health");
      pbd = prefs.getInt("paintballDamage");
      md = prefs.getInt("monsterDamage");
      ratio = prefs.getInt("ratio");
      monSpeed = prefs.getDouble("monSpeed");
      monHealth = prefs.getInt("monHealth");
    }
    this.time = 40L;
    this.health = SQheaLth;
    this.paintballDamage = pbd;
    this.monsterDamage = md;

    ResultSet waveG = s.executeQuery("SELECT * FROM WaveGroups WHERE Wave = " + wave + ";");
    ArrayList<Integer> monsters = new ArrayList<>();
    while (waveG.next()) {
      int monsta = waveG.getInt("Monster");
      monsters.add(monsta);
    }
    ArrayList<EntityType> monName = new ArrayList<>();
    for (int i : monsters) {
      ResultSet mons = s.executeQuery("SELECT * FROM Monsters WHERE monsterID =" + i);
      String nombre;
      while (mons.next()) {
        nombre = mons.getString("name");
        monName.add(EntityType.valueOf(nombre));
      }
    }

    this.initial = new Wave.Builder(1)
            .entities(monName)
            .monsterHealth(monHealth)
            .monsterSpeed(monSpeed)
            .monstersPerRound(ratio)
            .build();
  }

  @Override
  public Wave getInitialWave() {
    return this.initial;
  }

  @Override
  public long getTimeToUndoBlockPaint() {
    return this.time;
  }

  @Override
  public double getInitialPlayerHealth() {
    return this.health;
  }

  @Override
  public double getPaintballDamage() {
    return this.paintballDamage;
  }

  @Override
  public double getMonsterDamage() {
    return this.monsterDamage;
  }
}
