package me.ibrahim.bookpedia.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ibrahim.bookpedia.kmp.book.domain.Book
import me.ibrahim.bookpedia.kmp.book.presentation.book_list.components.BookListItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
                )
            )
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

/*@Preview(
    backgroundColor = 0xffffffff,
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun BookSearchBarPreview() {
    var query by remember {
        mutableStateOf("")
    }
    BookSearchBar(
        query,
        onSearchQueryChange = { query = it },
        onImeSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}*/

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun BookListItemPrev() {
    val book = Book(
        id = "id",
        title = "Discrete Mathematics",
        ratingCount = 4,
        authers = listOf("abc", "def"),
        descriptions = "",
        imageUrl = "",
        languages = listOf("English", "Urdu"),
        firstPublishYear = "2005",
        averageRating = 3.5,
        numPages = 100,
        numEditions = 2
    )
    BookListItem(book = book, onClick = {})
}