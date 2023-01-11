package com.prvt.horariosbuap.core

import com.google.firebase.Timestamp
import java.util.concurrent.TimeUnit

private const val SECOND_MILIS = 1
private const val MINUTE_MILIS = 60 * SECOND_MILIS
private const val HOUR_MILIS = 60 * MINUTE_MILIS
private const val DAY_MILIS = 24 * HOUR_MILIS

object TimeDifferences {

    //Regresa hace cuanto tiempo se realizo la accion de la fecha recibida
    fun getTimeAgo(time : Int) :  String{

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())

        if (time > now || time <= 0){
            return "En otra linea temporal"
        }

        val diff = now - time
        return when{

            diff < MINUTE_MILIS -> "Ahora"
            diff < 2 * MINUTE_MILIS -> "Hace un minuto"
            diff < HOUR_MILIS -> "Hace ${diff / MINUTE_MILIS} minutos"
            diff < 2 * HOUR_MILIS -> "Hace una hora"
            diff < DAY_MILIS -> "Hace ${diff / HOUR_MILIS} horas"
            diff < 2 * DAY_MILIS -> "Ayer"
            else -> "Hace ${diff - DAY_MILIS} dias"
        }
    }

    //Regresa los dias de diferencia entre una fecha dada y la fecha actual
    //regresa un -1000 si la fecha dada es incorrecta o incoherente
    fun getTimeLimitDays(time: Int) : Int{
        val now = obternerFechaActual().seconds
        val diff = now - time

        if (diff > now || diff <= 0){
            return -1000
        }
        return (diff / DAY_MILIS).toInt()
    }
}

private fun obternerFechaActual(): Timestamp{
    return Timestamp.now()
}