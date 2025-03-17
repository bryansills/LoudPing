package ninja.bryansills.loudping.grader

import ninja.bryansills.loudping.grouper.AlbumGroup
import ninja.bryansills.loudping.repository.album.AlbumRepository

class DefaultAlbumPlayGrader(
    private val albumRepo: AlbumRepository,
    private val rules: List<RuleGroup> = DefaultRules,
) : AlbumPlayGrader {
    override suspend fun grade(group: AlbumGroup): AlbumPlayGrade {
        TODO("Not yet implemented")
    }
}
