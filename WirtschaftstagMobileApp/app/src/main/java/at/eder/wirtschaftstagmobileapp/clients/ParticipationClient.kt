package at.eder.wirtschaftstagmobileapp.clients

import at.eder.wirtschaftstagmobileapp.models.Participation
import retrofit2.Call
import retrofit2.http.*

interface ParticipationClient {
    companion object {
        const val path = "/api/participations"
    }

    @GET(path)
    fun getAll() : Call<List<Participation>>

    @GET("${path}/{id}")
    fun getOneById(@Path("id") id: Long?): Call<Participation?>

    @Headers("content-type: application/json")
    @PUT(path)
    fun save(@Body participation: Participation): Call<Participation>
}