package com.kyleposluns.paintball.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Initializer {

  private Connection conn;

  public Connection getConnection(File customConfigFile) {
    Connection conn = null;
    try {
      Scanner s = new Scanner(customConfigFile);
      Properties connectionProps = new Properties();
      connectionProps.load(new FileReader(customConfigFile));
      connectionProps.put("user", connectionProps.getProperty("username"));
      connectionProps.put("password", connectionProps.getProperty("password"));
      conn = DriverManager.getConnection("jdbc:mysql://"
                      + connectionProps.getProperty("serverName") + ":"
                      + connectionProps.getProperty("port") + "/"
                      + connectionProps.getProperty("dbName")
                      + "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
              connectionProps);
      this.conn = conn;
    } catch (SQLException | FileNotFoundException sql) {
      System.out.println("Incorrect username and/or password. Try again.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public void setupDatabase() throws SQLException {
    Statement use;
    use = conn.createStatement();
    try {
      use.execute("USE PaintballGame");
    } catch (SQLSyntaxErrorException e) {
      //The database needs to be set up on this device.
      this.initDatabase();
      use.execute("USE PaintballGame");
      this.initTables();
      this.initProcedures();
    }
  }

  private void initDatabase() throws SQLException {
    Statement beginning;
    beginning = conn.createStatement();
    beginning.execute("DROP DATABASE IF EXISTS PaintballGame");
    beginning.execute("CREATE DATABASE PaintballGame");
  }

  private void initTables() throws SQLException {
    Statement stmt;
    stmt = conn.createStatement();

  }

  private void initProcedures() {

  }

}
