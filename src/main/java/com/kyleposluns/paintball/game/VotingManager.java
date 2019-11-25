package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.ArenaManager;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class VotingManager {

  private Map<UUID, UUID> playerVotes;

  private Map<UUID, Integer> votes;

  private boolean locked;

  VotingManager(ArenaManager arenaManager) {
    this.playerVotes = new ConcurrentHashMap<>();
    this.votes = arenaManager.getAvailableArenas().stream()
        .collect(Collectors.toConcurrentMap(arena -> arena, arena -> 0));
    this.locked = false;
  }

  void lockVotes() {
    this.locked = true;
  }

  void vote(UUID playerId, UUID arenaId) {
    if (!locked) {
      UUID oldVote = this.playerVotes.get(playerId);
      if (oldVote != null) {
        this.votes.put(oldVote, this.votes.get(oldVote) - 1);
      }

      this.playerVotes.put(playerId, arenaId);
      this.votes.put(arenaId, this.votes.get(arenaId) + 1);
    } else {
      throw new IllegalStateException("Cannot vote when the votes have been locked.");
    }
  }

  UUID getWinner() {
    return this.votes.entrySet().stream().max(
        Comparator.comparingInt(Entry::getValue))
        .orElseThrow().getKey();
  }


}
