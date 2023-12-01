package icesi.edu.co.icesicare.model.entity

import icesi.edu.co.icesicare.util.CalendarUtils.toLocalDateTime
import java.sql.Timestamp
import java.util.Date


data class AppointmentFirebase (

    var id : String = "",
    var date : Date = Date(),
    var canceled : Boolean = false,
    var approved: Boolean = false,
    var motive : String = "",
    var psychologistId : String = "",
    var studentId : String = ""
)

class AppointmentHelper{
    companion object{/*
        fun appointmentToAppointmentFirebase(appointment: Appointment): AppointmentFirebase {

            val finalDate = Timestamp.valueOf(appointment.date)

            return AppointmentFirebase(
                appointment.id,
                finalDate,
                appointment.canceled,
                appointment.approved,
                appointment.motive,
                appointment.psychologistId,
                appointment.studentId
            )
        }
        */
        fun appointmentFirebaseToAppointment(appointmentFirebase: AppointmentFirebase): Appointment {

            val finalDate = appointmentFirebase.date.toLocalDateTime

            return Appointment(
                appointmentFirebase.id,
                finalDate,
                appointmentFirebase.canceled,
                appointmentFirebase.approved,
                appointmentFirebase.motive,
                appointmentFirebase.psychologistId,
                appointmentFirebase.studentId
            )
        }
    }
}