package kaiyrzhan.de.empath.features.profile.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.ui.profile.components.ProfileErrorCard
import kaiyrzhan.de.empath.features.profile.ui.profile.components.ProfileCard
import kaiyrzhan.de.empath.features.profile.ui.profile.components.AccountProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.GeneralProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.StudyProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.WorkProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileScreen(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    val profileState = component.state.collectAsState()

    ProfileScreen(
        modifier = modifier
            .fillMaxSize(),
        state = profileState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .background(color = EmpathTheme.colors.scrim)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val maxHeight = maxWidth.value * 0.15f
            when (state) {
                is ProfileState.Success -> {
                    ProfileCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),
                        name = state.user.nickname,
                        email = state.user.email,
                        imageUrl = state.user.imageUrl,
                        onUserPageClick = { onEvent(ProfileEvent.UserPageClick) },
                    )
                }

                is ProfileState.Error -> {
                    ProfileErrorCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),
                        imageSize = maxHeight.dp,
                        onReloadClick = { onEvent(ProfileEvent.Reload) },
                        onUserPageClick = { onEvent(ProfileEvent.UserPageClick) },
                    )
                }

                is ProfileState.Loading -> {
                    CircularLoadingScreen(
                        modifier = Modifier
                            .clip(EmpathTheme.shapes.small)
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),

                        )
                }

                is ProfileState.Initial -> Unit
            }
        }
        AccountProperties(
            onEditProfileClick = { onEvent(ProfileEvent.EditProfileClick) },
        )
        StudyProperties()
        WorkProperties()
        GeneralProperties(
            onLogOutClick = { onEvent(ProfileEvent.LogOut) }
        )
    }
}


@Preview
@Composable
private fun Preview() {
    ProfileScreen(
        component = FakeProfileComponent(),
        modifier = Modifier.fillMaxSize(),
    )
}