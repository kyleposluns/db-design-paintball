package com.kyleposluns.paintball.team;

import java.util.Map;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.Material;

class RainbowPaintballTeam extends AbstractPaintballTeam {

  private final Random random;

  RainbowPaintballTeam(Random random, Map<Material, Color> blockColors) {
    super(blockColors);
    this.random = random;
  }

  @Override
  public Color getColor() {
    return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
  }

}
