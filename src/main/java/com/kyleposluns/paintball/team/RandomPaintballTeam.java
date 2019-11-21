package com.kyleposluns.paintball.team;

import java.util.Map;
import org.bukkit.Color;
import org.bukkit.Material;

class RandomPaintballTeam extends BasicPaintballTeam {

  RandomPaintballTeam(Map<Material, Color> blockColors) {
    super(Color.fromRGB((int) (Math.random() * 256), (int) (Math.random() * 256),
        (int) (Math.random() * 256)),
        blockColors);
  }


}
