package com.example.arogyanidhi.ui.schemes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeDetailScreen(
    schemeId: String,
    viewModel: SchemeDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val scheme by viewModel.scheme.collectAsState()
    val documents by viewModel.documents.collectAsState()

    LaunchedEffect(schemeId) {
        viewModel.loadScheme(schemeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(scheme?.name ?: "Scheme Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            scheme?.let {
                Text(text = it.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Required Documents", style = MaterialTheme.typography.titleMedium)
                
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(documents) { doc ->
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Checkbox(
                                checked = doc.isReady,
                                onCheckedChange = { viewModel.toggleDocument(doc) }
                            )
                            Text(text = doc.name)
                        }
                    }
                }
            }
        }
    }
}
