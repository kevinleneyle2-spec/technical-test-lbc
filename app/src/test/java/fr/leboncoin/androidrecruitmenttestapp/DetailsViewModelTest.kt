package fr.leboncoin.androidrecruitmenttestapp

import com.google.common.truth.Truth.assertThat
import fr.leboncoin.androidrecruitmenttestapp.details.viewmodel.DetailsViewModel
import fr.leboncoin.data.local.`interface`.FavoriteAlbumDao
import fr.leboncoin.data.network.model.AlbumDto
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.repository.FavoriteAlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeFavoriteAlbumDao: FavoriteAlbumDao

    private lateinit var trueStatusFavoriteAlbumDao: FavoriteAlbumDao
    private lateinit var favoriteAlbumRepository: FavoriteAlbumRepository

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        fakeFavoriteAlbumDao = object : FavoriteAlbumDao {
            override fun getAllAlbums(): Flow<List<FavoriteAlbumDto>> =
                MutableStateFlow(
                    listOf(
                        FavoriteAlbumDto(
                            id = 5
                        )
                    )
                )
            override suspend fun isFavorite(id: Int): Boolean = false
            override suspend fun insertFavoriteId(id: FavoriteAlbumDto) {}
            override suspend fun deleteFavoriteId(id: Int) {}
        }

        trueStatusFavoriteAlbumDao = object : FavoriteAlbumDao {
            override fun getAllAlbums(): Flow<List<FavoriteAlbumDto>> =
                MutableStateFlow(
                    listOf(
                        FavoriteAlbumDto(
                            id = 5
                        )
                    )
                )
            override suspend fun isFavorite(id: Int): Boolean = true
            override suspend fun insertFavoriteId(id: FavoriteAlbumDto) {}
            override suspend fun deleteFavoriteId(id: Int) {}
        }

        favoriteAlbumRepository = FavoriteAlbumRepository(fakeFavoriteAlbumDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `add album from favorite list`() = runTest(testDispatcher) {
        viewModel = DetailsViewModel(favoriteAlbumRepository)

        val album = AlbumDto(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")

        viewModel.onFavoriteClick(album)

        advanceUntilIdle()
        assertThat(viewModel.isFavorite.value).isTrue()
    }

    @Test
    fun `delete album from favorite list`() = runTest(testDispatcher) {
        favoriteAlbumRepository = FavoriteAlbumRepository(fakeFavoriteAlbumDao)
        viewModel = DetailsViewModel(favoriteAlbumRepository)

        val album = AlbumDto(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")

        viewModel.onFavoriteClick(album)
        advanceUntilIdle()
        assertThat(viewModel.isFavorite.value).isTrue()

        viewModel.onFavoriteClick(album)
        advanceUntilIdle()
        assertThat(viewModel.isFavorite.value).isNotEqualTo(true)
    }

    @Test
    fun `check album favorite status is false`() = runTest(testDispatcher) {
        favoriteAlbumRepository = FavoriteAlbumRepository(fakeFavoriteAlbumDao)
        viewModel = DetailsViewModel(favoriteAlbumRepository)

        val album = AlbumDto(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")

        viewModel.checkFavoriteStatus(album)
        advanceUntilIdle()
        assertThat(viewModel.isFavorite.value).isEqualTo(false)
    }

    @Test
    fun `check album favorite status is true`() = runTest(testDispatcher) {
        favoriteAlbumRepository = FavoriteAlbumRepository(trueStatusFavoriteAlbumDao)
        viewModel = DetailsViewModel(favoriteAlbumRepository)

        val album = AlbumDto(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")

        viewModel.checkFavoriteStatus(album)
        advanceUntilIdle()
        assertThat(viewModel.isFavorite.value).isTrue()
    }
}

