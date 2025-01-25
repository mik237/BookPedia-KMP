package me.ibrahim.bookpedia.kmp.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
                composable<Route.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    BookListScreenRoot(viewModel = viewModel, onBookClick = {
                        navController.navigate(Route.BookDetail(it.id))
                    })
                }

                composable<Route.BookDetail> { entry ->
                    val args = entry.toRoute<Route.BookDetail>()
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Color.Green.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Book Detail Screen: ID-${args.id}")
                    }
                }
            }
        }


    }
}