package at.eder.wirtschaftstagmobileapp.clients

import at.eder.wirtschaftstagmobileapp.models.TimeSlot
import retrofit2.Call
import retrofit2.http.*

interface TimeSlotClient {
    companion object {
        const val path = "/api/timeslots"
    }

    @GET(path)
    fun getAll() : Call<List<TimeSlot>>

    @GET("${path}/{id}")
    fun getOneById(@Path("id") id: Long?): Call<TimeSlot?>

    @Headers("content-type: application/json")
    @PUT(path)
    fun save(@Body timeSlot: TimeSlot): Call<TimeSlot>
}