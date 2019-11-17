package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.Set;
import java.util.UUID;

public interface PaintballGame {

  Arena getActiveArena();

  Set<UUID> getAllPlayers();

  PaintballTeam getTeam(UUID playerId);


}
