package com.kyleposluns.paintball.sql;

import java.sql.Connection;
import java.util.UUID;

public class UpdateBestWave extends AbstractSQLCommand {


  public UpdateBestWave(Connection conn, int wave, UUID player) {
    super(conn);
    this.command = "CALL WaveCheck(" + Integer.toString(wave) + ", " + player.toString() + ")";
  }
}
