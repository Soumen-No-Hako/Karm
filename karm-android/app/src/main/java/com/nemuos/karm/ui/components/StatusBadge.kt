package com.nemuos.karm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nemuos.karm.model.Status
import com.nemuos.karm.ui.theme.StatusCanceled
import com.nemuos.karm.ui.theme.StatusDone
import com.nemuos.karm.ui.theme.StatusInProgress
import com.nemuos.karm.ui.theme.StatusNew
import com.nemuos.karm.ui.theme.StatusTodo

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val statusEnum = Status.fromString(status)
    val backgroundColor = when (statusEnum) {
        Status.NEW -> StatusNew
        Status.TODO -> StatusTodo
        Status.IN_PROGRESS -> StatusInProgress
        Status.DONE -> StatusDone
        Status.CANCELED -> StatusCanceled
    }

    Text(
        text = status,
        modifier = modifier
            .background(
                color = backgroundColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = backgroundColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
}
