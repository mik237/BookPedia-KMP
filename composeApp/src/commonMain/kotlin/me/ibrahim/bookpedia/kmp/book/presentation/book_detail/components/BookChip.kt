package me.ibrahim.bookpedia.kmp.book.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.ChipSize
import me.ibrahim.bookpedia.kmp.theme.LightBlue

@Composable
fun BookChip(
    size: ChipSize = ChipSize.REGULAR,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .widthIn(
                min = when (size) {
                    ChipSize.SMALL -> 50.dp
                    ChipSize.REGULAR -> 70.dp
                    ChipSize.LARGE -> 90.dp
                }
            )
            .padding(8.dp)
            .background(
                LightBlue,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier.padding(horizontal = 12.dp, vertical = 8.dp)) { content() }
    }
}