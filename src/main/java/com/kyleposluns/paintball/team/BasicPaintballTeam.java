package com.kyleposluns.paintball.team;

import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

class BasicPaintballTeam extends AbstractPaintballTeam {

  private final ChatColor chatColor;

  private final Color color;

  private final Material matchingMaterial;

  BasicPaintballTeam(Color color, Map<Material, Color> blockColors) {
    super(blockColors);
    this.color = color;
    this.matchingMaterial = getClosest(blockColors, this.color);
    this.chatColor = getClosest(CHAT_COLOR_COLORS, this.color);
  }

  @Override
  public ChatColor getChatColor() {
    return this.chatColor;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public Material getBlock() {
    return this.matchingMaterial;
  }


}
