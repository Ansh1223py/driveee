package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Chauffeur
import com.example.model.MockData
import com.example.ui.theme.DriveeAmber
import com.example.ui.theme.DriveeBackground
import com.example.ui.theme.DriveeCardBorder
import com.example.ui.theme.DriveeGreen
import com.example.ui.theme.DriveeGreenBg
import com.example.ui.theme.DriveeNavy
import com.example.ui.theme.DriveeNavyDark
import com.example.ui.theme.DriveeOrange
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeSurfaceHigh
import com.example.ui.theme.DriveeSurfaceLow
import com.example.ui.theme.DriveeTextMuted
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary

@Composable
fun DriversListScreen(
    durationHours: Int = 4,
    pickupLocation: String = "Bandra West, Mumbai",
    onSelectChauffeur: (Chauffeur) -> Unit,
    onEditSearch: () -> Unit
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All Available") }
    var viewingChauffeurProfile by remember { mutableStateOf<Chauffeur?>(null) }

    val filterChips = listOf(
        "All Available",
        "Instant (≤ 20m)",
        "4.9+ Top Rated",
        "Automatic Specialist",
        "SUV & Highway"
    )

    val filteredDrivers = MockData.chauffeurs.filter { driver ->
        when (selectedFilter) {
            "Instant (≤ 20m)" -> driver.etaMins <= 20
            "4.9+ Top Rated" -> driver.rating >= 4.90
            "Automatic Specialist" -> driver.specialties.contains("Automatic", ignoreCase = true)
            "SUV & Highway" -> driver.category == "suv" || driver.experienceYears >= 10
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriveeBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // 1. Sticky Search Summary Header
        Surface(
            color = DriveeNavy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = DriveeOrange,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = pickupLocation,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "$durationHours Hours • Starts 8:00 PM today • Manual/Auto",
                        fontSize = 11.sp,
                        color = DriveeSurfaceHigh
                    )
                }

                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onEditSearch() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Edit",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Filter Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterChips.forEach { chip ->
                val isSelected = selectedFilter == chip
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) DriveeOrange else DriveeSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) DriveeOrange else DriveeCardBorder
                    ),
                    modifier = Modifier.clickable { selectedFilter = chip }
                ) {
                    Text(
                        text = chip,
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else DriveeTextPrimary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        // 3. Results Count Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredDrivers.size} Screened Chauffeurs Found",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DriveeNavy
            )
            Text(
                text = "⚡ Real-Time GPS Available",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = DriveeGreen
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 4. Drivers Cards List (Image 6)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            filteredDrivers.forEach { driver ->
                DriverCard(
                    chauffeur = driver,
                    durationHours = durationHours,
                    onViewProfile = { viewingChauffeurProfile = driver },
                    onBook = { onSelectChauffeur(driver) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 5. DRIVEE Car Assurance Guarantee Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurfaceLow),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "₹50 Lakh Vehicle Shield Included",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Every trip piloted by our chauffeurs is insured under ICICI Lombard Policy #DRV-POL-9921. Covers accidental damages, personal liability, and roadside assistance automatically with zero deductible to the vehicle owner.",
                    fontSize = 11.5.sp,
                    color = DriveeTextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }

    // Driver Profile Modal
    viewingChauffeurProfile?.let { chauffeur ->
        DriverProfileDialog(
            chauffeur = chauffeur,
            onBookNow = { onSelectChauffeur(it) },
            onDismiss = { viewingChauffeurProfile = null }
        )
    }
}

@Composable
fun DriverCard(
    chauffeur: Chauffeur,
    durationHours: Int,
    onViewProfile: () -> Unit,
    onBook: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DriveeSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (chauffeur.isTopMatch) DriveeOrange.copy(alpha = 0.5f) else DriveeCardBorder
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Badge if any
            chauffeur.badgeLabel?.let { badge ->
                Surface(
                    color = if (chauffeur.isTopMatch) DriveeOrange else DriveeNavy,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Driver Core Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = chauffeur.photoUrl,
                    contentDescription = chauffeur.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, DriveeOrange, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = chauffeur.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = "Verified",
                            tint = DriveeGreen,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = "${chauffeur.age} yrs • ${chauffeur.experienceYears}+ Yrs Exp • ${chauffeur.languages}",
                        fontSize = 11.sp,
                        color = DriveeTextSecondary
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = DriveeAmber,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${chauffeur.rating} (${chauffeur.tripsCount} trips)",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeTextPrimary
                        )
                    }
                }

                // Price and ETA block
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "₹${chauffeur.ratePerHour}/hr",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                    Text(
                        text = "Total: ₹${chauffeur.ratePerHour * durationHours}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DriveeOrange
                    )
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "⚡ In ${chauffeur.etaMins}m",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeGreen,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Specialties & Verification Chips
            Surface(
                color = DriveeSurfaceLow,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalPolice,
                        contentDescription = null,
                        tint = DriveeGreen,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Police Background Cleared • License Verified",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DriveeGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Specialty: ${chauffeur.specialties}",
                fontSize = 11.sp,
                color = DriveeTextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons: Profile & Book Driver
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewProfile,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Profile", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = DriveeNavy)
                }

                Button(
                    onClick = onBook,
                    modifier = Modifier
                        .weight(1.5f)
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Book ${chauffeur.name.split(" ")[0]}",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
