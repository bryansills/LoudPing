package ninja.bryansills.loudping.grader

sealed interface AlbumPlayGrade {
    data class Pass(val grade: Float)

    data class Fail(val grade: Float)
}
