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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.model.MockData
import com.example.model.TripRecord
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
fun AccountScreen(
    onRebookTrip: (TripRecord) -> Unit
) {
    val context = LocalContext.current
    var selectedFilterTab by remember { mutableStateOf("All Trips") }
    var viewingInvoiceTrip by remember { mutableStateOf<TripRecord?>(null) }

    val filterTabs = listOf("All Trips", "Completed", "Upcoming", "Family Care")

    val displayedTrips = MockData.tripsHistory.filter { trip ->
        when (selectedFilterTab) {
            "Completed" -> !trip.isUpcoming
            "Upcoming" -> trip.isUpcoming
            "Family Care" -> trip.isFamilyCare
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
        // 1. User Profile Header (Image 13)
        Surface(
            color = DriveeNavy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = MockData.USER_AVATAR_URL,
                        contentDescription = "Anshul Mehta",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(2.dp, DriveeOrange, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Anshul Mehta",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = DriveeOrange,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "GOLD",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "+91 98201 44521 • Bandra West, Mumbai",
                            fontSize = 11.5.sp,
                            color = DriveeSurfaceHigh
                        )
                        Text(
                            text = "Primary: Honda City Petrol Manual (MH 02 FJ 4410)",
                            fontSize = 10.5.sp,
                            color = DriveeTextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Bento Metrics Grid (Image 13)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricBentoCard(
                value = "38.5",
                unit = "Hrs",
                label = "Chauffeur Hours",
                modifier = Modifier.weight(1f)
            )
            MetricBentoCard(
                value = "₹5,736",
                unit = "",
                label = "Total Spend (8 GST)",
                modifier = Modifier.weight(1f)
            )
            MetricBentoCard(
                value = "4.95",
                unit = "★",
                label = "Avg Rating Given",
                modifier = Modifier.weight(1f)
            )
            MetricBentoCard(
                value = "₹50L",
                unit = "",
                label = "Vehicle Shield",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Filter Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterTabs.forEach { tab ->
                val isSelected = selectedFilterTab == tab
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) DriveeNavy else DriveeSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) DriveeNavy else DriveeCardBorder
                    ),
                    modifier = Modifier.clickable { selectedFilterTab = tab }
                ) {
                    Text(
                        text = tab,
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else DriveeTextPrimary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Trips History Cards List (Image 13)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            displayedTrips.forEach { trip ->
                TripHistoryCard(
                    trip = trip,
                    onViewInvoice = { viewingInvoiceTrip = trip },
                    onRebook = { onRebookTrip(trip) }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 5. Billing & Tax Center (Image 13)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "BILLING & TAX INVOICE CENTER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Registered GSTIN: 27AABCT3518Q1Z4 (Mehta Global Advisory LLP). All rides generate instant Rule 46 Tax Invoices eligible for Input Tax Credit (ITC).",
                    fontSize = 11.5.sp,
                    color = DriveeTextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Downloading FY2024-25 Invoices ZIP bundle...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Download All (.ZIP)", fontSize = 10.5.sp)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Emailing YTD summary statement...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Email YTD Sheet", fontSize = 10.5.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 6. Registered Cars Garage
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REGISTERED GARAGE (2 CARS)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Opening car registration form...", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = DriveeOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Add Car", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DriveeOrange)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                MockData.registeredCars.forEach { car ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = DriveeNavy, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = car.model, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DriveeNavy)
                            Text(text = "${car.plateNumber} • ${car.transmission.name}", fontSize = 10.5.sp, color = DriveeTextMuted)
                        }
                        Surface(
                            color = DriveeSurfaceLow,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "VERIFIED RC",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = DriveeNavy,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Full GST Tax Invoice Preview Modal
    viewingInvoiceTrip?.let { trip ->
        TaxInvoiceDialog(
            trip = trip,
            onDismiss = { viewingInvoiceTrip = null }
        )
    }
}

@Composable
fun MetricBentoCard(
    value: String,
    unit: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DriveeSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = DriveeNavy
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeOrange,
                        modifier = Modifier.padding(start = 1.dp, bottom = 1.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 8.5.sp,
                color = DriveeTextMuted,
                maxLines = 1
            )
        }
    }
}

@Composable
fun TripHistoryCard(
    trip: TripRecord,
    onViewInvoice: () -> Unit,
    onRebook: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DriveeSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Status & Booking ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = trip.bookingId,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = trip.dateStr,
                        fontSize = 11.sp,
                        color = DriveeTextMuted
                    )
                }

                Surface(
                    color = if (trip.isUpcoming) DriveeAmber.copy(alpha = 0.15f) else DriveeGreenBg,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = trip.status,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = if (trip.isUpcoming) DriveeAmber else DriveeGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Route & Purpose
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = DriveeOrange, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = trip.pickupToDrop,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                    Text(
                        text = "${trip.purpose} • ${trip.vehicleInfo}",
                        fontSize = 11.sp,
                        color = DriveeTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Driver & Fare Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DriveeSurfaceLow, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = trip.driverPhoto,
                        contentDescription = trip.driverName,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = trip.driverName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Text(
                            text = trip.durationText,
                            fontSize = 10.sp,
                            color = DriveeTextMuted
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "₹${trip.totalFare}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeOrange
                    )
                    Text(
                        text = trip.paymentMethod,
                        fontSize = 9.5.sp,
                        color = DriveeTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (trip.isUpcoming) {
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Calling driver ${trip.driverName}...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call Driver", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Opening itinerary details...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("View Itinerary", fontSize = 11.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = onViewInvoice,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Receipt, contentDescription = null, tint = DriveeNavy, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tax Invoice", fontSize = 11.sp, color = DriveeNavy)
                    }

                    Button(
                        onClick = onRebook,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Rebook Driver", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
