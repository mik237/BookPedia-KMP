package me.ibrahim.bookpedia.kmp.book.presentation.book_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.favorits_books
import bookpedia_kmp.composeapp.generated.resources.search_result
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListActions
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListState
import me.ibrahim.bookpedia.kmp.theme.DesertWhite
import me.ibrahim.bookpedia.kmp.theme.SandYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookTabs(
    state: BookListState,
    onAction: (BookListActions) -> Unit
) {
    TabRow(
        modifier = Modifier
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        selectedTabIndex = state.selectedIndex,
        containerColor = DesertWhite,
        contentColor = SandYellow,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                color = SandYellow,
                modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedIndex])
            )
        }
    ) {
        Tab(
            selected = state.selectedIndex == 0,
            text = {
                Text(stringResource(Res.string.search_result))
            },
            icon = {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = stringResource(Res.string.search_result))
            },
            onClick = {
                onAction(BookListActions.OnTabSelected(0))
            },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
        )
        Tab(
            selected = state.selectedIndex == 1,
            text = {
                Text(stringResource(Res.string.favorits_books))
            },
            icon = {
                Icon(Icons.Default.Favorite, contentDescription = stringResource(Res.string.favorits_books))
            },
            onClick = {
                onAction(BookListActions.OnTabSelected(1))
            },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
        )
    }
}