package kaiyrzhan.de.empath.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kaiyrzhan.de.empath.features.articles.ui.root.ArticlesRootComponent
import kaiyrzhan.de.empath.features.profile.ui.root.ProfileRootComponent

public interface MainRootComponent : BackHandlerOwner {

    public val stack: Value<ChildStack<*, Child>>

    public fun onBackClick()

    public fun onProfileTabClick()
    public fun onArticlesTabClick()
    public fun onMenuTabClick()
    public fun onAnalyticsTabClick()
    public fun onVacanciesTabClick()

    public sealed class Child {
        public class Profile(public val component: ProfileRootComponent) : Child()
        public class Articles(public val component: ArticlesRootComponent) : Child()
        public class Menu : Child()  //TODO("Need implementation")
        public class Analytics : Child()  //TODO("Need implementation")
        public class Vacancies : Child()  //TODO("Need implementation")
    }
}