package com.kyleposluns.paintball.game;

import org.bukkit.event.Listener;

/**
 * Represents a state in the game. A state is either a PregameState, a GameLogicState, or a
 * PostgameState. There should be a cycle from state to state where nextState() represents the
 * pointer to the next state. PregameState points to GameLogicState points to PostgameState.
 */
public interface State extends Listener, Runnable {

  /**
   * Determines if the end conditions for this state have been met.
   *
   * @return True if this state is allowed to move onto the next state.
   */
  boolean isFinished();

  /**
   * Points to the next state that should be run.
   *
   * @return The next state.
   * @throws IllegalStateException if this.isFinished() is not true.
   */
  State nextState();

  /**
   * Visitor pattern structure for states. Allows functionality to be added to each implementation
   * of this state.
   *
   * @param visitor The function object performing some computation on this state.
   * @param <R>     The type of the result of the computation described by the provided visitor
   *                object.
   * @return The result of the computation described by the provided visitor object.
   */
  <R> R accept(StateVisitor visitor);

  /**
   * Called when this state has been effectively entered.
   */
  void onEnter();

  /**
   * Called when this state has been effectively exited.
   */
  void onExit();

  /**
   * Called when this state must be finished right away.
   */
  void abort();


}
