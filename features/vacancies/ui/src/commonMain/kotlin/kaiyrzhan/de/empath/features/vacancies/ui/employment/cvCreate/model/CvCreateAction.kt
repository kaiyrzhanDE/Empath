package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model

internal sealed interface CvCreateAction {
    class ShowSnackbar(val message: String) : CvCreateAction
}