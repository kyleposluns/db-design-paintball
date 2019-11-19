package com.kyleposluns.paintball.team;

import java.util.Map;
import org.bukkit.Color;
import org.bukkit.Material;

class RainbowPaintballTeam extends AbstractPaintballTeam {


  RainbowPaintballTeam(Map<Material, Color> blockColors) {
    super(blockColors);
  }

  @Override
  public Color getColor() {
    return Color.fromRGB((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
  }

}
