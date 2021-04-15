package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.Mail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MailController {
    fun getAll(callback: (mails: List<Mail>?) -> Unit, errCallback: (call: Call<List<Mail>>, t: Throwable) -> Unit) {
        var call = APIClient.mailClient.getAll()
        call.enqueue(object : Callback<List<Mail>> {
            override fun onResponse(call: Call<List<Mail>>, response: Response<List<Mail>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<Mail>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (mails: Mail?) -> Unit, errCallback: (call: Call<Mail?>, t: Throwable) -> Unit) {
        var call = APIClient.mailClient.getOneById(id)
        call.enqueue(object : Callback<Mail?> {
            override fun onResponse(call: Call<Mail?>, response: Response<Mail?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Mail?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(mail: Mail, callback: (mails: Mail?) -> Unit, errCallback: (call: Call<Mail?>, t: Throwable) -> Unit) {
        var call = APIClient.mailClient.save(mail)
        call.enqueue(object : Callback<Mail?> {
            override fun onResponse(call: Call<Mail?>, response: Response<Mail?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Mail?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}