package me.ibrahim.bookpedia.kmp.book.presentation.book_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.book_cover
import bookpedia_kmp.composeapp.generated.resources.book_error_2
import bookpedia_kmp.composeapp.generated.resources.favorits_books
import bookpedia_kmp.composeapp.generated.resources.go_back
import bookpedia_kmp.composeapp.generated.resources.mark_as_favorite
import bookpedia_kmp.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailActions
import me.ibrahim.bookpedia.kmp.book.presentation.book_detail.BookDetailState
import me.ibrahim.bookpedia.kmp.theme.DarkBlue
import me.ibrahim.bookpedia.kmp.theme.DesertWhite
import me.ibrahim.bookpedia.kmp.theme.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BlurredImageBackground(
    state: BookDetailState,
    onAction: (BookDetailActions) -> Unit,
    content: @Composable () -> Unit
) {

    var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
    val painter = rememberAsyncImagePainter(
        model = state.selectedBook?.imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid Image Dimensions"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        })


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .background(DarkBlue)
            ) {
                imageLoadResult?.let { result ->
                    if (result.isSuccess) {
                        Image(
                            contentScale = ContentScale.Crop,
                            painter = painter,
                            contentDescription = stringResource(Res.string.book_cover),
                            modifier = Modifier.fillMaxSize()
                                .blur(15.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { onAction(BookDetailActions.OnBackClick) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)
                        .statusBarsPadding()
                        .background(brush = Brush.radialGradient(listOf(Color.White.copy(alpha = 50f), Color.Transparent)))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.go_back),
                        tint = Color.Black
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(DesertWhite)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            Box {
                ElevatedCard(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 16.dp
                    )
                ) {
                    imageLoadResult?.let { result ->
                        Image(
                            contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                            painter = if (result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                            contentDescription = stringResource(Res.string.book_cover),
                            modifier = Modifier
                                .height(270.dp)
                                .aspectRatio(ratio = 2 / 3f)
                        )
                    }
                }

                IconButton(
                    onClick = {
                        onAction(BookDetailActions.OnFavoriteClick)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(SandYellow, Color.Transparent),
                                radius = 70f
                            )
                        )
                ) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        tint = Color.Red,
                        contentDescription = if (state.isFavorite) {
                            stringResource(Res.string.remove_from_favorites)
                        } else {
                            stringResource(Res.string.mark_as_favorite)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            content()
        }
    }
}