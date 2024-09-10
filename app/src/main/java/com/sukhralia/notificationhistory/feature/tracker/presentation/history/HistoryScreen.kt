package com.sukhralia.notificationhistory.feature.tracker.presentation.history

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sukhralia.notificationhistory.R
import com.sukhralia.notificationhistory.util.formatDateTime
import com.sukhralia.notificationhistory.util.saveFileToDownloads
import java.util.Date

@Composable
fun HistoryScreen(title: String, packageName: String) {

    val viewModel: HistoryViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getNotificationByTitle(title, packageName)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LazyColumn {

            item {
                Text(
                    modifier = Modifier,
                    text = title,
                    minLines = 1,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 32.sp
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(uiState.notifications) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = it.message ?: "",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = formatDateTime(Date(it.createDate ?: 0)),
                        color = Color.Gray,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp
                        )
                    )

                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
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
                val notificationStrings: List<String> = uiState.notifications.map { notification ->
                    "${formatDateTime(Date(notification.createDate!!))} : ${notification.title}\n${notification.message}\n_____________________________\n"
                }
                val savedFile = saveFileToDownloads(
                    context,
                    "recovery_wiz_${title}_${packageName}_${Date()}",
                    notificationStrings
                )
                Toast.makeText(context, "Saved in Downloads", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .padding(bottom = 16.dp),
            containerColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_download),
                modifier = Modifier.size(32.dp),
                tint = Color.Black,
                contentDescription = "Save"
            )
        }
    }

}



