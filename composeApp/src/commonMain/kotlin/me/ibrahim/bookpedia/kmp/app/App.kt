package me.ibrahim.bookpedia.kmp.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import me.ibrahim.bookpedia.kmp.book.presentation.SelectedBookViewModel
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListScreenRoot
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.BookListViewModel
import me.ibrahim.bookpedia.kmp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.BookGraph
        ) {
            navigation<Route.BookGraph>(startDestination = Route.BookList) {

                composable<Route.BookList> { entry ->
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)
                    LaunchedEffect(true){
                        selectedBookViewModel.onSelectBook(null)
                    }
                    BookListScreenRoot(viewModel = viewModel, onBookClick = { book ->
                        selectedBookViewModel.onSelectBook(book)
                        navController.navigate(Route.BookDetail(book.id))
                    })
                }

                composable<Route.BookDetail> { entry ->
                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Color.Green.copy(alpha = 0.4f))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Book Detail:\n $selectedBook")
                    }
                }
            }
        }
    }
}


@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel<T>(viewModelStoreOwner = parentEntry)
}
