package com.sukhralia.notificationhistory.feature.tracker.presentation.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(packageName: String, onHistoryClick: (title: String, packageName: String) -> Unit) {

    val homeViewModel: HomeViewModel = viewModel()
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getNotificationsByPackage(packageName)
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

            uiState.notifications.isNotEmpty() -> {
                LazyColumn {

                    item {
                        Text(
                            modifier = Modifier,
                            text = "${packageName}/subjects",
                            minLines = 1,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 32.sp
                            ),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    uiState.groupedNotifications.forEach { (title, notificationList) ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(horizontal = 4.dp, vertical = 8.dp)
                                    .clickable { onHistoryClick(title ?: "", packageName) },
                                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(4.dp),
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 8.dp,
                                                vertical = 12.dp
                                            )
                                            .weight(5f)
                                    ) {
                                        Text(
                                            text = if (title.isNullOrEmpty()) "Unspecified" else title,
                                            color = Color.Black,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontSize = 18.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "${notificationList.size} messages",
                                            color = Color.Black,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontSize = 16.sp
                                            )
                                        )
                                    }
                                    Icon(
                                        Icons.Default.KeyboardArrowRight,
                                        modifier = Modifier
                                            .size(32.dp),
                                        tint = Color.Black,
                                        contentDescription = "Arrow"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}