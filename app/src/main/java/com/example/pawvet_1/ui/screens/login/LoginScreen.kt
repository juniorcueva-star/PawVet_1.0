package com.example.pawvet_1.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawvet_1.R
import com.example.pawvet_1.ui.theme.PawVetBackground
import com.example.pawvet_1.ui.theme.PawVetBorder
import com.example.pawvet_1.ui.theme.PawVetBodyFont
import com.example.pawvet_1.ui.theme.PawVetCoralGlow
import com.example.pawvet_1.ui.theme.PawVetPrimary
import com.example.pawvet_1.ui.theme.PawVetPrimaryDeep
import com.example.pawvet_1.ui.theme.PawVetSurface
import com.example.pawvet_1.ui.theme.PawVetTextPrimary
import com.example.pawvet_1.ui.theme.PawVetTextSecondary

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    errorMessage: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val canSubmit = email.isNotBlank() && password.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PawVetBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            LoginHeroHeader()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-28).dp),
                shape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp),
                color = PawVetBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenido a PawVet",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 34.sp, lineHeight = 38.sp),
                        color = PawVetTextPrimary
                    )

                    Text(
                        text = "Ingresa a tu clínica digital en un espacio alegre, natural y pensado para tus mascotas.",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp, lineHeight = 24.sp),
                        color = PawVetTextSecondary,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.9f)
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp),
                        color = Color.White.copy(alpha = 0.97f),
                        border = BorderStroke(1.dp, PawVetBorder.copy(alpha = 0.9f)),
                        shadowElevation = 14.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (!errorMessage.isNullOrBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = Color(0xFFFFF1EE),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.18f))
                                ) {
                                    Text(
                                        text = errorMessage,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                                    )
                                }
                            }

                            AuthField(
                                value = email,
                                onValueChange = { email = it },
                                label = "Correo electrónico",
                                icon = {
                                    Icon(Icons.Default.Email, contentDescription = null, tint = PawVetPrimary)
                                },
                                keyboardType = KeyboardType.Email
                            )

                            AuthField(
                                value = password,
                                onValueChange = { password = it },
                                label = "Contraseña",
                                icon = {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = PawVetPrimary)
                                },
                                keyboardType = KeyboardType.Password,
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Default.Info else Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = PawVetTextSecondary
                                        )
                                    }
                                }
                            )

                            Button(
                                onClick = { onLoginClick(email, password) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(62.dp)
                                    .shadow(
                                        elevation = if (canSubmit) 14.dp else 0.dp,
                                        shape = RoundedCornerShape(22.dp),
                                        ambientColor = PawVetPrimary
                                    ),
                                enabled = canSubmit,
                                shape = RoundedCornerShape(22.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PawVetPrimary,
                                    disabledContainerColor = PawVetPrimary.copy(alpha = 0.32f),
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White.copy(alpha = 0.82f)
                                )
                            ) {
                                Text(
                                    text = "Iniciar sesión",
                                    fontFamily = PawVetBodyFont,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRegisterClick() },
                        shape = RoundedCornerShape(22.dp),
                        color = PawVetSurface,
                        border = BorderStroke(1.dp, PawVetBorder),
                        shadowElevation = 6.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(9.dp)
                                    .background(PawVetPrimary, CircleShape)
                            )
                            Text(
                                text = " ¿Nuevo en la clínica?",
                                color = PawVetTextSecondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = " Crear cuenta",
                                color = PawVetPrimaryDeep,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
private fun LoginHeroHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(328.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.login_playful_bg),
            contentDescription = "Mascotas jugando en el pasto",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1F083C3A),
                            Color.Transparent,
                            PawVetBackground.copy(alpha = 0.98f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopEnd)
                .offset(x = 18.dp, y = (-16).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(PawVetCoralGlow.copy(alpha = 0.26f), Color.Transparent),
                        radius = 240f
                    ),
                    shape = CircleShape
                )
        )

    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: @Composable () -> Unit,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = icon,
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF8FCFB),
            unfocusedContainerColor = Color(0xFFF8FCFB),
            focusedBorderColor = PawVetPrimary,
            unfocusedBorderColor = PawVetBorder,
            focusedLabelColor = PawVetPrimary,
            unfocusedLabelColor = PawVetTextSecondary,
            cursorColor = PawVetPrimary
        )
    )
}
