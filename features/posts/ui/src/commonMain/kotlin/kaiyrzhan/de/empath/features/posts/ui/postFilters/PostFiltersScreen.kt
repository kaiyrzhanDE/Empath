package kaiyrzhan.de.empath.features.posts.ui.postFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.posts.ui.postFilters.components.PostReactionTypes
import kaiyrzhan.de.empath.features.posts.ui.postFilters.components.Specializations
import kaiyrzhan.de.empath.features.posts.ui.postFilters.components.TopBar
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersEvent
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.PostFiltersState
import kaiyrzhan.de.empath.features.posts.ui.postFilters.model.SpecializationsState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PostFiltersScreen(
    modifier: Modifier = Modifier,
    component: PostFiltersComponent,
) {
    val state = component.state.collectAsState()
    val specializationsState = component.specializationsState.collectAsState()

    PostFiltersScreen(
        modifier = modifier,
        state = state.value,
        specializationsState = specializationsState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun PostFiltersScreen(
    modifier: Modifier = Modifier,
    state: PostFiltersState,
    specializationsState: SpecializationsState,
    onEvent: (PostFiltersEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onEvent = onEvent,
            )
        },
        containerColor = EmpathTheme.colors.surface,
        contentColor = EmpathTheme.colors.onSurface,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(contentPadding)
                .screenHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.query,
                shape = EmpathTheme.shapes.small,
                onValueChange = { query -> onEvent(PostFiltersEvent.QueryChange(query)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.title),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.includeWords,
                shape = EmpathTheme.shapes.small,
                onValueChange = { word -> onEvent(PostFiltersEvent.IncludeWordsChange(word)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.include_words),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.excludeWords,
                shape = EmpathTheme.shapes.small,
                onValueChange = { query -> onEvent(PostFiltersEvent.ExcludeWordsChange(query)) },
                textStyle = EmpathTheme.typography.bodyLarge,
                maxLines = 2,
                label = {
                    Text(
                        text = stringResource(Res.string.exclude_words),
                        style = EmpathTheme.typography.bodyLarge,
                    )
                },
            )


            PostReactionTypes(
                modifier = Modifier
                    .fillMaxWidth(),
                state = state,
                onEvent = onEvent,
            )

            Specializations(
                modifier = Modifier.fillMaxWidth(),
                specializationsState = specializationsState,
                state = state,
                onEvent = onEvent,
            )

            Row(
                modifier = Modifier
                    .defaultMaxWidth()
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(PostFiltersEvent.Clear) },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.surfaceContainer,
                        contentColor = EmpathTheme.colors.onSurface,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.clear),
                        style = EmpathTheme.typography.labelLarge,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(PostFiltersEvent.Apply) },
                    shape = EmpathTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmpathTheme.colors.primary,
                        contentColor = EmpathTheme.colors.onPrimary,
                    ),
                ) {
                    Text(
                        text = stringResource(Res.string.apply),
                        style = EmpathTheme.typography.labelLarge,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
