package com.intercam.mapbiteam.vista

import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import com.intercam.mapbiteam.R
import com.intercam.mapbiteam.vista.components.NavigationItem
import com.intercam.mapbiteam.vista.components.TransparentTextField
import com.intercam.mapbiteam.vista.components.busquedaTextField
import com.intercam.mapbiteam.vista.model.PlaceInfo
import kotlinx.coroutines.launch
import com.intercam.mapbiteam.vista.components.*


private const val TAG = "MapActivity"

private val caj1 = LatLng(1.35, 103.87)
private val caj2 = LatLng(1.40, 103.77)
private val caj3 = LatLng(1.45, 103.77)
private val defaultCameraPosition = CameraPosition.fromLatLngZoom(caj1, 11f)

@Composable
fun mapa(navController: NavHostController) {

    var isMapLoaded by remember { mutableStateOf(false) }
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }



    //widgets
    val mSearchText: AutoCompleteTextView? = null
    val mGps: ImageView? = null
    var mInfo:android.widget.ImageView? = null
    var mPlacePicker:android.widget.ImageView? = null


    //vars
    val mLocationPermissionsGranted = false
    val mMap: GoogleMap? = null
    val mFusedLocationProviderClient: FusedLocationProviderClient? = null
   // val mPlaceAutocompleteAdapter: PlaceAutocompleteAdapter? = null
    val mGoogleApiClient: GoogleApiClient? = null
    val mPlace: PlaceInfo? = null
    val mMarker: Marker? = null




    Box(Modifier.fillMaxSize()) {
        GoogleMapView(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            },
            navController = navController
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .wrapContentSize()
                )
            }
        }
    }


}


@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {},
    navController: NavController
) {
    val caj1State = rememberMarkerState(position = caj1)
    val caj2State = rememberMarkerState(position = caj2)
    val caj3State = rememberMarkerState(position = caj3)
    var circleCenter by remember { mutableStateOf(caj1) }


    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    var shouldAnimateZoom by remember { mutableStateOf(true) }
    var ticker by remember { mutableStateOf(0) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var mapVisible by remember { mutableStateOf(true) }
    val busqueda = rememberSaveable{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val searchButtonColor = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFF0a497b),
        contentColor = MaterialTheme.colors.primary
    )

    if (mapVisible) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = onMapLoaded,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.name}")
            }
        ) {
            // Drawing on the map is accomplished with a child-based API
            val markerClick: (Marker) -> Boolean = {
                Log.d(TAG, "${it.title} was clicked")
                cameraPositionState.projection?.let { projection ->
                    Log.d(TAG, "The current projection is: $projection")
                }
                false
            }
            MarkerInfoWindowContent(
                state = caj1State,
                title = "Zoom in has been tapped $ticker times.",
                onClick = markerClick,
                draggable = true,
            ) {
                Text(it.title ?: "Title", color = Color.Red)
            }
            MarkerInfoWindowContent(
                state = caj2State,
                title = "Marker with custom info window.\nZoom in has been tapped $ticker times.",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                onClick = markerClick,
            ) {
                Text(it.title ?: "Title", color = Color.Blue)
            }
            Marker(
                state = caj3State,
                title = "Marker in CDMX",
                onClick = markerClick
            )
            Circle(
                center = circleCenter,
                fillColor = MaterialTheme.colors.secondary,
                strokeColor = MaterialTheme.colors.secondaryVariant,
                radius = 1000.0,
            )
            content()
        }

    }
    Column {


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 36.dp, 0.dp, 0.dp)
          ) {


                busquedaTextField(
                    textFieldValue = busqueda,
                    textLabel = "busqueda",
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Localized description") },
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    imeAction = ImeAction.Search
                )


            FloatingActionButton(
                modifier = Modifier
                    .size(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),


                backgroundColor = Color.White,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "Forward Icon",
                    tint = Color(0xFF0a497b)
                )
            }
        }//row
    }//column




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

                            Text(text="hello")
                            Scaffold(
                                bottomBar = { BottomBar(navController = navController)}
                            ) {
                          BottomNavigation(navController = navController)
                            }

                        }




                        }
                    }
                }



        }

}// fin fun


@Composable
fun BottomBar(navController: NavController){
    val screens = listOf(
        NavigationItem.Home,
        NavigationItem.Mapa,
        NavigationItem.Configuracion,
        NavigationItem.Perfil

    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation() {
        screens.forEach { screen ->
            AddItem(screen = screen,
                currentDestination = currentDestination ,
                navController = navController as NavHostController
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: NavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController)
{

    BottomNavigationItem (
        label = { Text(text = screen.title)},
        icon ={ Icon(imageVector = screen.icon, contentDescription = "navigation icpn" )},
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {navController.navigate(screen.route)
        }
    )
}


@Composable
private fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(state = ScrollState(0)),
        horizontalArrangement = Arrangement.Center
    ) {
        MapType.values().forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}

@Composable
private fun MapTypeButton(type: MapType, onClick: () -> Unit) =
    MapButton(text = type.toString(), onClick = onClick)

@Composable
private fun ZoomControls(
    isCameraAnimationChecked: Boolean,
    isZoomControlsEnabledChecked: Boolean,
    onZoomOut: () -> Unit,
    onZoomIn: () -> Unit,
    onCameraAnimationCheckedChange: (Boolean) -> Unit,
    onZoomControlsCheckedChange: (Boolean) -> Unit,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        MapButton("-", onClick = { onZoomOut() })
        //MapButton("+", onClick = { onZoomIn() })
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Camera Animations On?")
           /* Switch(
                isCameraAnimationChecked,
                onCheckedChange = onCameraAnimationCheckedChange,
                modifier = Modifier.testTag("cameraAnimations"),
            )
            Text(text = "Zoom Controls On?")
            Switch(
                isZoomControlsEnabledChecked,
                onCheckedChange = onZoomControlsCheckedChange
            )*/
        }
    }
}

@Composable
private fun MapButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun DebugView(
    cameraPositionState: CameraPositionState,
    markerState: MarkerState
) {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        val moving =
            if (cameraPositionState.isMoving) "moving" else "not moving"
        Text(text = "Camera is $moving")
        Text(text = "Camera position is ${cameraPositionState.position}")
        Spacer(modifier = Modifier.height(4.dp))
        val dragging =
            if (markerState.dragState == DragState.DRAG) "dragging" else "not dragging"
        Text(text = "Marker is $dragging")
        Text(text = "Marker position is ${markerState.position}")
    }
}


@Preview
@Composable
fun GoogleMapViewPreview() {

        GoogleMapView(Modifier.fillMaxSize())

}