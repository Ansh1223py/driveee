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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.model.Chauffeur
import com.example.model.TripRecord
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
fun TaxInvoiceDialog(
    trip: TripRecord,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header with close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TAX INVOICE",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = DriveeNavy,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Issued under Rule 46 of CGST Rules, 2017",
                            fontSize = 10.sp,
                            color = DriveeTextMuted
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Company Lockup
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DRIVEE MOBILITY SERVICES PVT LTD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeNavy
                        )
                        Text(
                            text = "Level 4, Godrej One, BKC, Mumbai - 400051",
                            fontSize = 10.sp,
                            color = DriveeTextSecondary
                        )
                        Text(
                            text = "GSTIN: 27AAACD9182K1ZW • SAC: 9966",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DriveeOrange
                        )
                        Text(
                            text = "CIN: U63090MH2023PTC412091",
                            fontSize = 9.sp,
                            color = DriveeTextMuted
                        )
                    }
                    Surface(
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DriveeGreen)
                    ) {
                        Text(
                            text = "PAID IN FULL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DriveeGreen,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Billed To Section
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DriveeSurfaceLow,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "BILLED TO (REGISTERED B2B / CONSUMER):",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeTextMuted
                        )
                        Text(
                            text = "Mehta Global Advisory LLP",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DriveeTextPrimary
                        )
                        Text(
                            text = "Attn: Anshul Mehta • +91 98201 44521",
                            fontSize = 11.sp,
                            color = DriveeTextSecondary
                        )
                        Text(
                            text = "GSTIN: 27AABCT3518Q1Z4 (Eligible for Input Tax Credit)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DriveeNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Invoice metadata
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Invoice No: ${trip.bookingId}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Date: ${trip.dateStr}", fontSize = 10.sp, color = DriveeTextSecondary)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Place of Supply: Maharashtra (27)", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Payment: ${trip.paymentMethod}", fontSize = 10.sp, color = DriveeTextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Table Items
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Table header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DriveeNavy)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Description", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "HSN/SAC", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Total (INR)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        // Table row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "On-Demand Personal Chauffeur",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${trip.durationText} • ${trip.vehicleInfo}",
                                    fontSize = 9.5.sp,
                                    color = DriveeTextMuted
                                )
                                Text(
                                    text = "Chauffeur: ${trip.driverName}",
                                    fontSize = 9.5.sp,
                                    color = DriveeTextSecondary
                                )
                            }
                            Text(text = "9966", fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp))
                            val taxable = String.format("%.2f", trip.totalFare / 1.18)
                            Text(
                                text = "₹$taxable",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tax breakdown
                val taxableVal = trip.totalFare / 1.18
                val gstVal = (trip.totalFare - taxableVal) / 2
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Taxable Value (Chauffeur Services):", fontSize = 11.sp, color = DriveeTextSecondary)
                        Text(text = "₹${String.format("%.2f", taxableVal)}", fontSize = 11.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "CGST (Central Tax @ 9%):", fontSize = 11.sp, color = DriveeTextSecondary)
                        Text(text = "₹${String.format("%.2f", gstVal)}", fontSize = 11.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "SGST (Maharashtra State Tax @ 9%):", fontSize = 11.sp, color = DriveeTextSecondary)
                        Text(text = "₹${String.format("%.2f", gstVal)}", fontSize = 11.sp)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TOTAL AMOUNT (INCL. GST):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DriveeNavy
                        )
                        Text(
                            text = "₹${trip.totalFare}.00",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = DriveeOrange
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            Toast.makeText(context, "Invoice #${trip.bookingId} PDF downloaded to device", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Download PDF", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Sent official GST invoice to anshul.mehta@global.in", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Email ITC Copy", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DriverProfileDialog(
    chauffeur: Chauffeur,
    onBookNow: (Chauffeur) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header with close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CHAUFFEUR PROFILE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeTextMuted,
                        letterSpacing = 1.sp
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Driver Avatar & Name
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = chauffeur.photoUrl,
                        contentDescription = chauffeur.name,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .border(2.dp, DriveeOrange, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = chauffeur.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DriveeNavy
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = "Verified",
                                tint = DriveeGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "${chauffeur.age} yrs • ${chauffeur.experienceYears}+ years experience",
                            fontSize = 12.sp,
                            color = DriveeTextSecondary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = DriveeOrange,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${chauffeur.rating} (${chauffeur.tripsCount} trips)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DriveeTextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Verified Badges Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        color = DriveeGreenBg,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.LocalPolice, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Police Cleared", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DriveeGreen)
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        color = DriveeSurfaceLow,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = DriveeNavy, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("₹50L Insured", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DriveeNavy)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Bio
                Text(
                    text = "ABOUT THE CHAUFFEUR",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeTextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chauffeur.bio,
                    fontSize = 12.5.sp,
                    color = DriveeTextPrimary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Competencies
                Text(
                    text = "VEHICLE EXPERTISE & SKILLS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DriveeTextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(chauffeur.specialties, fontSize = 12.sp, color = DriveeTextSecondary)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Languages: ${chauffeur.languages}", fontSize = 12.sp, color = DriveeTextSecondary)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = DriveeGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Chauffeur Etiquette & VIP Hospitality Certified", fontSize = 12.sp, color = DriveeTextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Rate & Action Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "₹${chauffeur.ratePerHour}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = DriveeNavy
                            )
                            Text(
                                text = "/hr",
                                fontSize = 13.sp,
                                color = DriveeTextMuted,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                        Text(
                            text = "⚡ Arrives in ${chauffeur.etaMins} mins",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DriveeGreen
                        )
                    }

                    Button(
                        onClick = {
                            onBookNow(chauffeur)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DriveeOrange),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text(text = "Book ${chauffeur.name.split(" ")[0]}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencySosDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DriveeSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(DriveeRedBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = "Emergency",
                        tint = DriveeRed,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "24x7 SAFETY DISPATCH",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = DriveeNavy
                )
                Text(
                    text = "Instant response team is ready to assist you or contact authorities with your car's live telemetry.",
                    fontSize = 12.sp,
                    color = DriveeTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Call Button
                Button(
                    onClick = {
                        Toast.makeText(context, "Calling DRIVEE 24x7 Safety Command Center...", Toast.LENGTH_LONG).show()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DriveeRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call Safety Helpline (1800-DRIVEE)", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Emergency contacts alerted with live GPS coordinates.", Toast.LENGTH_LONG).show()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = DriveeTextPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Broadcast GPS to Emergency Contacts", color = DriveeTextPrimary, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "False alarm? Dismiss",
                    fontSize = 12.sp,
                    color = DriveeTextMuted,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
        }
    }
}
