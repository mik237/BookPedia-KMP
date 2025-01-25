package me.ibrahim.bookpedia.kmp.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.ibrahim.bookpedia.kmp.theme.SandYellow
import kotlin.math.round

@Composable
fun BookTitle(modifier: Modifier) {

    val book = LocalBookProvider.current

    Column(
        modifier = modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            book.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        book.authors.firstOrNull()?.let { autherName ->
            Text(
                autherName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        book.averageRating?.let { rating ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${round(rating * 10) / 10.0}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    Icons.Default.Star, contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = SandYellow
                )
            }
        }
    }
}