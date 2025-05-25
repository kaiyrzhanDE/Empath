package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model

internal sealed interface CvEditAction {
    class ShowSnackbar(val message: String) : CvEditAction
}