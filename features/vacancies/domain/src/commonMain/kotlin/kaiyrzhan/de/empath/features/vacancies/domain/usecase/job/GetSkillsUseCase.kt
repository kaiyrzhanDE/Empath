package kaiyrzhan.de.empath.features.vacancies.domain.usecase.job

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow

public class GetSkillsUseCase(
    private val repository: JobRepository,
) {
    public suspend operator fun invoke(
        query: String?,
    ): Flow<PagingData<Skill>> {
        return repository.getSkills(query)
    }
}