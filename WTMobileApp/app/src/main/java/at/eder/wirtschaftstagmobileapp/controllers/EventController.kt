package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventController {
    fun getAll(callback: (events: List<Event>?) -> Unit, errCallback: (call: Call<List<Event>>, t: Throwable) -> Unit) {
        var call = APIClient.eventClient.getAll()
        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (events: Event?) -> Unit, errCallback: (call: Call<Event?>, t: Throwable) -> Unit) {
        var call = APIClient.eventClient.getOneById(id)
        call.enqueue(object : Callback<Event?> {
            override fun onResponse(call: Call<Event?>, response: Response<Event?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Event?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(event: Event, callback: (events: Event?) -> Unit, errCallback: (call: Call<Event?>, t: Throwable) -> Unit) {
        var call = APIClient.eventClient.save(event)
        call.enqueue(object : Callback<Event?> {
            override fun onResponse(call: Call<Event?>, response: Response<Event?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Event?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}