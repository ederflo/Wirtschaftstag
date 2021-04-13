package at.eder.wirtschaftstagmobileapp.controllers

import at.eder.wirtschaftstagmobileapp.APIClient
import at.eder.wirtschaftstagmobileapp.models.Company
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyController {
    fun getAll(callback: (companies: List<Company>?) -> Unit, errCallback: (call: Call<List<Company>>, t: Throwable) -> Unit) {
        var call = APIClient.companyClient.getAll()
        call.enqueue(object : Callback<List<Company>> {
            override fun onResponse(call: Call<List<Company>>, response: Response<List<Company>>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun getOneById(id: Long, callback: (companies: Company?) -> Unit, errCallback: (call: Call<Company?>, t: Throwable) -> Unit) {
        var call = APIClient.companyClient.getOneById(id)
        call.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }

    fun save(company: Company, callback: (companies: Company?) -> Unit, errCallback: (call: Call<Company?>, t: Throwable) -> Unit) {
        var call = APIClient.companyClient.save(company)
        call.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                try {
                    if (response.code() == 200) {
                        callback(response.body())
                    }
                } catch(ex: Throwable) {
                    println(ex.message)
                }
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {
                errCallback(call, t)
            }
        })
    }
}