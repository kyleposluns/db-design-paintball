package com.kyleposluns.paintball.team;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public interface PaintballTeam {

  Color getColor();

  ChatColor getChatColor();

  Material getBlock();

  class Builder {

    private static final Map<Material, Color> TERRACOTTA_COLORS = Map.ofEntries(
        new AbstractMap.SimpleEntry<>(Material.TERRACOTTA, Color.fromRGB(93, 140, 71)),
        new AbstractMap.SimpleEntry<>(Material.WHITE_TERRACOTTA, Color.fromRGB(204, 178, 162)),
        new AbstractMap.SimpleEntry<>(Material.ORANGE_TERRACOTTA, Color.fromRGB(148, 83, 41)),
        new AbstractMap.SimpleEntry<>(Material.MAGENTA_TERRACOTTA, Color.fromRGB(139, 87, 105)),
        new AbstractMap.SimpleEntry<>(Material.LIGHT_BLUE_TERRACOTTA, Color.fromRGB(110, 107, 134)),
        new AbstractMap.SimpleEntry<>(Material.YELLOW_TERRACOTTA, Color.fromRGB(175, 130, 51)),
        new AbstractMap.SimpleEntry<>(Material.LIME_TERRACOTTA, Color.fromRGB(102, 115, 55)),
        new AbstractMap.SimpleEntry<>(Material.PINK_TERRACOTTA, Color.fromRGB(150, 80, 78)),
        new AbstractMap.SimpleEntry<>(Material.GRAY_TERRACOTTA, Color.fromRGB(48, 33, 26)),
        new AbstractMap.SimpleEntry<>(Material.LIGHT_GRAY_TERRACOTTA, Color.fromRGB(130, 106, 96)),
        new AbstractMap.SimpleEntry<>(Material.CYAN_TERRACOTTA, Color.fromRGB(88, 88, 88)),
        new AbstractMap.SimpleEntry<>(Material.PURPLE_TERRACOTTA, Color.fromRGB(111, 70, 84)),
        new AbstractMap.SimpleEntry<>(Material.BLUE_TERRACOTTA, Color.fromRGB(68, 56, 86)),
        new AbstractMap.SimpleEntry<>(Material.BROWN_TERRACOTTA, Color.fromRGB(69, 46, 29)),
        new AbstractMap.SimpleEntry<>(Material.GREEN_TERRACOTTA, Color.fromRGB(73, 78, 41)),
        new AbstractMap.SimpleEntry<>(Material.RED_TERRACOTTA, Color.fromRGB(132, 62, 46)),
        new AbstractMap.SimpleEntry<>(Material.BLACK_TERRACOTTA, Color.fromRGB(25, 11, 5)));

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

    private final Random random;

    private Map<Material, Color> blockColors;

    private Color color;

    private String colorName;

    public Builder(Random random) {
      this.blockColors = TERRACOTTA_COLORS;
      this.random = random;
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
          return new RandomPaintballTeam(this.random, this.blockColors);
        } else if (uppercaseName.equals("RAINBOW")) {
          return new RainbowPaintballTeam(this.random, this.blockColors);
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
        throw new IllegalArgumentException("Not enough information to create a paintball team!");
      }

    }


  }

}
