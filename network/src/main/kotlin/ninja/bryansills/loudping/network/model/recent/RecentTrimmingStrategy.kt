package ninja.bryansills.loudping.network.model.recent

enum class RecentTrimmingStrategy {
  /**
   * Do not trim off any data returned back from the network. Useful for trying to find gaps in
   * recorded history.
   */
  None,

  /** Trim off playback records older than what we care about. */
  StopAt,
}
