package kaiyrzhan.de.empath.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.main.MainRootComponent
import kaiyrzhan.de.empath.main.MainRootComponent.Child
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NavigationBar(
    component: MainRootComponent,
    currentChild: Child,
    modifier: Modifier = Modifier,
) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = EmpathTheme.colors.onSurface,
        selectedTextColor = EmpathTheme.colors.onSurface,
        indicatorColor = EmpathTheme.colors.primary,
        unselectedIconColor = EmpathTheme.colors.onSurfaceVariant,
        unselectedTextColor = EmpathTheme.colors.onSurfaceVariant,
    )
    Column(
        modifier = modifier,
    ) {
        HorizontalDivider(color = EmpathTheme.colors.outlineVariant)
        NavigationBar(
            modifier = Modifier
                .navigationBarsPadding(),
            containerColor = EmpathTheme.colors.surface,
            contentColor = EmpathTheme.colors.onSurfaceVariant,
        ) {
            NavigationBarItem(
                selected = currentChild is Child.Vacancies,
                onClick = component::onVacanciesTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Vacancies) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Vacancies",
                    )
                },
                label = {
                    Text(
                        text = "Vacancies",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )

            NavigationBarItem(
                selected = currentChild is Child.Analytics,
                onClick = component::onAnalyticsTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Analytics) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Analytics",
                    )
                },
                label = {
                    Text(
                        text = "Analytics",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )

            NavigationBarItem(
                selected = currentChild is Child.Menu,
                onClick = component::onMenuTabClick,
                icon = {
                    Icon(
                        imageVector = if (currentChild is Child.Menu) Icons.Default.Info else Icons.Default.Info,
                        contentDescription = "Menu",
                    )
                },
                label = {
                    Text(
                        text = "Menu",
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )

            NavigationBarItem(
                selected = currentChild is Child.Articles,
                onClick = component::onArticlesTabClick,
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = if (currentChild is Child.Articles) Res.drawable.ic_local_library_filled
                            else Res.drawable.ic_local_library_filled
                        ),
                        contentDescription = stringResource(Res.string.articles),
                    )
                },
                label = {
                    Text(
                        text = stringResource(Res.string.articles),
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )

            NavigationBarItem(
                selected = currentChild is Child.Profile,
                onClick = component::onProfileTabClick,
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = if (currentChild is Child.Profile) Res.drawable.ic_person_filled
                            else Res.drawable.ic_person_outlined
                        ),
                        contentDescription = stringResource(Res.string.profile),
                    )
                },
                label = {
                    Text(
                        text = stringResource(Res.string.profile),
                        style = EmpathTheme.typography.labelMedium,
                    )
                },
                colors = colors,
            )
        }
    }
}