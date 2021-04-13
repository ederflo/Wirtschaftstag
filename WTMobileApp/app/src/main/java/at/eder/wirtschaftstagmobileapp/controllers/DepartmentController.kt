package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.Department
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepartmentController {
    fun getAll(callback: (departments: List<Department>?) -> Unit, errCallback: (call: Call<List<Department>>, t: Throwable) -> Unit) {
        var call = APIClient.departmentClient.getAll()
        call.enqueue(object : Callback<List<Department>> {
            override fun onResponse(call: Call<List<Department>>, response: Response<List<Department>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<Department>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (departments: Department?) -> Unit, errCallback: (call: Call<Department?>, t: Throwable) -> Unit) {
        var call = APIClient.departmentClient.getOneById(id)
        call.enqueue(object : Callback<Department?> {
            override fun onResponse(call: Call<Department?>, response: Response<Department?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Department?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(department: Department, callback: (departments: Department?) -> Unit, errCallback: (call: Call<Department?>, t: Throwable) -> Unit) {
        var call = APIClient.departmentClient.save(department)
        call.enqueue(object : Callback<Department?> {
            override fun onResponse(call: Call<Department?>, response: Response<Department?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Department?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}