package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AppScreen
import com.example.model.MockData
import com.example.model.TransmissionType
import com.example.ui.theme.DriveeAmber
import com.example.ui.theme.DriveeAmberBg
import com.example.ui.theme.DriveeBackground
import com.example.ui.theme.DriveeCardBorder
import com.example.ui.theme.DriveeGreen
import com.example.ui.theme.DriveeGreenBg
import com.example.ui.theme.DriveeNavy
import com.example.ui.theme.DriveeNavyDark
import com.example.ui.theme.DriveeNavyLight
import com.example.ui.theme.DriveeOrange
import com.example.ui.theme.DriveeOrangeContainer
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeSurfaceHigh
import com.example.ui.theme.DriveeSurfaceLow
import com.example.ui.theme.DriveeTextMuted
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigateToDrivers: (durationHours: Int, transmission: TransmissionType, location: String) -> Unit,
    onNavigateToScreen: (AppScreen) -> Unit
) {
    val context = LocalContext.current
    var pickupLocation by remember { mutableStateOf("Bandra West, Mumbai") }
    var selectedTransmission by remember { mutableStateOf(TransmissionType.MANUAL) }
    var selectedDurationHours by remember { mutableIntStateOf(4) }
    var selectedTiming by remember { mutableStateOf("Instant (In 15 mins)") }

    val durationOptions = listOf(
        Pair(2, 298),
        Pair(3, 447),
        Pair(4, 596),
        Pair(6, 894),
        Pair(8, 1192)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriveeBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // 1. Not a taxi banner
        Surface(
            color = DriveeNavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = DriveeOrange,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "NOT A TAXI",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "We drive YOUR personal car • ₹50L Shield",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        // 2. Hero Headline
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Your car. Your trip.\nWe provide the driver.",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = DriveeNavy,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Book screened, police-verified professional chauffeurs by the hour to pilot your personal vehicle. Instant dispatch in Mumbai.",
                fontSize = 13.sp,
                color = DriveeTextSecondary,
                lineHeight = 18.sp
            )
        }

        // 3. Trust badges row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TrustBadgeItem(
                icon = Icons.Default.Bolt,
                title = "₹149/hr",
                subtitle = "Flat Starting",
                modifier = Modifier.weight(1f)
            )
            TrustBadgeItem(
                icon = Icons.Default.Security,
                title = "₹50L Insured",
                subtitle = "ICICI Lombard",
                modifier = Modifier.weight(1f)
            )
            TrustBadgeItem(
                icon = Icons.Default.Videocam,
                title = "Dashcam",
                subtitle = "Monitored",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 4. Quick Hourly Booking Card (Image 4 & 11)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BOOK YOUR CHAUFFEUR",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy,
                        letterSpacing = 0.5.sp
                    )
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DriveeGreen)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "3 Drivers Ready",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = DriveeGreen
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Location selector
                Text(
                    text = "PICKUP LOCATION IN MUMBAI",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeTextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = DriveeOrange)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DriveeOrange,
                        unfocusedBorderColor = DriveeCardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Transmission Selector
                Text(
                    text = "TRANSMISSION TYPE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeTextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TransmissionButton(
                        label = "Manual Car",
                        subtitle = "Standard clutch",
                        isSelected = selectedTransmission == TransmissionType.MANUAL,
                        onClick = { selectedTransmission = TransmissionType.MANUAL },
                        modifier = Modifier.weight(1f)
                    )
                    TransmissionButton(
                        label = "Automatic Car",
                        subtitle = "AT / DCT / CVT / EV",
                        isSelected = selectedTransmission == TransmissionType.AUTOMATIC,
                        onClick = { selectedTransmission = TransmissionType.AUTOMATIC },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Timing Selector
                Text(
                    text = "START TIME",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeTextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val timings = listOf("Instant (In 15 mins)", "Today 8:00 PM", "Tomorrow Morning")
                    timings.forEach { timing ->
                        val isSelected = selectedTiming == timing
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) DriveeSurfaceHigh else DriveeSurfaceLow,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) DriveeNavy else DriveeCardBorder
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTiming = timing }
                        ) {
                            Text(
                                text = timing,
                                fontSize = 10.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) DriveeNavy else DriveeTextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Duration selector (Chips)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SELECT DURATION (HOURS)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeTextMuted
                    )
                    Text(
                        text = "₹149/hr base",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeOrange
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    durationOptions.forEach { (hours, price) ->
                        val isSelected = selectedDurationHours == hours
                        val isPopular = hours == 4
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) DriveeOrange else DriveeSurfaceLow,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) DriveeOrange else DriveeCardBorder
                            ),
                            modifier = Modifier.clickable { selectedDurationHours = hours }
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (isPopular) {
                                    Text(
                                        text = "POPULAR",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isSelected) Color.White else DriveeOrange
                                    )
                                }
                                Text(
                                    text = "$hours Hours",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else DriveeTextPrimary
                                )
                                Text(
                                    text = "₹$price",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSelected) Color.White.copy(alpha = 0.9f) else DriveeTextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Find Available Drivers Button
                Button(
                    onClick = {
                        onNavigateToDrivers(selectedDurationHours, selectedTransmission, pickupLocation)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Find Available Drivers ($selectedDurationHours hrs • ₹${selectedDurationHours * 149})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Real-time availability pulse card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DriveeNavy,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    onNavigateToDrivers(selectedDurationHours, selectedTransmission, pickupLocation)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Overlapping avatars
                    Box(modifier = Modifier.width(66.dp)) {
                        AsyncImage(
                            model = MockData.DRIVER_RAJESH_URL,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = MockData.DRIVER_SURESH_URL,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 18.dp)
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model = MockData.DRIVER_IMRAN_URL,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 36.dp)
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Chauffeurs ready near you",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "⚡ 12-15 min fastest arrival in Bandra",
                            fontSize = 10.sp,
                            color = DriveeOrangeContainer
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 6. Popular Use Cases
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "POPULAR USE CASES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DriveeOrange,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "When to hire a personal driver",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DriveeNavy
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MockData.useCases.forEach { useCase ->
                UseCaseCard(useCase = useCase)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 7. How DRIVEE Works (4 Steps)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DriveeSurfaceLow)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "SIMPLE 4-STEP PROCESS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DriveeOrange,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "How DRIVEE Works",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DriveeNavy
            )
            Spacer(modifier = Modifier.height(14.dp))

            val steps = listOf(
                Pair("01", Pair("Set Location & Hours", "Choose your pickup spot in Mumbai and select the hours you need the driver.")),
                Pair("02", Pair("Pick Screened Chauffeur", "Review driver ratings, background clearance, and vehicle transmission experience.")),
                Pair("03", Pair("Hand Over Your Keys", "Driver arrives at your doorstep, completes vehicle walkaround check, and takes the wheel.")),
                Pair("04", Pair("Pay Flat Hourly Rate", "Relax in your own car. Extend with 1 tap or pay automatically with GST invoice."))
            )

            steps.forEach { (number, stepData) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(
                        shape = CircleShape,
                        color = DriveeNavy,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = number,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = stepData.first,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stepData.second,
                            fontSize = 12.sp,
                            color = DriveeTextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 8. The DRIVEE Promise Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeNavyDark)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = DriveeOrange,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "\"You invested in your personal car for its comfort, safety, and hygiene. Why settle for random cabs or surge fares when you can have a dedicated white-glove chauffeur at a fraction of the cost?\"",
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "— THE DRIVEE PHILOSOPHY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeOrange,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 9. Trusted By Car Owners Testimonials
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "COMMUNITY REVIEWS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = DriveeOrange,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Trusted by 10,000+ Car Owners",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DriveeNavy
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MockData.testimonials.forEach { testimonial ->
                TestimonialCard(testimonial = testimonial)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 10. DRIVEE 5-minute Guarantee Banner
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = DriveeGreenBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeGreen.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = DriveeGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "5-Minute Punctuality Guarantee",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeGreen
                    )
                    Text(
                        text = "If your driver arrives later than the confirmed ETA, your first hour is on us.",
                        fontSize = 11.sp,
                        color = DriveeTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun TrustBadgeItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DriveeSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = DriveeOrange,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DriveeNavy
            )
            Text(
                text = subtitle,
                fontSize = 9.5.sp,
                color = DriveeTextMuted
            )
        }
    }
}

@Composable
fun TransmissionButton(
    label: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) DriveeNavy else DriveeSurfaceLow,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) DriveeNavy else DriveeCardBorder
        ),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else DriveeTextPrimary
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = if (isSelected) Color.White.copy(alpha = 0.8f) else DriveeTextMuted
            )
        }
    }
}

@Composable
fun UseCaseCard(useCase: com.example.model.UseCaseItem) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = DriveeSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            val icon = when (useCase.iconName) {
                "nightlife" -> Icons.Default.Nightlife
                "local_hospital" -> Icons.Default.LocalHospital
                "flight_takeoff" -> Icons.Default.FlightTakeoff
                "terrain" -> Icons.Default.Terrain
                "shopping_bag" -> Icons.Default.ShoppingBag
                else -> Icons.Default.Work
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DriveeSurfaceLow,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = useCase.title,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                    Surface(
                        color = DriveeOrange.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = useCase.tag,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeOrange,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = useCase.description,
                    fontSize = 11.5.sp,
                    color = DriveeTextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun TestimonialCard(testimonial: com.example.model.Testimonial) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = DriveeSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
        modifier = Modifier.width(280.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = testimonial.photoUrl,
                    contentDescription = testimonial.name,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = testimonial.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                    Text(
                        text = "${testimonial.location} • ${testimonial.carModel}",
                        fontSize = 10.sp,
                        color = DriveeTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                repeat(testimonial.rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = DriveeAmber,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"${testimonial.review}\"",
                fontSize = 11.5.sp,
                color = DriveeTextSecondary,
                lineHeight = 16.sp
            )
        }
    }
}
