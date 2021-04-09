package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.Participation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class ParticipationController {
    fun getAll(callback: (participations: List<Participation>?) -> Unit, errCallback: (call: Call<List<Participation>>, t: Throwable) -> Unit) {
        var call = APIClient.participationClient.getAll()
        call.enqueue(object : Callback<List<Participation>> {
            override fun onResponse(call: Call<List<Participation>>, response: Response<List<Participation>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<Participation>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (participations: Participation?) -> Unit, errCallback: (call: Call<Participation?>, t: Throwable) -> Unit) {
        var call = APIClient.participationClient.getOneById(id)
        call.enqueue(object : Callback<Participation?> {
            override fun onResponse(call: Call<Participation?>, response: Response<Participation?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Participation?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(participation: Participation, callback: (participations: Participation?) -> Unit, errCallback: (call: Call<Participation?>, t: Throwable) -> Unit) {
        var call = APIClient.participationClient.save(participation)
        call.enqueue(object : Callback<Participation?> {
            override fun onResponse(call: Call<Participation?>, response: Response<Participation?>) {
                try {
                    if (response.code() == 201) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Participation?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}