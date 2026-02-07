package com.example.starwarsentitymanagementsystem.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.starwarsentitymanagementsystem.R


@Composable
fun NoDataScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        //Creación en las guías que se posicionan en el (10% del comienzo, 10% del final
        //y en la mitad de la pantalla verticalmente)
        val startGuide = createGuidelineFromStart(0.1f)
        val endGuide = createGuidelineFromEnd(0.1f)
        val verticalCenterGuide = createGuidelineFromTop(0.5f)

        //Crear las referencias de los elementos en el diseño
        val (animation,text) = createRefs()

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))
        val animatable = rememberLottieAnimatable()

        LaunchedEffect(composition) {
            animatable.animate(
                composition = composition,
                iterations = 3,
                speed = 0.5f
            )
        }

        LottieAnimation(
            composition = composition,
            progress = { animatable.progress },
            modifier = Modifier.constrainAs(animation) {
                bottom.linkTo(verticalCenterGuide)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                width = Dimension.fillToConstraints
                height = Dimension.ratio("1:1")
            }
        )

        Text( text = stringResource(R.string.no_data),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(text){
                top.linkTo(verticalCenterGuide, margin = 12.dp)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview (showBackground = true)
@Composable
fun NoDataScreenPreview(){
    NoDataScreen()
}