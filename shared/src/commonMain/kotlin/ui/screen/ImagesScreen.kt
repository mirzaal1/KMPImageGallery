package ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import domain.State
import domain.model.Images
import ui.theme.AppTheme
import ui.viewmodel.ImagesGalleryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagesScreen(viewModel: ImagesGalleryViewModel) {
    AppTheme {
        CompositionLocalProvider() {
            val imagesList: Images by viewModel.images.collectAsState()
            val state: State by viewModel.state.collectAsState()

            Button(
                onClick = {
                }
            ) {
                Text("Hello ali")
            }
        }
    }
}



