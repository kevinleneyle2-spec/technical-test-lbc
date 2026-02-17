package fr.leboncoin.androidrecruitmenttestapp

import com.google.common.truth.Truth.assertThat
import app.cash.turbine.test
import fr.leboncoin.androidrecruitmenttestapp.home.viewmodel.HomeViewModel
import fr.leboncoin.data.local.`interface`.FavoriteAlbumDao
import fr.leboncoin.data.local.`interface`.OfflineAlbumDao
import fr.leboncoin.data.network.model.AlbumDto
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.local.model.OfflineAlbumDto
import fr.leboncoin.data.network.api.AlbumApiService
import fr.leboncoin.data.repository.AlbumRepository
import fr.leboncoin.data.repository.FavoriteAlbumRepository
import fr.leboncoin.data.repository.OfflineAlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeService: AlbumApiService

    private lateinit var fakeErrorService: AlbumApiService
    private lateinit var fakeOfflineAlbumDao: OfflineAlbumDao
    private lateinit var fakeFavoriteAlbumDao: FavoriteAlbumDao
    private lateinit var albumRepository: AlbumRepository
    private lateinit var albumErrorRepository: AlbumRepository
    private lateinit var offlineAlbumRepository: OfflineAlbumRepository
    private lateinit var favoriteAlbumRepository: FavoriteAlbumRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        fakeService = object : AlbumApiService {
            override suspend fun getAlbums(): List<AlbumDto> = listOf(
                AlbumDto(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")
            )
        }

        fakeErrorService = object : AlbumApiService {
            override suspend fun getAlbums(): List<AlbumDto> {
                throw IOException("Network error")
            }
        }

        fakeOfflineAlbumDao = object : OfflineAlbumDao {
            override fun getItemById(id: Int): Flow<OfflineAlbumDto> =
                MutableStateFlow(
                    OfflineAlbumDto(
                        id = 1,
                        albumId = 2,
                        title = "r",
                        url = "f",
                        thumbnailUrl = "rf"
                    )
                )

            override suspend fun deleteItem(item: OfflineAlbumDto) {}
            override suspend fun insertAll(albums: List<OfflineAlbumDto>) {}
            override suspend fun updateItem(item: OfflineAlbumDto) {}
            override suspend fun deleteAll() {}
            override fun getAllItems(): Flow<List<OfflineAlbumDto>> =
                MutableStateFlow(
                    listOf(
                        OfflineAlbumDto(
                            id = 1,
                            albumId = 2,
                            title = "r",
                            url = "f",
                            thumbnailUrl = "rf"
                        )
                    )
                )
        }
        fakeFavoriteAlbumDao = object : FavoriteAlbumDao {
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

        albumRepository = AlbumRepository(fakeService)
        albumErrorRepository = AlbumRepository(fakeErrorService)
        offlineAlbumRepository = OfflineAlbumRepository(fakeOfflineAlbumDao)
        favoriteAlbumRepository = FavoriteAlbumRepository(fakeFavoriteAlbumDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads albums and emits non-empty list`() = runTest(testDispatcher) {
        viewModel = HomeViewModel(albumRepository, offlineAlbumRepository, favoriteAlbumRepository)

        viewModel.loadAlbums()

        advanceUntilIdle()

        viewModel.albums.test {
            val loadedAlbums = awaitItem()
            assertThat(loadedAlbums).isNotEmpty()
            assertThat(loadedAlbums).hasSize(1)
            assertThat(loadedAlbums[0].id).isEqualTo(1)
            assertThat(loadedAlbums[0].albumId).isEqualTo(1)
            assertThat(loadedAlbums[0].title).isEqualTo("t")
            assertThat(loadedAlbums[0].url).isEqualTo("u")
            assertThat(loadedAlbums[0].thumbnailUrl).isEqualTo("tu")

            expectNoEvents()
        }
    }

    @Test
    fun `loads empty albums and emits offline albums list`() = runTest(testDispatcher) {
        viewModel = HomeViewModel(albumErrorRepository, offlineAlbumRepository, favoriteAlbumRepository)

        viewModel.loadAlbums()

        advanceUntilIdle()

        viewModel.albums.test {
            val loadedAlbums = awaitItem()
            assertThat(loadedAlbums).hasSize(1)
            assertThat(loadedAlbums[0].id).isEqualTo(1)
            assertThat(loadedAlbums[0].albumId).isEqualTo(2)
            assertThat(loadedAlbums[0].title).isEqualTo("r")
            assertThat(loadedAlbums[0].url).isEqualTo("f")
            assertThat(loadedAlbums[0].thumbnailUrl).isEqualTo("rf")

            expectNoEvents()
        }
    }

    @Test
    fun `find all favorite albums and emits favorite list`() = runTest(testDispatcher) {
        viewModel = HomeViewModel(albumRepository, offlineAlbumRepository, favoriteAlbumRepository)

        viewModel.findAllFavoriteAlbums()
        advanceUntilIdle()

        viewModel.favoriteAlbums.test {
            val favoriteAlbums = awaitItem()
            assertThat(favoriteAlbums).hasSize(1)
            assertThat(favoriteAlbums[0].id).isEqualTo(5)

            expectNoEvents()
        }
    }
}

