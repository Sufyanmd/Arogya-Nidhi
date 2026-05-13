package com.example.arogyanidhi.ui.schemes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeListScreen(
    viewModel: SchemeViewModel,
    onNavigateBack: () -> Unit,
    onSchemeClick: (String) -> Unit
) {
    val schemes by viewModel.schemes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Government Schemes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(schemes) { scheme ->
                Card(
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                    onClick = { onSchemeClick(scheme.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = scheme.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = scheme.category, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
