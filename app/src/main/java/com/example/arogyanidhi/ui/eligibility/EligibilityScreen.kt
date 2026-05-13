package com.example.arogyanidhi.ui.eligibility

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arogyanidhi.domain.model.Scheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EligibilityScreen(
    onNavigateBack: () -> Unit,
    viewModel: EligibilityViewModel = hiltViewModel()
) {
    val eligibilityData by viewModel.eligibilityData.collectAsState()
    val eligibleSchemes by viewModel.eligibleSchemes.collectAsState()
    var currentStep by remember { mutableStateOf(0) }

    // Hardcoded list for the "Hybrid" approach test
    val availableSchemes = listOf(
        Scheme(
            id = "1",
            name = "Ayushman Bharat",
            description = "Free health insurance up to 5 Lakhs for BPL families.",
            incomeLimit = 150000,
            requiresBPL = true,
            category = "General"
        ),
        Scheme(
            id = "2",
            name = "PM-Kisan Maandhan",
            description = "Monthly pension scheme for small and marginal farmers.",
            incomeLimit = 300000,
            category = "Farmer"
        ),
        Scheme(
            id = "3",
            name = "Janani Suraksha Yojana",
            description = "Financial assistance for pregnant women (BPL/SC/ST).",
            genderTarget = "Female",
            requiresBPL = true
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eligibility Checker") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            if (eligibleSchemes.isEmpty() && currentStep < 2) {
                // --- STEP 1: FINANCIAL ---
                if (currentStep == 0) {
                    Text("Step 1: Financial Information", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = eligibilityData.income.toString(),
                        onValueChange = { viewModel.updateIncome(it.toDoubleOrNull() ?: 0.0) },
                        label = { Text("Annual Income (₹)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Checkbox(
                            checked = eligibilityData.isBpl,
                            onCheckedChange = { viewModel.updateBpl(it) }
                        )
                        Text("I hold a BPL Card")
                    }

                    Button(
                        onClick = { currentStep = 1 },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Next")
                    }
                }
                // --- STEP 2: PROFESSIONAL ---
                else if (currentStep == 1) {
                    Text("Step 2: Professional Information", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = eligibilityData.occupation,
                        onValueChange = { viewModel.updateOccupation(it) },
                        label = { Text("Occupation (e.g. Farmer, Student)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.checkEligibility(availableSchemes)
                            currentStep = 2 // Move to results
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check Eligibility")
                    }

                    TextButton(onClick = { currentStep = 0 }) {
                        Text("Back to Financial Info")
                    }
                }
            }
            // --- STEP 3: RESULTS ---
            else {
                Text("Recommended Schemes", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Based on your profile and quiz answers:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (eligibleSchemes.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No matching schemes found for your criteria.")
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(eligibleSchemes) { scheme ->
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = scheme.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = scheme.description, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        // Reset the state to try again
                        currentStep = 0
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Finish")
                }
            }
        }
    }
}