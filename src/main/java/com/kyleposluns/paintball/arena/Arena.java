package com.kyleposluns.paintball.arena;

import java.util.UUID;

public interface Arena {

  String getName();

  UUID getUniqueId();

  Region bounds();


}
