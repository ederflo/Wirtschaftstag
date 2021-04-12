package at.eder.wirtschaftstagmobileapp.models

data class Company(
    var id: Long?,
    var name: String?,
    var zipTown: String?,
    var street: String?,
    var phone: String?,
    var email: String?,
    var replyTo: String?,
    var comments: String?
) {
    override fun toString(): String = "$id - $name: $zipTown"
}