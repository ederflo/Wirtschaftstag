package at.eder.wirtschaftstagmobileapp.models

data class Event (
    var id: Long?,
    var label: String?,
    var date: String?,
    var organiser: User?
) {
    override fun toString(): String {
        return if (id == (-1).toLong())
            label!!
        else
            "$id - $label: $date"
    }
}
