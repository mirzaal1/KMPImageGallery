import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.screen.ImagesScreen
import ui.viewmodel.ImagesGalleryViewModel


fun MainView(viewModel: ImagesGalleryViewModel): UIViewController = ComposeUIViewController { ImagesScreen(viewModel) }