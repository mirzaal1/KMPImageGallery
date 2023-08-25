import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        let viewModel = ImagesGalleryViewModel.init(fetchUseCase: ImagesFetchUseCase.init())
        return Main_iOSKt.MainView(viewModel: viewModel)
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    
    init() {
       KoinModuleKt.doInitKoin()
    }
    
    var body: some View {
        ComposeView().ignoresSafeArea(.keyboard)
    }
}
