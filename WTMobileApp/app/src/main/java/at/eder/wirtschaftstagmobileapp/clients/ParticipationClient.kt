package at.eder.wirtschaftstagmobileapp.clients

import at.eder.wirtschaftstagmobileapp.models.Participation
import retrofit2.Call
import retrofit2.http.*

interface ParticipationClient {
    companion object {
        const val path = "/api/participations"
    }

    @GET(path)
    fun getAll(@Query("eventId") eventId: Long = -1) : Call<List<Participation>>

    @GET(path)
    fun getAllByEventId(@Query("eventId") eventId: Long) : Call<List<Participation>>

    @GET("${path}/{id}")
    fun getOneById(@Path("id") id: Long?): Call<Participation?>

    @Headers("content-type: application/json")
    @PUT(path)
    fun save(@Body participation: Participation): Call<Participation>

    @DELETE("${path}/{id}")
    fun delete(@Path("id") id: Long?): Call<Boolean>
}