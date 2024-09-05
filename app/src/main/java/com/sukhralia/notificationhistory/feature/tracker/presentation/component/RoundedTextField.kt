package com.sukhralia.notificationhistory.feature.tracker.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp)
        .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp)),
    onValueChange: (value: String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxChar: Int = 50,
    text: String = "",
    label: String = "",
    trailingIcon: ImageVector? = null
) {
    var textValue by remember { mutableStateOf(text) }

    TextField(
        maxLines = 1,
        value = textValue,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = {
            if (it.length <= maxChar) {
                textValue = it
                onValueChange.invoke(it)
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingIcon = {
            if (trailingIcon != null) Icon(
                trailingIcon,
                "trailing icon",
                modifier = Modifier.size(32.dp)
            )
        })
}
