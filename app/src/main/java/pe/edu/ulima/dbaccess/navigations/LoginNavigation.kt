package pe.edu.ulima.dbaccess.navigations

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.ulima.dbaccess.ui.app.screens.CreateAccountScreen
import pe.edu.ulima.dbaccess.ui.app.screens.LoginScreen
import pe.edu.ulima.dbaccess.ui.app.screens.ResetPasswordScreen
import pe.edu.ulima.dbaccess.ui.app.screens.SplashScreen
import pe.edu.ulima.dbaccess.ui.app.viewmodels.CreateAccountScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.LoginScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.ResetPasswordScreenViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.SplashScreenViewModel


@Composable
fun LoginNavigation(
    loginScreenViewModel: LoginScreenViewModel,
    resetPasswordScreenViewModel: ResetPasswordScreenViewModel,
    createAccountScreenViewModel: CreateAccountScreenViewModel,
    splashScreenViewModel: SplashScreenViewModel
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val parameter = navBackStackEntry?.arguments?.getString("parameter")
    val optionalParameter = navBackStackEntry?.arguments?.getString("optionalParameter")

    NavHost(
        navController = navController,
        startDestination = "/"
    ){
        composable(
            route = "/",
            arguments = listOf()
        ){ entry ->
            SplashScreen(
                splashScreenViewModel,
                navController
            )
            Handler().postDelayed({
                navController.navigate("/login") //Despues de 2 segundos vamos a /login/
            }, 2000)

        }


        composable(
            route = "/login",
            arguments = listOf()
        ){ entry ->
            LoginScreen(
                loginScreenViewModel,
                goToCreateAccountScreen = {
                    navController.navigate("/create_account")
                },
                goToResetPasswordScreen = {
                    navController.navigate("/recover_password")
                }
            )
        }




        composable(
            route = "/login/{parameter}?optionalParameter={optionalParameter}",
            arguments = listOf(
                navArgument("parameter") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("optionalParameter") {
                    type = NavType.StringType
                    defaultValue = "default_value"
                }
            )
        ){ entry ->
            Log.d("pe.edu.ulima", "1 +++++++++++++++++++++++++++++++++++++++++++")
            Log.d("pe.edu.ulima", parameter.toString())
            Log.d("pe.edu.ulima", optionalParameter.toString())
            Log.d("pe.edu.ulima", "2 +++++++++++++++++++++++++++++++++++++++++++")
            if(parameter == null || parameter == ""){
                LoginScreen(
                    loginScreenViewModel,
                    goToCreateAccountScreen = {
                        navController.navigate("/create_account")
                    },
                    goToResetPasswordScreen = {
                        navController.navigate("/reset_password")
                    },

                    )
                //que hace esto redirige hacia reset password si no tiene los parametros?¿ ¿parameter?
            }else{
                loginScreenViewModel.updateUsuario(parameter)
                LoginScreen(
                    loginScreenViewModel,
                    goToCreateAccountScreen = {
                        navController.navigate("/create_account")
                    },
                    goToResetPasswordScreen = {
                        navController.navigate("/reset_password")
                    }
                )
            }
        }

        composable(
            route = "/recover_password",
            arguments = listOf()
        ){ entry ->
            ResetPasswordScreen(
                resetPasswordScreenViewModel,
                goToLoginScreen = {
                    navController.navigate("/login")
                    //ESTO TAMPOCO ENTIENDO
                }
            )
        }

        composable(
            route = "/create_account",
            arguments = listOf()
        ){ entry ->
            CreateAccountScreen(
                createAccountScreenViewModel
            ) {
                navController.navigate("/login")
            }

        }


    }
}