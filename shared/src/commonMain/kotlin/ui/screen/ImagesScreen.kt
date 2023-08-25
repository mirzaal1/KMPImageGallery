@file:OptIn(ExperimentalFoundationApi::class)

package ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogProperties
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import domain.State
import domain.model.Images
import domain.model.SingleImage
import kotlinx.coroutines.delay
import ui.theme.AppTheme
import ui.viewmodel.ImagesGalleryViewModel
import utils.OnBottomReached
import utils.generateImageLoader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesScreen(viewModel: ImagesGalleryViewModel) {
    CompositionLocalProvider(LocalImageLoader provides generateImageLoader()) {
        AppTheme {
            val images: Images by viewModel.images.collectAsState()
            val state: State by viewModel.state.collectAsState()
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val listState = rememberLazyStaggeredGridState()
            val scope = rememberCoroutineScope()
            val onRefresh = viewModel::refreshImages

            Scaffold(scaffoldState = scaffoldState) {
                Box(Modifier.fillMaxSize()) {
                    when (state) {
                        State.EMPTY -> EmptyScreen(onRefresh)
                        State.ERROR -> EmptyScreen(onRefresh)
                        else -> ImagesList(
                            onLoadMore = viewModel::loadMore,
                            images = images,
                            state = listState
                        ) { isPreviewVisible, imageUrl ->
                            PreviewDialog(isPreviewVisible, imageUrl)
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun EmptyScreen(
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No images",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp),
        )

        Text(
            text = "Please try again later",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp),
        )

        Button(
            onClick = { onRefresh() }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Refresh")
        }
    }
}

@Composable
fun ImagesList(
    images: Images,
    state: LazyStaggeredGridState,
    onLoadMore: () -> Unit,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
) {
    PlatformSpecificImagesGrid(
        onLoadMore = onLoadMore,
        images = images,
        modifier = Modifier.fillMaxSize().testTag("imagesGrid"),
        onPreviewVisibilityChanged = onPreviewVisibilityChanged,
        state = state,
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlatformSpecificImagesGrid(
    state: LazyStaggeredGridState,
    images: Images,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        state = state.apply { OnBottomReached(onLoadMore::invoke) },
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(items = images.images, key = SingleImage::imageUrl) { image ->
            ImageItem(
                image,
                (180 * image.ratio).dp,
                ContentScale.Crop,
                onPreviewVisibilityChanged,
            )
        }
    }
}


@Composable
fun ImageItem(
    image: SingleImage,
    height: Dp,
    contentScale: ContentScale,
    onPreviewVisibilityChanged: @Composable (isVisible: Boolean, imageUrl: String) -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    var isLongPressed by remember { mutableStateOf(false) }

    if (isLongPressed) {
        onPreviewVisibilityChanged.invoke(true, image.imageUrl)
    } else {
        onPreviewVisibilityChanged.invoke(false, image.imageUrl)
    }

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!isLongPressed)
                            uriHandler.openUri(image.imageUrl)
                    },
                    onPress = {
                        delay(200)
                        isLongPressed = true
                        tryAwaitRelease()
                        isLongPressed = false
                    })
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        val action by rememberImageAction(
            url = image.imageUrl
        )

        val painter = rememberImageActionPainter(action)

        val transition by animateFloatAsState(
            targetValue = if (action is ImageResult) 1f else 0f
        )

        Image(painter = painter, contentDescription = null, modifier = Modifier.graphicsLayer {
            val animatedValue: Float = .8f + (.2f * transition)
            val blurValue: Float = if (transition == 1f) 1f else 24f + (.2f * transition)
            scaleX = animatedValue
            scaleY = animatedValue
            alpha = animatedValue
            renderEffect = BlurEffect(blurValue, blurValue)
        }.height(height), contentScale = contentScale)
    }
}



@Composable
fun PreviewDialog(
    isPreviewVisible: Boolean,
    imageUrl: String,
    dialogState: MaterialDialogState = rememberMaterialDialogState()
) {
    if (isPreviewVisible) {
        MaterialDialog(
            dialogState = dialogState,
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            properties = MaterialDialogProperties(
                resizable = false,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) { PreviewImage(imageUrl) }
        LaunchedEffect(imageUrl) {
            dialogState.show()
        }
    }
}

@Composable
fun PreviewImage(imageUrl: String) {
    val action by rememberImageAction(
        url = imageUrl
    )
    val painter = rememberImageActionPainter(action)

    val transition by animateFloatAsState(
        targetValue = if (action is ImageResult) 1f else 0f
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.graphicsLayer {
            val animatedValue: Float = .8f + (.2f * transition)
            val blurValue: Float =
                if (transition == 1f) 1f else 24f + (.2f * transition)
            scaleX = animatedValue
            scaleY = animatedValue
            alpha = animatedValue
            renderEffect = BlurEffect(blurValue, blurValue)
        }.padding(24.dp).fillMaxSize()
    )
}




