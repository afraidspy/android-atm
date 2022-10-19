package com.intercam.mapbiteam.vista

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.intercam.mapbiteam.R
import com.intercam.mapbiteam.vista.components.TransparentTextField


@Composable
fun login(navController: NavHostController) {

        val usrValue = rememberSaveable{ mutableStateOf("") }
        val passwordValue = rememberSaveable{ mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

    val imageModifier = Modifier.size(450.dp,450.dp)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF0a497b))
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Login Image",
                contentScale = ContentScale.Fit,
                modifier = imageModifier,
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ){
                ConstraintLayout {

                    val (surface, fab) = createRefs()

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .constrainAs(surface) {
                                bottom.linkTo(parent.bottom)
                            },
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStartPercent = 8,
                            topEndPercent = 8
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ){


                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Dp(16f)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                TransparentTextField(
                                    textFieldValue = usrValue,
                                    textLabel = "Usuario",
                                    keyboardType = KeyboardType.Email,
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }
                                    ),
                                    imeAction = ImeAction.Next
                                )

                                TransparentTextField(
                                    textFieldValue = passwordValue,
                                    textLabel = "Contraseña",
                                    keyboardType = KeyboardType.Password,
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()

                                            //TODO("LOGIN")
                                        }
                                    ),
                                    imeAction = ImeAction.Done,
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                passwordVisibility = !passwordVisibility
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if(passwordVisibility) {
                                                    Icons.Default.Visibility
                                                } else {
                                                    Icons.Default.VisibilityOff
                                                },
                                                contentDescription = "Toggle Password Icon"
                                            )
                                        }
                                    },
                                    visualTransformation = if(passwordVisibility) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    }
                                )


                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RoundedButton(
                                    text = "Entrar",
                                    displayProgressBar = false,
                                    onClick = {
                                        // TODO("LOGIN")
                                        navController.navigate("mapa")


                                    }
                                )


                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .size(72.dp)
                            .constrainAs(fab) {
                                top.linkTo(surface.top, margin = (-36).dp)
                                end.linkTo(surface.end, margin = 36.dp)
                            },
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = {}
                    ) {
                        Icon(
                            modifier = Modifier.size(42.dp),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Forward Icon",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
