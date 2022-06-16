package com.fuzy.csgomatches.ui.details

import androidx.navigation.findNavController
import com.fuzy.csgomatches.*
import com.fuzy.csgomatches.external.FakeApiService
import com.fuzy.csgomatches.external.RemoteDataSourceImpl
import com.fuzy.csgomatches.ui.MainActivity
import com.fuzy.csgomatches.core.BaseTest
import com.fuzy.csgomatches.core.EspressoActions
import com.fuzy.csgomatches.core.TestUtil
import com.fuzy.csgomatches.core.launchMyMainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MatchDetailsFragmentTest: BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fakeCheckerApiService: FakeApiService // get a fake by hilt

    @BindValue
    lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    override fun setup() {
        super.setup()
        hiltRule.inject()
        remoteDataSource =
            RemoteDataSourceImpl(fakeCheckerApiService, Dispatchers.Default)
    }

    @After
    override fun teardown() {
        super.teardown()
        fakeCheckerApiService.clearAllFlags()
    }

    @Test
    fun load_details_successfully() {
        fakeCheckerApiService.hasPlayers = true
        launchActivity()
        EspressoActions.performClickOnViewInsideRecyclerView(
            R.id.recyclerViewHome,
            0,
            R.id.textViewItemHomeLeagueSerieName
        )
        EspressoActions.checkSizeOnRecyclerView(
            R.id.recyclerViewMatchDetails,
            TestUtil.PLAYERS_SIZE
        )
    }

    override fun launchActivity() {
        activityScenario = launchMyMainActivity<MainActivity>().onActivity {
            it.findNavController(R.id.main_nav_host_fragment).run {
                navController = this
            }
        }
    }

}