package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.User
import at.eder.wirtschaftstagmobileapp.models.UserTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {
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

    fun getAllByUserType(userType: UserTypes, callback: (users: List<User>?) -> Unit, errCallback: (call: Call<List<User>>, t: Throwable) -> Unit) {
        var call = APIClient.userClient.getAllByUserType(userType.toString())
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
        var call = APIClient.userClient.save(user, user.userType.toString())
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

    fun delete(id: Long, callback: (result: Boolean?) -> Unit, errCallback: (call: Call<Boolean?>, t: Throwable) -> Unit) {
        var call = APIClient.userClient.delete(id)
        call.enqueue(object : Callback<Boolean?> {
            override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}