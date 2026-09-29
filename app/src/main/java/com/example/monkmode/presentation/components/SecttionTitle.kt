package com.example.monkmode.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SectionTitle(

    title:String,

    subtitle:String,

    actionText:String="",

    onActionClick:()->Unit={}

){

    Row(

        modifier=Modifier.fillMaxWidth(),

        verticalAlignment=Alignment.CenterVertically

    ){

        Column(

            modifier=Modifier.weight(1f)

        ){

            Text(

                text=title,

                style=MaterialTheme.typography.titleLarge,

                fontWeight=FontWeight.Bold,

                color=Color.White

            )

            Text(

                text=subtitle,

                color=Color.Gray

            )

        }

        if(actionText.isNotEmpty()){

            TextButton(

                onClick=onActionClick

            ){

                Text(actionText)

            }

        }

    }

}