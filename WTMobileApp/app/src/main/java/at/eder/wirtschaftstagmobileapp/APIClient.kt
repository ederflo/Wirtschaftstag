package at.eder.wirtschaftstagmobileapp

import at.eder.wirtschaftstagmobileapp.clients.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    var protocol = "http"
    var ip = "vakuumapparat.duckdns.org"
    var port = "8080"

    private val retrofit get() = Retrofit.Builder()
        .baseUrl("$protocol://$ip:$port")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val companyClient: CompanyClient get() = retrofit.create(CompanyClient::class.java)
    val departmentClient: DepartmentClient get() = retrofit.create(DepartmentClient::class.java)
    val eventClient: EventClient get() = retrofit.create(EventClient::class.java)
    val mailClient: MailClient get() = retrofit.create(MailClient::class.java)
    val participationClient: ParticipationClient get() = retrofit.create(ParticipationClient::class.java)
    val timeSlotClient: TimeSlotClient get() = retrofit.create(TimeSlotClient::class.java)
    val userClient: UserClient get() = retrofit.create(UserClient::class.java)
}