package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AppScreen
import com.example.model.MockData
import com.example.ui.theme.DriveeBackground
import com.example.ui.theme.DriveeCardBorder
import com.example.ui.theme.DriveeGreen
import com.example.ui.theme.DriveeNavy
import com.example.ui.theme.DriveeOrange
import com.example.ui.theme.DriveeRed
import com.example.ui.theme.DriveeRedBg
import com.example.ui.theme.DriveeSurface
import com.example.ui.theme.DriveeSurfaceLow
import com.example.ui.theme.DriveeTextMuted
import com.example.ui.theme.DriveeTextPrimary
import com.example.ui.theme.DriveeTextSecondary

@Composable
fun DriveeTopBar(
    currentLocation: String,
    onLocationClick: () -> Unit,
    onSosClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = DriveeSurface,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand Logo & Wordmark
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onProfileClick() }
            ) {
                AsyncImage(
                    model = MockData.LOGO_URL,
                    contentDescription = "DRIVEE Logo",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "DRIVEE",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = DriveeNavy,
                            letterSpacing = 0.5.sp
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = 3.dp)
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(DriveeOrange)
                        )
                    }
                    Text(
                        text = "YOUR CAR • YOUR DRIVER",
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = DriveeTextMuted,
                        letterSpacing = 0.6.sp
                    )
                }
            }

            // Location Selector Pill
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = DriveeSurfaceLow,
                border = androidx.compose.foundation.BorderStroke(1.dp, DriveeCardBorder),
                modifier = Modifier.clickable { onLocationClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = DriveeOrange,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = currentLocation,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DriveeTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Actions: SOS Button + User Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // SOS Safety Quick Trigger
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = DriveeRedBg,
                    modifier = Modifier.clickable { onSosClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = "Emergency SOS",
                            tint = DriveeRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "SOS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DriveeRed
                        )
                    }
                }

                // Profile Avatar
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, DriveeOrange, CircleShape)
                        .clickable { onProfileClick() }
                ) {
                    AsyncImage(
                        model = MockData.USER_AVATAR_URL,
                        contentDescription = "User Profile",
                        modifier = Modifier.size(34.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun DriveeBottomBar(
    currentScreen: AppScreen,
    onScreenSelect: (AppScreen) -> Unit,
    hasActiveTrip: Boolean = true
) {
    NavigationBar(
        containerColor = DriveeSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.border(
            width = 1.dp,
            color = DriveeCardBorder,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
    ) {
        val items = listOf(
            NavigationTabItem(
                screen = AppScreen.HOME,
                label = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            NavigationTabItem(
                screen = AppScreen.DRIVERS,
                label = "Drivers",
                selectedIcon = Icons.Filled.NearMe,
                unselectedIcon = Icons.Outlined.NearMe
            ),
            NavigationTabItem(
                screen = AppScreen.LIVE_TRACKING,
                label = "Live Trip",
                selectedIcon = Icons.Filled.Speed,
                unselectedIcon = Icons.Filled.Speed,
                hasBadge = hasActiveTrip
            ),
            NavigationTabItem(
                screen = AppScreen.FAMILY,
                label = "Family",
                selectedIcon = Icons.Filled.FamilyRestroom,
                unselectedIcon = Icons.Outlined.FamilyRestroom
            ),
            NavigationTabItem(
                screen = AppScreen.ACCOUNT,
                label = "Billing",
                selectedIcon = Icons.Filled.ReceiptLong,
                unselectedIcon = Icons.Outlined.ReceiptLong
            )
        )

        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onScreenSelect(item.screen) },
                icon = {
                    if (item.hasBadge) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = DriveeGreen,
                                    modifier = Modifier.size(7.dp)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DriveeOrange,
                    selectedTextColor = DriveeOrange,
                    unselectedIconColor = DriveeTextMuted,
                    unselectedTextColor = DriveeTextMuted,
                    indicatorColor = DriveeOrange.copy(alpha = 0.12f)
                )
            )
        }
    }
}

data class NavigationTabItem(
    val screen: AppScreen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasBadge: Boolean = false
)
