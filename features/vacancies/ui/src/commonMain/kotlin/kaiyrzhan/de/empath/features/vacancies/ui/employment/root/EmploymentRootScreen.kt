package kaiyrzhan.de.empath.features.vacancies.ui.employment.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kaiyrzhan.de.empath.core.ui.animations.swipe
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies.VacanciesScreen
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.VacancyDetailScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun EmploymentRootScreen(
    component: EmploymentRootComponent,
    modifier: Modifier = Modifier,
) {
    val windowInfo = currentWindowAdaptiveInfo()

    if (windowInfo.isPhone()) {
        EmploymentRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        EmploymentRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(fade()),
            ),
        )
    }

}

@Composable
private fun EmploymentRootScreen(
    modifier: Modifier = Modifier,
    component: EmploymentRootComponent,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is EmploymentRootComponent.Child.Vacancies -> {
                VacanciesScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is EmploymentRootComponent.Child.VacancyDetail -> {
                VacancyDetailScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

        }
    }
}