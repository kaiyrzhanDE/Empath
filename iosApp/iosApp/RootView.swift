import UIKit
import SwiftUI
import EmpathApp

struct RootView: UIViewControllerRepresentable {
    let root: RootComponent
    let backDispatcher: BackDispatcher

    func makeUIViewController(context: Context) -> UIViewController {
        let controller = RootViewControllerKt.rootViewController(root: root, backDispatcher: backDispatcher)
        controller.overrideUserInterfaceStyle = .dark
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}


