package com.kyleposluns.paintball.game;

public interface StateVisitor {

  <R> R visitPregameState(PregameState state);

  <R> R visitGameLogicState(GameLogicState state);

  <R> R visitPostGameState(PostgameState state);

}
