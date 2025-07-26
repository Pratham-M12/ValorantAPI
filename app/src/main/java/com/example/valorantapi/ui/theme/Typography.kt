package com.example.valorantapi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.valorantapi.R

val ValorantFont = FontFamily(Font(R.font.orbitron_regular, FontWeight.Normal))

val valorantTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = ValorantWhite
    ),
    headlineSmall = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = ValorantRed
    ),
    bodyLarge = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = ValorantWhite
    ),
    bodyMedium = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = ValorantWhite
    ),
    bodySmall = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        color = ValorantWhite
    ),
    labelLarge = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = ValorantRed
    ),
    titleLarge = TextStyle(
        fontFamily = ValorantFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = ValorantWhite
    )
)
