package com.fuzy.csgomatches.ui.home

import androidx.navigation.findNavController
import androidx.test.espresso.matcher.ViewMatchers
import com.fuzy.csgomatches.core.BaseTest
import com.fuzy.csgomatches.core.EspressoActions
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.external.FakeApiService
import com.fuzy.csgomatches.external.RemoteDataSourceImpl
import com.fuzy.csgomatches.core.launchMyMainActivity
import com.fuzy.csgomatches.ui.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomeFragmentTest : BaseTest() {

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

    // could do this type of test for every error on the app
    @Test
    fun load_matches_throw_unknown_error_exception() {
        fakeCheckerApiService.throwUnknownError = true
        launchActivity()
        EspressoActions.performClickOnViewInsideRecyclerView(
            R.id.recyclerViewHome,
            0,
            R.id.textViewItemHomeLeagueSerieName
        )
        EspressoActions.checkTextOnAlertDialog(R.string.an_error_occurred_try_again)
    }

    @Test
    fun perform_click_navigate_to_details() {
        // Arrange
        fakeCheckerApiService.hasMatches = true

        // Act
        launchActivity()
        EspressoActions.performClickOnViewInsideRecyclerView(
            R.id.recyclerViewHome,
            0,
            R.id.textViewItemHomeLeagueSerieName
        )

        // Assert
        ViewMatchers.assertThat(
            navController.currentDestination?.id,
            Matchers.`is`(R.id.matchDetailsFragment)
        )
    }

    override fun launchActivity() {
        activityScenario = launchMyMainActivity<MainActivity>().onActivity {
            navController = it.findNavController(R.id.main_nav_host_fragment)
        }
    }
}