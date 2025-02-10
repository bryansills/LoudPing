package ninja.bryansills.loudping.deephistory

import kotlinx.coroutines.flow.Flow

interface DeepHistoryRunner {
    operator fun invoke(dataProvider: DeepHistoryDataProvider): Flow<DeepHistoryRunEvent>
}
