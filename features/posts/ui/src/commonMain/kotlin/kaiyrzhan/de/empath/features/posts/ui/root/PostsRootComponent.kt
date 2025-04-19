package kaiyrzhan.de.empath.features.posts.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.posts.ui.postCreate.PostCreateComponent
import kaiyrzhan.de.empath.features.posts.ui.postDetail.PostDetailComponent
import kaiyrzhan.de.empath.features.posts.ui.postEdit.PostEditComponent
import kaiyrzhan.de.empath.features.posts.ui.posts.PostsComponent

public interface PostsRootComponent : BackHandlerOwner {
    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public sealed class Child {
        internal class Posts(val component: PostsComponent) : Child()
        internal class PostDetail(val component: PostDetailComponent) : Child()
        internal class PostCreate(val component: PostCreateComponent) : Child()
        internal class PostEdit(val component: PostEditComponent) : Child()
    }
}