package kaiyrzhan.de.empath.features.vacancies.ui.employment.vacancies

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun VacanciesScreen(
    component: VacanciesComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier,
    ){
        Text(
            text = "Employment vacancies",
        )
    }
}