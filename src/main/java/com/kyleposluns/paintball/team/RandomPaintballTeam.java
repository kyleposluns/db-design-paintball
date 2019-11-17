package com.kyleposluns.paintball.team;

import java.util.Map;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.Material;

class RandomPaintballTeam extends BasicPaintballTeam {

  RandomPaintballTeam(Random random, Map<Material, Color> blockColors) {
    super(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)),
        blockColors);
  }


}
