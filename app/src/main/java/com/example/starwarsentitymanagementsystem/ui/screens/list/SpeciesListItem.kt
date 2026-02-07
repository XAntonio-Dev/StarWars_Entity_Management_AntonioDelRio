import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.starwarsentitymanagementsystem.R
import com.example.starwarsentitymanagementsystem.data.model.Species
import com.example.starwarsentitymanagementsystem.ui.base.common.LocalSpacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpeciesItem(
    species: Species,
    onEdit: () -> Unit,
    onLongClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { },
                onLongClick = onLongClick // Requisito Rúbrica: Pulsación larga
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Face, null, Modifier.size(40.dp).padding(end = spacing.medium), MaterialTheme.colorScheme.primary)
            Column(modifier = Modifier.weight(1f)) {
                Text(species.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(species.classification, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = onEdit) { Text(stringResource(R.string.edit)) }
        }
    }
}