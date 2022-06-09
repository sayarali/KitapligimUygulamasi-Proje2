package com.alisayar.kitapligimuygulamas_proje2

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*



fun convertTimeFromTimestamp(time: Timestamp): String {
    val now = Timestamp.now().toDate().time
    val diffTimeSeconds = (now - time.toDate().time) / 1000
    val diffTimeMinutes = diffTimeSeconds / 60
    val diffTimeHours = diffTimeMinutes / 60
    val diffTimeDays = diffTimeHours / 24


    var timeString = ""

    if(diffTimeSeconds < 60)
        timeString = "$diffTimeSeconds saniye önce"
    else if (diffTimeMinutes < 60)
        timeString = "$diffTimeMinutes dakika önce"
    else if (diffTimeHours < 24)
        timeString = "$diffTimeHours saat önce"
    else if (diffTimeDays < 7)
        timeString = "$diffTimeDays gün önce"
    else {

        val uploadedDay = time.toDate().date.toString()
        val uploadedMonth = time.toDate().month
        var month: String? = null
        when(uploadedMonth){
            0 -> month = "Ocak"
            1 -> month = "Şubat"
            2 -> month = "Mart"
            3 -> month = "Nisan"
            4 -> month = "Mayıs"
            5 -> month = "Haziran"
            6 -> month = "Temmuz"
            7 -> month = "Ağustos"
            8 -> month = "Eylül"
            9 -> month = "Ekim"
            10 -> month = "Kasım"
            11 -> month = "Aralık"
        }
        timeString = "$uploadedDay $month"

    }
    return timeString
}