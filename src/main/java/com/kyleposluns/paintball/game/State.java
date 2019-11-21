package com.kyleposluns.paintball.game;

import org.bukkit.event.Listener;

public interface State extends Listener, Runnable {

  boolean isFinished();

  State nextState();

  <R> R accept(StateVisitor visitor);

  void onEnter();

  void onExit();
  

}
