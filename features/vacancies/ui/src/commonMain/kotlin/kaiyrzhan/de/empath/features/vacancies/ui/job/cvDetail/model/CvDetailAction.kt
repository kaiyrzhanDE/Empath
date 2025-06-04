package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model

internal sealed interface CvDetailAction {
    class ShowSnackbar(val message: String) : CvDetailAction
}