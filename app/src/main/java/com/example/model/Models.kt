package com.example.model

enum class AppScreen {
    HOME,
    DRIVERS,
    LIVE_TRACKING,
    FAMILY,
    SAFETY,
    ACCOUNT
}

enum class TransmissionType {
    MANUAL,
    AUTOMATIC
}

data class Chauffeur(
    val id: String,
    val name: String,
    val age: Int,
    val rating: Double,
    val tripsCount: Int,
    val languages: String,
    val photoUrl: String,
    val isTopMatch: Boolean = false,
    val badgeLabel: String? = null,
    val etaMins: Int,
    val ratePerHour: Int,
    val policeCleared: Boolean = true,
    val licenseVerified: Boolean = true,
    val experienceYears: Int,
    val specialties: String,
    val category: String = "general",
    val phone: String = "+91 98200 11982",
    val bio: String = "Verified professional chauffeur with extensive experience in luxury sedans, peak Mumbai traffic, and highway trips."
)

data class Testimonial(
    val id: String,
    val name: String,
    val location: String,
    val carModel: String,
    val review: String,
    val rating: Int,
    val photoUrl: String
)

data class UseCaseItem(
    val id: String,
    val title: String,
    val tag: String,
    val description: String,
    val iconName: String
)

data class TripRecord(
    val bookingId: String,
    val dateStr: String,
    val status: String,
    val driverName: String,
    val driverPhoto: String,
    val driverRating: Double,
    val driverTrips: Int,
    val pickupToDrop: String,
    val purpose: String,
    val vehicleInfo: String,
    val durationText: String,
    val totalFare: Int,
    val paymentMethod: String,
    val userRating: Double,
    val userReviewExcerpt: String,
    val isFamilyCare: Boolean = false,
    val familyMemberName: String? = null,
    val isUpcoming: Boolean = false
)

data class RegisteredCar(
    val id: String,
    val model: String,
    val plateNumber: String,
    val transmission: TransmissionType
)

data class FamilyProfile(
    val relation: String,
    val name: String,
    val age: Int,
    val destination: String,
    val purpose: String
)
