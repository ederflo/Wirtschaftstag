package at.eder.wirtschaftstagmobileapp.models

import java.time.LocalDate
import java.time.LocalDateTime

data class Event (
    var id: Long?,
    var label: String?,
    var date: String?,
    var organiser: User?
) {
    override fun toString(): String = "$id - $label: $date"
}
