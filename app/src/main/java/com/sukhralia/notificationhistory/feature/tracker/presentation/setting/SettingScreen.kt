package com.sukhralia.notificationhistory.feature.tracker.presentation.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sukhralia.notificationhistory.feature.tracker.presentation.component.SettingAppCard
import com.sukhralia.notificationhistory.util.isNotificationServiceEnabled
import com.sukhralia.notificationhistory.util.openNotificationSettings

@Composable
fun SettingScreen() {

    val viewModel: SettingViewModel = viewModel()

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var isPermissionClicked by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(isNotificationServiceEnabled(context)) }

    var checked = if (uiState.whiteListedApps == null) {
        true
    } else {
        uiState.installedApps.map { it.packageName }.toSet() == uiState.whiteListedApps!!.toSet()
    }
    LifecycleResumeEffect(key1 = Unit) {
        isPermissionGranted = isNotificationServiceEnabled(context)
        onPauseOrDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        when {
            uiState.installedApps.isNotEmpty() -> {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "Apps Installed",
                        minLines = 1,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 32.sp
                        ),
                    )
                    Switch(
                        checked = checked, onCheckedChange = {
                            checked = it
                            if (!checked) viewModel.removeAll() else viewModel.addAll()
                        }, modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

                        items(uiState.installedApps) { item ->
                            SettingAppCard(
                                { pkg, _ -> viewModel.updateWhiteListedApps(pkg) },
                                item.packageName,
                                item,
                                uiState.whiteListedApps?.contains(item.packageName) ?: true
                            )
                        }
                    }
                }
            }

            else -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No apps installed",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ErrorTextTag")
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                isPermissionClicked = true
                openNotificationSettings(context)
            }, modifier = Modifier.padding(bottom = 16.dp), containerColor = Color.White
        ) {
            if (isPermissionGranted) Icon(
                Icons.Default.Close,
                modifier = Modifier.size(32.dp),
                tint = Color.Black,
                contentDescription = "Play"
            )
            else Icon(
                Icons.Default.PlayArrow,
                modifier = Modifier.size(32.dp),
                tint = Color.Black,
                contentDescription = "Stop"
            )
        }
    }
}
