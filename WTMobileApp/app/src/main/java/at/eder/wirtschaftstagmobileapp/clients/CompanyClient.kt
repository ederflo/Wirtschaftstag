package at.eder.wirtschaftstagmobileapp.clients

import at.eder.wirtschaftstagmobileapp.models.Company
import retrofit2.Call
import retrofit2.http.*

interface CompanyClient {
    companion object {
        const val path = "/api/companies"
    }

    @GET(path)
    fun getAll() : Call<List<Company>>

    @GET("$path/{id}")
    fun getOneById(@Path("id") id: Long?): Call<Company?>

    @Headers(path)
    @PUT("/api/companies")
    fun save(@Body company: Company): Call<Company>
}