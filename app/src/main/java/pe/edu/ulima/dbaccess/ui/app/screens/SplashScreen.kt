package pe.edu.ulima.dbaccess.ui.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.edu.ulima.dbaccess.ui.theme.Gray200
import pe.edu.ulima.dbaccess.ui.theme.Orange200
import pe.edu.ulima.dbaccess.R
import pe.edu.ulima.dbaccess.ui.app.viewmodels.SplashScreenViewModel

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen(
        SplashScreenViewModel(),
        rememberNavController()
    )

}

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navController: NavHostController
){
    val caslonFont = FontFamily(Font(R.font.caslon_classico_sc_regular))
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_ulima),
                contentDescription = "Logo Ulima",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 10.dp),
                colorFilter = ColorFilter.tint(
                    color = if(isSystemInDarkTheme()) Orange200 else Gray200
                )
            )
            Text(
                text = "UNIVERSIDAD DE LIMA\n PROGRAMACIÓN MÓVIL \n 2023-I",
                textAlign = TextAlign.Center,
                fontFamily = caslonFont
            )
        }
    }

    viewModel.checkUser(context ,navController )
}