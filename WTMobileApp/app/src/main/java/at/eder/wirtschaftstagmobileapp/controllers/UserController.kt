package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class UserController {
    fun getAll(callback: (users: List<User>?) -> Unit, errCallback: (call: Call<List<User>>, t: Throwable) -> Unit) {
        var call = APIClient.userClient.getAll()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (users: User?) -> Unit, errCallback: (call: Call<User?>, t: Throwable) -> Unit) {
        var call = APIClient.userClient.getOneById(id)
        call.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(user: User, callback: (users: User?) -> Unit, errCallback: (call: Call<User?>, t: Throwable) -> Unit) {
        var call = APIClient.userClient.save(user)
        call.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                try {
                    if (response.code() == 201) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}