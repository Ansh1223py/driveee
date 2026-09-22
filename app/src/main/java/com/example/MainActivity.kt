package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppScreen
import com.example.model.Chauffeur
import com.example.model.MockData
import com.example.model.TransmissionType
import com.example.ui.components.DriveeBottomBar
import com.example.ui.components.DriveeTopBar
import com.example.ui.screens.AccountScreen
import com.example.ui.screens.DriversListScreen
import com.example.ui.screens.EmergencySosDialog
import com.example.ui.screens.FamilyModeScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveTrackingScreen
import com.example.ui.screens.SafetyScreen
import com.example.ui.theme.DriveeBackground
import com.example.ui.theme.DriveeCardBorder
import com.example.ui.theme.DriveeNavy
import com.example.ui.theme.DriveeOrange
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DriveeMainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriveeMainApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var selectedChauffeur by remember { mutableStateOf(MockData.chauffeurs[0]) }
    var durationHours by remember { mutableIntStateOf(4) }
    var transmission by remember { mutableStateOf(TransmissionType.MANUAL) }
    var pickupLocation by remember { mutableStateOf("Bandra West, Mumbai") }
    var showSosDialog by remember { mutableStateOf(false) }
    var showLocationSheet by remember { mutableStateOf(false) }
    var hasActiveTrip by remember { mutableStateOf(true) }

    val mumbaiLocations = listOf(
        "Bandra West, Mumbai",
        "Juhu / Santacruz, Mumbai",
        "Worli Sea Face, Mumbai",
        "Bandra Kurla Complex (BKC)",
        "Powai / Hiranandani",
        "Colaba / Nariman Point"
    )

    Scaffold(
        topBar = {
            DriveeTopBar(
                currentLocation = pickupLocation,
                onLocationClick = { showLocationSheet = true },
                onSosClick = { showSosDialog = true },
                onProfileClick = { currentScreen = AppScreen.ACCOUNT }
            )
        },
        bottomBar = {
            DriveeBottomBar(
                currentScreen = currentScreen,
                onScreenSelect = { currentScreen = it },
                hasActiveTrip = hasActiveTrip
            )
        },
        containerColor = DriveeBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        onNavigateToDrivers = { hours, trans, loc ->
                            durationHours = hours
                            transmission = trans
                            pickupLocation = loc
                            currentScreen = AppScreen.DRIVERS
                        },
                        onNavigateToScreen = { currentScreen = it }
                    )
                }

                AppScreen.DRIVERS -> {
                    DriversListScreen(
                        durationHours = durationHours,
                        pickupLocation = pickupLocation,
                        onSelectChauffeur = { chauffeur ->
                            selectedChauffeur = chauffeur
                            hasActiveTrip = true
                            currentScreen = AppScreen.LIVE_TRACKING
                        },
                        onEditSearch = {
                            currentScreen = AppScreen.HOME
                        }
                    )
                }

                AppScreen.LIVE_TRACKING -> {
                    LiveTrackingScreen(
                        chauffeur = selectedChauffeur,
                        initialHours = durationHours,
                        onSosClick = { showSosDialog = true },
                        onTripCompleted = {
                            hasActiveTrip = false
                            currentScreen = AppScreen.ACCOUNT
                        }
                    )
                }

                AppScreen.FAMILY -> {
                    FamilyModeScreen(
                        onBookFamilyDriver = { relation ->
                            selectedChauffeur = MockData.chauffeurs[2] // Mohammad Imran for family care
                            durationHours = 3
                            hasActiveTrip = true
                            currentScreen = AppScreen.LIVE_TRACKING
                        }
                    )
                }

                AppScreen.SAFETY -> {
                    SafetyScreen(
                        onTriggerSos = { showSosDialog = true }
                    )
                }

                AppScreen.ACCOUNT -> {
                    AccountScreen(
                        onRebookTrip = { trip ->
                            val driverMatch = MockData.chauffeurs.find { it.name == trip.driverName }
                                ?: MockData.chauffeurs[0]
                            selectedChauffeur = driverMatch
                            currentScreen = AppScreen.LIVE_TRACKING
                        }
                    )
                }
            }
        }
    }

    // Emergency SOS Modal
    if (showSosDialog) {
        EmergencySosDialog(
            onDismiss = { showSosDialog = false }
        )
    }

    // Location Picker Bottom Sheet
    if (showLocationSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLocationSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = DriveeSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "SELECT PICKUP AREA IN MUMBAI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = DriveeNavy,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                mumbaiLocations.forEach { loc ->
                    val isSelected = pickupLocation == loc
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                pickupLocation = loc
                                showLocationSheet = false
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = if (isSelected) DriveeOrange else DriveeTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = loc,
                                fontSize = 13.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) DriveeOrange else DriveeTextPrimary
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = DriveeOrange,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
