package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.UUID;

public interface PaintballPlayer {

  UUID uniqueId();

  String name();

  PaintballTeam team();


}
