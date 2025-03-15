package ninja.bryansills.loudping.grader

/**
 * All of these are different rules that can be applied for grading whether or not an album was
 * played. When used, each of these rules will return a result between 0.0 and 1.0 indicating how
 * the particular group faired. A 1.0 result indicates that the album was played (according to the
 * rule) and a 0.0 indicates that the album was not played.
 */
sealed interface GraderRule {
    /**
     * Grades based on the number of tracks played on the album, as a percentage.
     *
     * For example, if an album is 10 tracks long and the play record indicates that 7 tracks were
     * played, then this would return 0.7.
     */
    data object Tracks : GraderRule

    /**
     * Grades based on the runtime duration of the play through, as a percentage.
     *
     * For example, if an album is 40 minutes long and the play record indicates that 30 minutes
     * was played, then this would return 0.75.
     */
    data object Duration : GraderRule

    /**
     * Grades based on the playback being in one after the other, regardless of the starting point.
     * This is a percentage of the tracks that were played in order.
     *
     * For example, if an album is 10 tracks long and the play record indicates that tracks 2-6 were
     * played in order, then this would return 0.5.
     */
    data object Sequence : GraderRule

    /**
     * Grades based on how quickly an album was played through compared to the expected runtime.
     *
     * For example, if an album is 50 minutes long, and it took 50 minutes to listen to, then this
     * would return 1.0.
     *
     * @param runtimeMultiplier The value (which is multiplied with the play through's expected
     * runtime) in which this rule would return 0.0. For example, if the play through was expected
     * to be 30 minutes, but actually took 45, and the `runtimeMultiplier` was 2.0 (aka 2 times the
     * expected duration), then this rule would return 0.5.
     * @param minDurationPlayed The floor for whether or not we even consider giving this play
     * through a grade using this rule. This is important because compactness is based on the
     * expected runtime of the actual tracks played, not the expected runtime for the entire album.
     * For example, if 50% of the album was played through (using duration as the measurement), but
     * `minDurationPlayed` was 0.6, then this rule would return 0.0.
     */
    data class Compactness(val runtimeMultiplier: Float, val minDurationPlayed: Float) : GraderRule
}
