package com.sukhralia.notificationhistory.feature.tracker.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sukhralia.notificationhistory.R
import com.sukhralia.notificationhistory.ui.theme.AppThemeColor
import com.sukhralia.notificationhistory.util.isNotificationServiceEnabled
import com.sukhralia.notificationhistory.util.openNotificationSettings

@Composable
fun MenuScreen(onHomeClicked: (packageName: String) -> Unit = {}) {

    val viewModel: MenuViewModel = viewModel()

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var isPermissionClicked by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(isNotificationServiceEnabled(context)) }

    var searchQuery by remember {
        mutableStateOf("")
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
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(75.dp),
                    color = Color.Black,
                )
            }

            !isPermissionGranted -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Please grant permissions to ${stringResource(id = R.string.app_name)} from settings",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ErrorTextTag")
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "(Press the Play button)",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ErrorTextTag")
                    )
                }
            }

            uiState.packages.isNotEmpty() -> {
                LazyColumn {

                    item {
                        Text(
                            modifier = Modifier,
                            text = "App packages",
                            minLines = 1,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 32.sp
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

//                    item {
//                        RoundedTextField(
//                            label = "Search apps",
//                            trailingIcon = Icons.Filled.Search,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .border(.5.dp, Color.Gray, RoundedCornerShape(24.dp)),
//                            onValueChange = {
//                                searchQuery = it
//                                viewModel.getPackages(it)
//                            },
//                            text = searchQuery,
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//                    }

                    items(uiState.packages) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                                .clickable { onHomeClicked(it) },
                            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp),
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.ifEmpty { "Unspecified" },
                                    minLines = 1,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 18.sp
                                    ),
                                )
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .size(32.dp),
                                    tint = Color.Black,
                                    contentDescription = "Arrow"
                                )
                            }
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
                        text = "No messages yet",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ErrorTextTag")
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "(We will record messages here when received)",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
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
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                isPermissionClicked = true
                openNotificationSettings(context)
            },
            modifier = Modifier
                .padding(bottom = 16.dp),
            containerColor = Color.White
        ) {
            if (isPermissionGranted)
                Icon(
                    Icons.Default.Close,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black,
                    contentDescription = "Play"
                )
            else
                Icon(
                    Icons.Default.PlayArrow,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black,
                    contentDescription = "Stop"
                )
        }
    }
}
