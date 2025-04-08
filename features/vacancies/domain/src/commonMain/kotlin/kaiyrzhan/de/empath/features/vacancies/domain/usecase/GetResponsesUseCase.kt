package kaiyrzhan.de.empath.features.vacancies.domain.usecase

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import kotlinx.coroutines.flow.Flow

public class GetResponsesUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        vacancyId: String? = null,
    ): Flow<PagingData<Response>> {
        return repository
            .getResponses(
                vacancyId = vacancyId,
            )
    }
}