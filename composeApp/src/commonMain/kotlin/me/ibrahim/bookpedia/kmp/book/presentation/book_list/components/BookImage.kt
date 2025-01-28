package me.ibrahim.bookpedia.kmp.book.presentation.book_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia_kmp.composeapp.generated.resources.Res
import bookpedia_kmp.composeapp.generated.resources.book_error_2
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import me.ibrahim.bookpedia.kmp.core.presentation.PulseAnimation
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookImage() {
    val book = LocalBookProvider.current
    Box(
        modifier = Modifier
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {

        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }

        val painter = rememberAsyncImagePainter(
            model = book.imageUrl,
            onSuccess = {
                imageLoadResult = if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid Image Size"))
                }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        val painterState by painter.state.collectAsStateWithLifecycle()
        val transition by animateFloatAsState(
            targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
            animationSpec = tween(800)
        )

        when (val result = imageLoadResult) {
            null -> PulseAnimation(modifier = Modifier.size(60.dp))
            else ->
                Image(
                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                    contentDescription = book.title,
                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                    modifier = Modifier.aspectRatio(
                        ratio = 0.65f,
                        matchHeightConstraintsFirst = true
                    )
                        .graphicsLayer {
                            rotationX = (1f - transition) * 30f
                            val scale = 0.8f + (0.2f * transition)
                            scaleX = scale
                            scaleY = scale
                        }
                )
        }
    }
}