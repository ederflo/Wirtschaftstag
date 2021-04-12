package at.eder.wirtschaftstagmobileapp.clients

import at.eder.wirtschaftstagmobileapp.models.User
import retrofit2.Call
import retrofit2.http.*

interface UserClient {
    companion object {
        const val path = "/api/users"
    }

    @GET(path)
    fun getAll() : Call<List<User>>

    @GET("${path}/{id}")
    fun getOneById(@Path("id") id: Long?): Call<User?>

    @Headers("content-type: application/json")
    @PUT(path)
    fun save(@Body user: User): Call<User>
}