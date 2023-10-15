package icesi.edu.co.icesicare.model.repository

import icesi.edu.co.icesicare.model.entity.Appointment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AppointmentsRepository {

    @GET("appointments/{studentId}")
    fun getUserAppointment(@Path("id") id : String) : Call<Appointment>
}