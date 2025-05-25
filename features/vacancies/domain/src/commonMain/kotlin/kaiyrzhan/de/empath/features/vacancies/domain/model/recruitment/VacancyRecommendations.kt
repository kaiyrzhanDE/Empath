package kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment

public class VacancyRecommendations(
    public val weights: VacancyWeight,
    public val recommendations: List<Cv>,
)