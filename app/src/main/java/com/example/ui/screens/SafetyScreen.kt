package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun SafetyScreen(
    onTriggerSos: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriveeBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Header
        Surface(
            color = DriveeNavy,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SAFETY ARCHITECTURE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeOrange,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Uncompromising Vetting & Vehicle Shield",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Only 8% of chauffeur applicants pass our stringent multi-layer background, driving, and etiquette criteria.",
                    fontSize = 12.sp,
                    color = DriveeSurfaceLow
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SOS Panic Button Callout
        Surface(
            color = DriveeRedBg,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DriveeRed.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Emergency, contentDescription = null, tint = DriveeRed, modifier = Modifier.size(30.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = "24x7 Emergency SOS", fontSize = 13.5.sp, fontWeight = FontWeight.Black, color = DriveeRed)
                        Text(text = "Instant response desk & GPS dispatch", fontSize = 11.sp, color = DriveeTextSecondary)
                    }
                }
                Button(
                    onClick = onTriggerSos,
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Trigger SOS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4 Verification Pillars
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SafetyPillarCard(
                icon = Icons.Default.LocalPolice,
                title = "1. Police Criminal Verification",
                subtitle = "Court records, criminal checks, and residential address physical verification via local police station clearance."
            )
            SafetyPillarCard(
                icon = Icons.Default.AssignmentTurnedIn,
                title = "2. Practical 15-Point Driving Test",
                subtitle = "Assessed on manual clutches, hill starts, bumper-to-bumper Mumbai traffic navigation, and EV regenerative braking."
            )
            SafetyPillarCard(
                icon = Icons.Default.Verified,
                title = "3. Chauffeur Etiquette & VIP Protocol",
                subtitle = "Trained in passenger privacy, quiet rides, white-glove door assistance, and luggage handling."
            )
            SafetyPillarCard(
                icon = Icons.Default.Videocam,
                title = "4. GPS Telemetry & Dashcam Feed",
                subtitle = "Live trip speeds, route deviations, and emergency braking monitored in real time by our automated ops center."
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ₹50 Lakh Insurance Certificate
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
                    Icon(Icons.Default.Security, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ICICI Lombard Protection Certificate",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeNavy
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Master Policy No: DRV-POL-9921 • Covers up to ₹50,00,000 for accidental vehicle repair, third-party bodily injury, and 24x7 roadside flatbed towing.",
                    fontSize = 11.5.sp,
                    color = DriveeTextSecondary,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "Policy certificate PDF saved to Downloads.", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Download Master Policy Document", fontSize = 11.5.sp)
                }
            }
        }
    }
}

@Composable
fun SafetyPillarCard(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
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
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DriveeGreenBg,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DriveeNavy)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 11.5.sp, color = DriveeTextSecondary, lineHeight = 16.sp)
            }
        }
    }
}
