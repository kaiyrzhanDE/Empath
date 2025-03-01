import UIKit
import SwiftUI
import EmpathShared

struct RootView: UIViewControllerRepresentable {
    let root: ComposeAppRootComponent
    let backDispatcher: BackDispatcher

    func makeUIViewController(context: Context) -> UIViewController {
        let controller = RootViewControllerKt.rootViewController(root: root, backDispatcher: backDispatcher)
        controller.overrideUserInterfaceStyle = .dark
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}


