package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
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
import com.example.ui.theme.DriveeBackground
import com.example.ui.theme.DriveeCardBorder
import com.example.ui.theme.DriveeGreen
import com.example.ui.theme.DriveeGreenBg
import com.example.ui.theme.DriveeNavy
import com.example.ui.theme.DriveeNavyDark
import com.example.ui.theme.DriveeOrange
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeSurfaceLow
import com.example.ui.theme.DriveeTextMuted
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary

@Composable
fun FamilyModeScreen(
    onBookFamilyDriver: (relation: String) -> Unit
) {
    val context = LocalContext.current
    var selectedMemberIndex by remember { mutableStateOf(0) }
    val member = MockData.familyProfiles[selectedMemberIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DriveeBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Top Header
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
                        imageVector = Icons.Default.FamilyRestroom,
                        contentDescription = null,
                        tint = DriveeOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "FAMILY MODE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeOrange,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Chauffeur care for your loved ones in your car",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Real-time WhatsApp GPS broadcasts, elderly assistance, and hospital basement parking escort.",
                    fontSize = 12.sp,
                    color = DriveeSurfaceLow
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Family Switcher Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MockData.familyProfiles.forEachIndexed { index, profile ->
                val isSelected = selectedMemberIndex == index
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) DriveeOrange else DriveeSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) DriveeOrange else DriveeCardBorder
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedMemberIndex = index }
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = profile.relation,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else DriveeNavy
                        )
                        Text(
                            text = "${profile.age} yrs",
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else DriveeTextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Member Details Card
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
                    Column {
                        Text(
                            text = member.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Text(
                            text = "${member.relation} • Frequent Passenger",
                            fontSize = 11.5.sp,
                            color = DriveeTextSecondary
                        )
                    }
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "SENIOR CARE CERTIFIED",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Destination & Purpose
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = DriveeOrange, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(text = "Saved Regular Route:", fontSize = 10.5.sp, color = DriveeTextMuted)
                        Text(text = member.destination, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = DriveeNavy)
                        Text(text = member.purpose, fontSize = 11.sp, color = DriveeTextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pre-assigned Car
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = DriveeNavy, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Designated Car: Hyundai Creta SX AT (MH 02 BG 4410)",
                        fontSize = 11.5.sp,
                        color = DriveeTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onBookFamilyDriver(member.relation)
                        Toast.makeText(context, "Dispatching verified chauffeur for ${member.name}...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Book Driver for ${member.name}", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Active Family Care Trip Showcase
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LAST FAMILY MISSION #DRV-6810",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Black,
                        color = DriveeNavy
                    )
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "COMPLETED 5★",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Mohammad Imran piloted Creta AT for Dad's dialysis appointment at Lilavati Hospital.",
                    fontSize = 12.sp,
                    color = DriveeTextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Special Driver Notes executed: \"Driver parked inside hospital basement, assisted Dad to wheelchair lobby, and waited until son arrived.\"",
                    fontSize = 11.sp,
                    color = DriveeTextSecondary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Live GPS link shared to Family WhatsApp Group", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("WhatsApp Link", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Rebooking Mohammad Imran for next appointment...", Toast.LENGTH_SHORT).show()
                            onBookFamilyDriver("Dad")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Rebook Imran", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
