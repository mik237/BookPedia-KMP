package me.ibrahim.bookpedia.kmp.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import me.ibrahim.bookpedia.kmp.book.presentation.SelectedBookViewModel
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailActions
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailScreenRoot
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailViewModel
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

                composable<Route.BookList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) { entry ->
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)
                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }
                    BookListScreenRoot(viewModel = viewModel, onBookClick = { book ->
                        selectedBookViewModel.onSelectBook(book)
                        navController.navigate(Route.BookDetail(book.id))
                    })
                }

                composable<Route.BookDetail>(
                    enterTransition = { slideInHorizontally { initialOffset -> initialOffset } },
                    exitTransition = { slideOutHorizontally { initialOffset -> initialOffset } }
                ) { entry ->

                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                    val viewModel = koinViewModel<BookDetailViewModel>()

                    LaunchedEffect(selectedBook) {
                        viewModel.onAction(BookDetailActions.OnSelectedBookChange(selectedBook))
                    }

                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
                    )
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
