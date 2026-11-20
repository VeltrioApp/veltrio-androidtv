package app.veltrio.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.veltrio.android.Nav
import app.veltrio.android.VeltrioRoot
import app.veltrio.android.screens.HomeScreen
import app.veltrio.android.screens.LibraryScreen
import app.veltrio.android.screens.LiveScreen
import app.veltrio.android.screens.SourcesScreen
import app.veltrio.shared.ui.Type
import app.veltrio.shared.ui.VeltrioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VeltrioRoot(this, tv = true) { Sections() } }
    }
}

private val sections = listOf("Accueil", "Collection", "Direct", "Sources")

/** The macOS sections on a TV: the wordmark and numbered sections in a rail
 *  on the left, search and settings below them; the section on the right. */
@Composable
private fun Sections() {
    val p = VeltrioTheme.palette
    val first = remember { FocusRequester() }
    LaunchedEffect(Unit) { runCatching { first.requestFocus() } }
    Row(Modifier.fillMaxSize()) {
        Column(
            Modifier.width(200.dp).fillMaxHeight().background(p.backgroundAlt).padding(vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text("Veltrio", style = Type.displaySerif(28), color = p.textPrimary, modifier = Modifier.padding(start = 24.dp, bottom = 28.dp))
            sections.forEachIndexed { index, label ->
                RailItem(
                    "%02d".format(index + 1), label, index == Nav.tab,
                    if (index == Nav.tab) Modifier.focusRequester(first) else Modifier,
                ) { Nav.tab = index }
            }
            Spacer(Modifier.weight(1f))
            // Setting a TV up with a remote is tedious: a phone's settings, one step away.
            RailIcon(Icons.Outlined.PhoneAndroid, "Depuis un téléphone") { Nav.pair() }
            RailIcon(Icons.Outlined.Search, "Rechercher") { Nav.search() }
            RailIcon(Icons.Outlined.Settings, "Réglages") { Nav.settings() }
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            when (Nav.tab) {
                0 -> HomeScreen()
                1 -> LibraryScreen()
                2 -> LiveScreen()
                else -> SourcesScreen()
            }
        }
    }
}

@Composable
private fun RailItem(number: String, label: String, active: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val p = VeltrioTheme.palette
    Row(
        modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(Modifier.width(3.dp).height(18.dp).background(if (active) p.accent else Color.Transparent))
        Text(number, style = Type.mono(11, FontWeight.SemiBold), color = p.accentText)
        Text(label.uppercase(), style = Type.eyebrow(12, 0.16f), color = if (active) p.textPrimary else p.textTertiary)
    }
}

@Composable
private fun RailIcon(icon: ImageVector, label: String, onClick: () -> Unit) {
    val p = VeltrioTheme.palette
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(icon, null, Modifier.size(20.dp), tint = p.textSecondary)
        Text(label.uppercase(), style = Type.eyebrow(11, 0.16f), color = p.textSecondary)
    }
}
