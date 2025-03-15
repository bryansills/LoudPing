package ninja.bryansills.loudping.grader

import ninja.bryansills.loudping.grouper.AlbumGroup

interface AlbumPlayGrader {
    suspend fun grade(group: AlbumGroup): AlbumPlayGrade
}
