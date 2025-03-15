package ninja.bryansills.loudping.grader

sealed interface RuleGroup {
    data class All(val rules: List<GraderRule>, val allAbove: Float) : RuleGroup

    data class None(val rules: List<GraderRule>, val notBelow: Float) : RuleGroup

    data class Some(val rules: List<GraderRule>, val above: Float, val percentageAbove: Float) : RuleGroup
}

val DefaultRules = listOf(
    // simplest rule, if all the tracks were played in order and quickly, we are good
    RuleGroup.All(
        rules = listOf(
            GraderRule.Tracks,
            GraderRule.Sequence,
            GraderRule.Compactness(runtimeMultiplier = 1.5f, minDurationPlayed = 0.94f)
        ),
        allAbove = 0.94f
    ),
    // if either are above 75%, we are good
    RuleGroup.Some(
        rules = listOf(
            GraderRule.Tracks,
            GraderRule.Duration,
            GraderRule.Sequence,
            GraderRule.Compactness(runtimeMultiplier = 2.0f, minDurationPlayed = 0.64f)
        ),
        above = 0.74f,
        percentageAbove = 0.49f
    ),
    // if 3/4 are above 60%, we are good
    RuleGroup.Some(
        rules = listOf(
            GraderRule.Tracks,
            GraderRule.Duration,
            GraderRule.Sequence,
            GraderRule.Compactness(runtimeMultiplier = 3.0f, minDurationPlayed = 0.49f)
        ),
        above = 0.59f,
        percentageAbove = 0.74f
    )
)
