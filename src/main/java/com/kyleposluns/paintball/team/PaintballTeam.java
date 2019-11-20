package com.kyleposluns.paintball.team;

import java.util.AbstractMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 *
 */
public interface PaintballTeam {

  Color getColor();

  ChatColor getChatColor();

  Material getBlock();

  class Builder {

    private static final Map<Material, Color> TERRACOTTA_COLORS = Map.ofEntries(
        new AbstractMap.SimpleEntry<>(Material.TERRACOTTA, Color.fromRGB(144, 96, 73)),
        new AbstractMap.SimpleEntry<>(Material.WHITE_TERRACOTTA, Color.fromRGB(202, 171, 161)),
        new AbstractMap.SimpleEntry<>(Material.ORANGE_TERRACOTTA, Color.fromRGB(149, 84, 41)),
        new AbstractMap.SimpleEntry<>(Material.MAGENTA_TERRACOTTA, Color.fromRGB(139, 87, 105)),
        new AbstractMap.SimpleEntry<>(Material.LIGHT_BLUE_TERRACOTTA, Color.fromRGB(109, 106, 134)),
        new AbstractMap.SimpleEntry<>(Material.YELLOW_TERRACOTTA, Color.fromRGB(177, 132, 52)),
        new AbstractMap.SimpleEntry<>(Material.LIME_TERRACOTTA, Color.fromRGB(103, 115, 56)),
        new AbstractMap.SimpleEntry<>(Material.PINK_TERRACOTTA, Color.fromRGB(149, 79, 77)),
        new AbstractMap.SimpleEntry<>(Material.GRAY_TERRACOTTA, Color.fromRGB(49, 35, 27)),
        new AbstractMap.SimpleEntry<>(Material.LIGHT_GRAY_TERRACOTTA, Color.fromRGB(129, 104, 96)),
        new AbstractMap.SimpleEntry<>(Material.CYAN_TERRACOTTA, Color.fromRGB(84, 88, 88)),
        new AbstractMap.SimpleEntry<>(Material.PURPLE_TERRACOTTA, Color.fromRGB(109, 68, 82)),
        new AbstractMap.SimpleEntry<>(Material.BLUE_TERRACOTTA, Color.fromRGB(67, 56, 86)),
        new AbstractMap.SimpleEntry<>(Material.BROWN_TERRACOTTA, Color.fromRGB(69, 45, 29)),
        new AbstractMap.SimpleEntry<>(Material.GREEN_TERRACOTTA, Color.fromRGB(73, 78, 40)),
        new AbstractMap.SimpleEntry<>(Material.RED_TERRACOTTA, Color.fromRGB(131, 60, 45)),
        new AbstractMap.SimpleEntry<>(Material.BLACK_TERRACOTTA, Color.fromRGB(26, 12, 5)));

    private static final Map<String, Color> BY_NAME = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("WHITE", Color.WHITE),
        new AbstractMap.SimpleEntry<>("SILVER", Color.SILVER),
        new AbstractMap.SimpleEntry<>("GRAY", Color.GRAY),
        new AbstractMap.SimpleEntry<>("BLACK", Color.BLACK),
        new AbstractMap.SimpleEntry<>("RED", Color.RED),
        new AbstractMap.SimpleEntry<>("MAROON", Color.MAROON),
        new AbstractMap.SimpleEntry<>("YELLOW", Color.YELLOW),
        new AbstractMap.SimpleEntry<>("OLIVE", Color.OLIVE),
        new AbstractMap.SimpleEntry<>("LIME", Color.GREEN),
        new AbstractMap.SimpleEntry<>("AQUA", Color.AQUA),
        new AbstractMap.SimpleEntry<>("TEAL", Color.TEAL),
        new AbstractMap.SimpleEntry<>("BLUE", Color.BLUE),
        new AbstractMap.SimpleEntry<>("NAVY", Color.NAVY),
        new AbstractMap.SimpleEntry<>("FUCHSIA", Color.FUCHSIA),
        new AbstractMap.SimpleEntry<>("PURPLE", Color.PURPLE),
        new AbstractMap.SimpleEntry<>("ORANGE", Color.ORANGE));

    private Map<Material, Color> blockColors;

    private Color color;

    private String colorName;

    public Builder() {
      this.blockColors = TERRACOTTA_COLORS;
      this.colorName = null;
      this.color = null;
    }

    public Builder color(Color color) {
      this.color = color;
      return this;
    }

    public Builder byName(String color) {
      this.colorName = color;
      return this;
    }

    public Builder blockColors(Map<Material, Color> blockColors) {
      this.blockColors = blockColors;
      return this;
    }

    public PaintballTeam build() {

      if (this.blockColors == null) {
        throw new IllegalArgumentException("Cannot have a null block-color mapping!");
      }

      if (colorName != null) {

        String uppercaseName = this.colorName.toUpperCase();

        if (uppercaseName.equals("RANDOM")) {
          return new RandomPaintballTeam(this.blockColors);
        } else if (uppercaseName.equals("RAINBOW")) {
          return new RainbowPaintballTeam(this.blockColors);
        } else {
          Color color = BY_NAME.get(uppercaseName);
          if (color == null) {
            throw new IllegalArgumentException("Cannot find color by the name " + uppercaseName);
          }
          return new BasicPaintballTeam(color, this.blockColors);
        }

      } else if (this.color != null) {
        return new BasicPaintballTeam(this.color, this.blockColors);
      } else {
        return new RandomPaintballTeam(this.blockColors);
      }

    }


  }

}
