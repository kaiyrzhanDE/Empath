package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.root

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
import kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyDetail.VacancyDetailScreen
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.VacancyCreateScreen
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.createVacancy.model.VacancyCreateState
import kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancies.VacanciesScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
public fun RecruitmentRootScreen(
    component: RecruitmentRootComponent,
    modifier: Modifier = Modifier,
) {
    val windowInfo = currentWindowAdaptiveInfo()

    if (windowInfo.isPhone()) {
        RecruitmentRootScreen(
            component = component,
            modifier = modifier,
            animation = predictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackClick,
                fallbackAnimation = stackAnimation(swipe()),
            ),
        )
    } else {
        RecruitmentRootScreen(
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
private fun RecruitmentRootScreen(
    modifier: Modifier = Modifier,
    component: RecruitmentRootComponent,
    animation: StackAnimation<Any, Any>? = null,
) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = animation,
    ) { child ->
        when (val instance = child.instance) {
            is RecruitmentRootComponent.Child.Vacancies -> {
                VacanciesScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RecruitmentRootComponent.Child.VacancyDetail -> {
                VacancyDetailScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RecruitmentRootComponent.Child.VacancyCreate -> {
                VacancyCreateScreen(
                    component = instance.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}