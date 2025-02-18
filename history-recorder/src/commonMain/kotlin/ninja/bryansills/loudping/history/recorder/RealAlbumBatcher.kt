package ninja.bryansills.loudping.history.recorder

import kotlinx.datetime.Instant

class RealAlbumBatcher : AlbumBatcher {
  override suspend fun invoke(startAt: Instant, stopAt: Instant?): Result<AlbumBatcherResult> {
    // get all the tracks played
    // filter out everything that wasn't an album play
    // group into chunks of albums
    // check if i played enough of the album to qualify
    // record the played albums
    // return the result

    TODO()
  }
}
