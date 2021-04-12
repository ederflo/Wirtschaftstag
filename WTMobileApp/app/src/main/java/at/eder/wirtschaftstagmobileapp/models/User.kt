package at.eder.wirtschaftstagmobileapp.models

data class User(
    var id: Long?,
    var name: String?,
    var email: String?,
    var pwdToken: String?
) {
    override fun toString(): String = "$id - $name: $email"
}