package kaiyrzhan.de.empath.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.composeapp.generated.resources.Res
import empath.composeapp.generated.resources.ic_person_selected
import empath.composeapp.generated.resources.ic_person_unselected
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.main.MainRootComponent
import kaiyrzhan.de.empath.main.MainRootComponent.Child
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun NavigationRail(
    component: MainRootComponent,
    currentChild: Child,
    modifier: Modifier = Modifier,
) {
    val colors = NavigationRailItemDefaults.colors(
        selectedIconColor = EmpathTheme.colors.onSurface,
        selectedTextColor = EmpathTheme.colors.onSurface,
        indicatorColor = EmpathTheme.colors.primary,
        unselectedIconColor = EmpathTheme.colors.onSurfaceVariant,
        unselectedTextColor = EmpathTheme.colors.onSurfaceVariant,
    )

    NavigationRail(
        modifier = modifier,
        containerColor = EmpathTheme.colors.scrim,
        contentColor = EmpathTheme.colors.onSurfaceVariant,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        NavigationRailItem(
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
        Spacer(modifier = Modifier.height(12.dp))
        NavigationRailItem(
            selected = currentChild is Child.Analytics,
            onClick = component::onAnalyticsTabClick,
            icon = {
                Icon(
                    imageVector = if (currentChild is Child.Vacancies) Icons.Default.Info else Icons.Default.Info,
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
        Spacer(modifier = Modifier.height(12.dp))
        NavigationRailItem(
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
        Spacer(modifier = Modifier.height(12.dp))
        NavigationRailItem(
            selected = currentChild is Child.Study,
            onClick = component::onStudyTabClick,
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
        Spacer(modifier = Modifier.height(12.dp))
        NavigationRailItem(
            selected = currentChild is Child.Profile,
            onClick = component::onProfileTabClick,
            icon = {
                Icon(
                    painter = painterResource(
                        resource = if (currentChild is Child.Profile) Res.drawable.ic_person_selected else Res.drawable.ic_person_unselected,
                    ),
                    contentDescription = "Profile",
                )
            },
            label = {
                Text(
                    text = "Profile",
                    style = EmpathTheme.typography.labelMedium,
                )
            },
            colors = colors,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}