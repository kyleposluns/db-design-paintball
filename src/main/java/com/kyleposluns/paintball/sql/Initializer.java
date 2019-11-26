package com.kyleposluns.paintball.sql;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Properties;

public class Initializer {

  private Connection conn;

  public Initializer(FileConfiguration configuration) {
    this.conn = getConnection(configuration);
  }


  public Connection getConnection() {
    return this.conn;
  }

  private Connection getConnection(FileConfiguration customConfigFile) {
    Connection conn = null;
    customConfigFile.getString("username");
    Properties connectionProps = new Properties();
    connectionProps.put("user", customConfigFile.getString("username"));
    connectionProps.put("password", customConfigFile.getString("password"));
    try {
      conn = DriverManager.getConnection("jdbc:mysql://"
              + customConfigFile.getString("serverName") + ":"
              + customConfigFile.getString("port") + "/"
              + customConfigFile.getString("dbName")
              + "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
          connectionProps);
      System.out.println(conn == null);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public void setupDatabase() throws SQLException {
    Statement use;
    use = this.conn.createStatement();
    try {
      use.execute("USE PaintballGame");
      this.initTables();
      this.initProcedures();
      this.insertArenaData();
      System.out.println("----------nhrejberjkfehajkfbeafk");
    } catch (SQLSyntaxErrorException e) {
      e.printStackTrace();
      //The database needs to be set up on this device.
      /*
      this.initDatabase();
      use.execute("USE PaintballGame");
      this.initTables();
      this.initProcedures();
      this.insertArenaData();*/
    }
  }

  private void initDatabase() throws SQLException {
    /*
    Statement beginning;
    beginning = conn.createStatement();
    beginning.execute("DROP DATABASE IF EXISTS PaintballGame");
    beginning.execute("CREATE DATABASE PaintballGame");*/
  }

  private void initTables() throws SQLException {
    Statement stmt;
    stmt = conn.createStatement();
    stmt.execute("CREATE TABLE IF NOT EXISTS Player (\n" +
        "\tplayerID CHAR(36) NOT NULL,\n" +
        "    name VARCHAR(45) NOT NULL,\n" +
        "    killsOverall INT NOT NULL,\n" +
        "    bestWave INT,\n" +
        "    PRIMARY KEY (playerID));");
    Statement stmt2 = conn.createStatement();
    stmt2.execute("CREATE TABLE IF NOT EXISTS Location (\n" +
        "\tLocID INT NOT NULL,\n" +
        "\tx DOUBLE NOT NULL,\n" +
        "    y DOUBLE NOT NULL,\n" +
        "    z DOUBLE NOT NULL,\n" +
        "    World CHAR(36) NOT NULL,\n" +
        "    PRIMARY KEY (LocID));");
    stmt.execute("CREATE TABLE IF NOT EXISTS Region (\n" +
        "\tReg VARCHAR(45) NOT NULL,\n" +
        "\tLocation1 INT NOT NULL,\n" +
        "    Location2 INT NOT NULL,\n" +
        "    PRIMARY KEY (Reg),\n" +
        "    CONSTRAINT fk_loc1 \n" +
        "    Foreign Key (Location1)\n" +
        "    REFERENCES Location (LocID)\n" +
        "    ON DELETE CASCADE\n" +
        "    ON UPDATE CASCADE,\n" +
        "    CONSTRAINT fk_loc2 \n" +
        "    Foreign Key (Location2)\n" +
        "    REFERENCES Location (LocID)\n" +
        "    ON DELETE CASCADE\n" +
        "    ON UPDATE CASCADE);");
    stmt.execute("CREATE TABLE IF NOT EXISTS Arena (\n" +
        "\tname VARCHAR(45) NOT NULL,\n" +
        "    UUID CHAR(36) NOT NULL PRIMARY KEY,\n" +
        "    Region VARCHAR(45) NOT NULL,\n" +
        "    constraint fk_region_of_arena\n" +
        "    FOREIGN KEY (Region)\n" +
        "    REFERENCES Region (Reg)\n" +
        "    ON DELETE CASCADE\n" +
        "    ON UPDATE CASCADE);");
  }

  private void initProcedures() throws SQLException {
    Statement stmt;
    stmt = conn.createStatement();
    stmt.execute(
        "DROP PROCEDURE IF EXISTS addKills; ");
    stmt.execute(
            "CREATE PROCEDURE addKills (IN newBlood INT, playID CHAR(36)) "
                + "BEGIN "
                + "UPDATE Player "
                + "SET killsOverall = killsOverall + newBlood "
                + "WHERE playerID = playID; "
                + "END");
    stmt.execute("DROP PROCEDURE IF EXISTS addPlayer; ");
    stmt.execute(
        "CREATE PROCEDURE addPlayer (IN name VARCHAR(45), playID CHAR(36)) " +
        "BEGIN " +
        "   IF NOT EXISTS (SELECT * FROM Player  " +
        "                   WHERE PlayerID = PlayID) " +
        "                   THEN " +
        "       INSERT INTO Player (playerID, name, killsOverall, bestWave) " +
        "       VALUES (playID, name, 0, 0); " +
        "   END IF; " +
        "END");
    stmt.execute("DROP PROCEDURE IF EXISTS WaveCheck; ");
    stmt.execute(
        "CREATE PROCEDURE WaveCheck (IN wave INT, playID CHAR(36)) " +
        "BEGIN " +
        "\tDECLARE oldWave INT; " +
        "    SET oldWave = (Select bestWave FROM Player WHERE playerID = playID); " +
        "\tIF (oldWave < wave) THEN " +
        "    UPDATE Player " +
        "    SET bestWave = wave " +
        "    WHERE playerID = playID; " +
        "    END IF; " +
        "END");
  }

  private void insertArenaData() {
    Statement stmt;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate("INSERT IGNORE INTO Location VALUES (1, -891, 103, -268, " +
          "'20eb6653-6d66-44f2-bc3e-3421e3a4c04b');");
      stmt.executeUpdate("INSERT IGNORE INTO Location VALUES (2, -831, 118, -226, " +
              "'20eb6653-6d66-44f2-bc3e-3421e3a4c04b');");
      stmt.executeUpdate(
          "INSERT IGNORE INTO Region VALUES ('CubeTrees', 1, 2);");
      stmt.executeUpdate(
          "INSERT IGNORE INTO Arena VALUES ('OferTheyreForest', '456a9918-7634-406f-b6de-83f19ba08fe3', " +
          "'CubeTrees');");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
