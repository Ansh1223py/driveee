package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.ui.theme.DriveeRed
import com.example.ui.theme.DriveeRedBg
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeSurfaceLow
import com.example.ui.theme.DriveeTextMuted
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary

@Composable
fun LiveTrackingScreen(
    chauffeur: Chauffeur = MockData.chauffeurs[0],
    initialHours: Int = 4,
    onSosClick: () -> Unit,
    onTripCompleted: () -> Unit
) {
    val context = LocalContext.current
    var extendedHours by remember { mutableIntStateOf(0) }
    var currentSpeed by remember { mutableIntStateOf(42) }
    var showRatingModal by remember { mutableStateOf(false) }

    val basePrice = initialHours * 149
    val extensionPrice = extendedHours * 149
    val totalFare = basePrice + extensionPrice

    val totalBookedHours = initialHours + extendedHours

    // Pulsing animation for active GPS marker
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 16f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseRadius"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriveeBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // 1. Live Status Header
        Surface(
            color = DriveeNavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(DriveeGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "TRIP IN PROGRESS • #DRV-8824",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Black,
                            color = DriveeGreen,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Surface(
                        color = DriveeOrange,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "OTP VERIFIED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Bandra West → Worli Sea Link",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Time counters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Elapsed Time", fontSize = 10.sp, color = DriveeSurfaceLow)
                            Text(text = "1h 18m", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Remaining Time", fontSize = 10.sp, color = DriveeSurfaceLow)
                            val remainingMins = (totalBookedHours * 60) - 78
                            val remH = remainingMins / 60
                            val remM = remainingMins % 60
                            Text(text = "${remH}h ${remM}m", fontSize = 14.sp, fontWeight = FontWeight.Black, color = DriveeOrange)
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Booked", fontSize = 10.sp, color = DriveeSurfaceLow)
                            Text(text = "$totalBookedHours Hours", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
                        }
                    }
                }
            }
        }

        // 2. Simulated Live GPS Map Canvas (Image 8)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(Color(0xFF1E293B))
        ) {
            // Simulated Map Canvas with road curves, vehicle marker, and route
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // Background grid lines (streets)
                drawLine(
                    color = Color(0xFF334155),
                    start = Offset(0f, height * 0.35f),
                    end = Offset(width, height * 0.35f),
                    strokeWidth = 10f
                )
                drawLine(
                    color = Color(0xFF334155),
                    start = Offset(0f, height * 0.7f),
                    end = Offset(width, height * 0.7f),
                    strokeWidth = 14f
                )
                drawLine(
                    color = Color(0xFF334155),
                    start = Offset(width * 0.3f, 0f),
                    end = Offset(width * 0.3f, height),
                    strokeWidth = 12f
                )
                drawLine(
                    color = Color(0xFF334155),
                    start = Offset(width * 0.75f, 0f),
                    end = Offset(width * 0.75f, height),
                    strokeWidth = 8f
                )

                // Active GPS Travel Path (Orange glowing route)
                val routePath = Path().apply {
                    moveTo(width * 0.15f, height * 0.85f)
                    cubicTo(
                        width * 0.3f, height * 0.7f,
                        width * 0.45f, height * 0.5f,
                        width * 0.55f, height * 0.45f
                    )
                    cubicTo(
                        width * 0.65f, height * 0.4f,
                        width * 0.75f, height * 0.35f,
                        width * 0.85f, height * 0.25f
                    )
                }

                // Path underlay glow
                drawPath(
                    path = routePath,
                    color = Color(0xFFFD651E).copy(alpha = 0.35f),
                    style = Stroke(width = 16f, cap = StrokeCap.Round)
                )
                // Path main line
                drawPath(
                    path = routePath,
                    color = Color(0xFFFD651E),
                    style = Stroke(width = 7f, cap = StrokeCap.Round)
                )

                // Current Car position (Center on route)
                val carCenter = Offset(width * 0.55f, height * 0.45f)

                // Pulsing radar circle
                drawCircle(
                    color = Color(0xFFFD651E).copy(alpha = 0.25f),
                    radius = pulseRadius * 2,
                    center = carCenter
                )
                // Outer ring
                drawCircle(
                    color = Color.White,
                    radius = 14f,
                    center = carCenter
                )
                // Car center dot
                drawCircle(
                    color = Color(0xFF0B1C30),
                    radius = 10f,
                    center = carCenter
                )
            }

            // Overlay 1: Live Speedometer
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DriveeNavyDark.copy(alpha = 0.9f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "$currentSpeed km/h",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "• Live GPS",
                        fontSize = 10.sp,
                        color = DriveeGreen
                    )
                }
            }

            // Overlay 2: Toll Plaza ETA Badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DriveeNavyDark.copy(alpha = 0.9f),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = "Sea Link Toll: 18 min",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                )
            }

            // Overlay 3: Map Controls
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = DriveeNavyDark,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            Toast.makeText(context, "Traffic layer refreshed (Moderate flow)", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Layers, contentDescription = "Layers", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = DriveeOrange,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            Toast.makeText(context, "Recentered to vehicle location", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.MyLocation, contentDescription = "Recenter", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Assigned Chauffeur Card (Image 8)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = chauffeur.photoUrl,
                        contentDescription = chauffeur.name,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .border(2.dp, DriveeOrange, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = chauffeur.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = DriveeNavy
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                        }
                        Text(
                            text = "Assigned Personal Chauffeur",
                            fontSize = 11.sp,
                            color = DriveeTextSecondary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = DriveeAmber, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${chauffeur.rating} • ${chauffeur.tripsCount} trips • Luxury Trained",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DriveeTextMuted
                            )
                        }
                    }

                    // Call & Chat buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            shape = CircleShape,
                            color = DriveeGreenBg,
                            modifier = Modifier
                                .size(38.dp)
                                .clickable {
                                    Toast.makeText(context, "Connecting call to chauffeur ${chauffeur.name}...", Toast.LENGTH_SHORT).show()
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Call, contentDescription = "Call", tint = DriveeGreen, modifier = Modifier.size(18.dp))
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = DriveeSurfaceLow,
                            modifier = Modifier
                                .size(38.dp)
                                .clickable {
                                    Toast.makeText(context, "Opening in-app chat with driver...", Toast.LENGTH_SHORT).show()
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Chat, contentDescription = "Chat", tint = DriveeNavy, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Vehicle being driven
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Piloting: Honda City i-VTEC (MH 02 CZ 4410)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Text(
                            text = "Manual Clutch • In-car Dashcam Active",
                            fontSize = 10.5.sp,
                            color = DriveeTextMuted
                        )
                    }
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "PROTECTED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. 1-Tap Booking Extension (Image 8)
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MoreTime,
                            contentDescription = null,
                            tint = DriveeOrange,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "EXTEND YOUR BOOKING",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = DriveeNavy
                        )
                    }
                    if (extendedHours > 0) {
                        Text(
                            text = "+$extendedHours hrs added",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Plans changed? Extend in 1 tap without interrupting your driver.",
                    fontSize = 11.sp,
                    color = DriveeTextSecondary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            extendedHours += 1
                            Toast.makeText(context, "Added +1 Hour! Driver notified.", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeSurfaceLow),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "+1 Hour (₹149)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DriveeNavy)
                    }

                    Button(
                        onClick = {
                            extendedHours += 2
                            Toast.makeText(context, "Added +2 Hours! Driver notified.", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "+2 Hours (₹298)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5. Quick Actions: WhatsApp Share + Emergency SOS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    Toast.makeText(context, "Live tracking link copied & WhatsApp opened!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Share via WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DriveeNavy)
            }

            Button(
                onClick = onSosClick,
                colors = ButtonDefaults.buttonColors(containerColor = DriveeRed),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Emergency, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("24x7 Safety SOS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 6. Live Fare Meter (Image 8)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurfaceLow),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "LIVE FARE BREAKDOWN", fontSize = 11.sp, fontWeight = FontWeight.Black, color = DriveeNavy)
                    Text(text = "GST Compliant", fontSize = 10.sp, color = DriveeTextMuted)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Base Booking ($initialHours hours):", fontSize = 11.sp, color = DriveeTextSecondary)
                    Text(text = "₹$basePrice.00", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                if (extendedHours > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Extension ($extendedHours hours):", fontSize = 11.sp, color = DriveeTextSecondary)
                        Text(text = "+₹$extensionPrice.00", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DriveeGreen)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "₹50L Insurance Shield:", fontSize = 11.sp, color = DriveeTextSecondary)
                    Text(text = "Included (₹0)", fontSize = 11.sp, color = DriveeGreen)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Estimated Amount:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                    Text(
                        text = "₹$totalFare.00",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeOrange
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 7. End Trip & Review Button
        Button(
            onClick = { showRatingModal = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "End Trip & Complete Booking", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }

    // Rating / Completion Dialog
    if (showRatingModal) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { showRatingModal = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DriveeSurface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "RATE YOUR CHAUFFEUR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "How was your drive with ${chauffeur.name} in your Honda City?",
                        fontSize = 11.5.sp,
                        color = DriveeTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = DriveeOrange,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            showRatingModal = false
                            Toast.makeText(context, "Trip completed! GST invoice generated.", Toast.LENGTH_SHORT).show()
                            onTripCompleted()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Submit Rating & View Invoice", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
