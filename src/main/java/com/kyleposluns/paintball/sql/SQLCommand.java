package com.kyleposluns.paintball.sql;

import java.sql.SQLException;

/**
 * Represents a Runnable command on a database that, when called, creates an update.
 */
public interface SQLCommand {

  public void run() throws SQLException;

}
