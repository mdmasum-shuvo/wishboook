package com.example.messagebook

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.messagebook.ui.theme.MessageBookTheme
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

const val MY_REQUEST_CODE = 300

class MainActivity : ComponentActivity() {
    // Creates instance of the manager.
    private var appUpdateManager: AppUpdateManager? = null
    //APP ID: ca-app-pub-9642459238169402~7551121410
    //UNIT UD:ca-app-pub-9642459238169402/2822319167
    lateinit var navController: NavHostController
    private val listener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                Log.d(ContentValues.TAG, "An update has been downloaded")
                Toast.makeText(this, "অ্যাাপের নতুন আপডেট ডাউনলোড হয়েছে", Toast.LENGTH_SHORT).show()
                showSnackBarForCompleteUpdate()
            }
        }

    private fun showSnackBarForCompleteUpdate() {
        appUpdateManager!!.completeUpdate()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()
        setContent {
            MobileAds.initialize(this)
            MessageBookTheme {
                // A surface container using the 'background' color from the theme
                navController = rememberNavController()


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetUpNavGraph(navController = navController)

                }
            }
        }
    }

    private fun checkUpdate() {
        // Returns an intent object that you use to check for an update.
        if (BuildConfig.DEBUG){
            return
        }

        appUpdateManager?.registerListener(listener!!)
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        Log.d(ContentValues.TAG, "Checking for updates")
        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                // Request the update.
                Log.d(ContentValues.TAG, "Update available")
                appUpdateManager?.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE
                )
            } else {
                Log.d(ContentValues.TAG, "No Update available")
            }
        }


    }

    override fun onStop() {
        appUpdateManager?.unregisterListener(listener!!)
        super.onStop()
    }

}
