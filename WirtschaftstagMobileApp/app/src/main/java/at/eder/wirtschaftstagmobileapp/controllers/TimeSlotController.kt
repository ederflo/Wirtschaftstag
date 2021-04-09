package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.TimeSlot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class  TimeSlotController {
    fun getAll(callback: (timeSlots: List<TimeSlot>?) -> Unit, errCallback: (call: Call<List<TimeSlot>>, t: Throwable) -> Unit) {
        var call = APIClient.timeSlotClient.getAll()
        call.enqueue(object : Callback<List<TimeSlot>> {
            override fun onResponse(call: Call<List<TimeSlot>>, response: Response<List<TimeSlot>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<TimeSlot>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (timeslots: TimeSlot?) -> Unit, errCallback: (call: Call<TimeSlot?>, t: Throwable) -> Unit) {
        var call = APIClient.timeSlotClient.getOneById(id)
        call.enqueue(object : Callback<TimeSlot?> {
            override fun onResponse(call: Call<TimeSlot?>, response: Response<TimeSlot?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<TimeSlot?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(timeslot: TimeSlot, callback: (timeslots: TimeSlot?) -> Unit, errCallback: (call: Call<TimeSlot?>, t: Throwable) -> Unit) {
        var call = APIClient.timeSlotClient.save(timeslot)
        call.enqueue(object : Callback<TimeSlot?> {
            override fun onResponse(call: Call<TimeSlot?>, response: Response<TimeSlot?>) {
                try {
                    if (response.code() == 201) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<TimeSlot?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}