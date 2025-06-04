package kaiyrzhan.de.empath.features.vacancies.ui.job.cvDetail.model

internal sealed interface CvDetailEvent {
    data object LoadCv : CvDetailEvent
    data object BackClick : CvDetailEvent
}