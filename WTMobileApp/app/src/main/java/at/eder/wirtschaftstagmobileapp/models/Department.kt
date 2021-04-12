package at.eder.wirtschaftstagmobileapp.models

data class Department(
    var id: Long?,
    var label: String?
) {
    override fun toString(): String = "$id - $label"
}