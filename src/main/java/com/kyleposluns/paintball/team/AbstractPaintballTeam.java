package com.kyleposluns.paintball.team;

import java.util.AbstractMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

abstract class AbstractPaintballTeam implements PaintballTeam {

  static final Map<ChatColor, Color> CHAT_COLOR_COLORS = Map.ofEntries(
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_RED, Color.fromRGB(0xAA0000)),
      new AbstractMap.SimpleEntry<>(ChatColor.RED, Color.fromRGB(0xFF5555)),
      new AbstractMap.SimpleEntry<>(ChatColor.GOLD, Color.fromRGB(0xFFAA00)),
      new AbstractMap.SimpleEntry<>(ChatColor.YELLOW, Color.fromRGB(0xFFFF55)),
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_GREEN, Color.fromRGB(0x00AA00)),
      new AbstractMap.SimpleEntry<>(ChatColor.GREEN, Color.fromRGB(0x55FF55)),
      new AbstractMap.SimpleEntry<>(ChatColor.AQUA, Color.fromRGB(0x55FFFF)),
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_AQUA, Color.fromRGB(0x00AAAA)),
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_BLUE, Color.fromRGB(0x0000AA)),
      new AbstractMap.SimpleEntry<>(ChatColor.BLUE, Color.fromRGB(0x0000AA)),
      new AbstractMap.SimpleEntry<>(ChatColor.LIGHT_PURPLE, Color.fromRGB(0xFF55FF)),
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_PURPLE, Color.fromRGB(0xAA00AA)),
      new AbstractMap.SimpleEntry<>(ChatColor.WHITE, Color.fromRGB(0xFFFFFF)),
      new AbstractMap.SimpleEntry<>(ChatColor.GRAY, Color.fromRGB(0xAAAAAA)),
      new AbstractMap.SimpleEntry<>(ChatColor.DARK_GRAY, Color.fromRGB(0x555555)),
      new AbstractMap.SimpleEntry<>(ChatColor.BLACK, Color.fromRGB(0x000000)));

  private Map<Material, Color> blockColors;

  AbstractPaintballTeam(Map<Material, Color> blockColors) {
    this.blockColors = blockColors;
  }

  @Override
  public Material getBlock() {
    return getClosest(this.blockColors, this.getColor());
  }

  @Override
  public ChatColor getChatColor() {
    return getClosest(CHAT_COLOR_COLORS, this.getColor());
  }


  <T> T getClosest(Map<T, Color> colorMap, Color color) {
    return colorMap.entrySet().stream()
        .min((o1, o2) -> {
          Color first = o1.getValue();
          Color second = o2.getValue();

          double firstDistance = Math.sqrt(Math.pow(color.getRed() - first.getRed(), 2)
              + Math.pow(color.getGreen() - first.getGreen(), 2)
              + Math.pow(color.getBlue() - first.getBlue(), 2));

          double secondDistance = Math.sqrt(Math.pow(color.getRed() - second.getRed(), 2)
              + Math.pow(color.getGreen() - second.getGreen(), 2)
              + Math.pow(color.getBlue() - second.getBlue(), 2));

          return Double.compare(firstDistance, secondDistance);
        }).orElseThrow().getKey();
  }

}
