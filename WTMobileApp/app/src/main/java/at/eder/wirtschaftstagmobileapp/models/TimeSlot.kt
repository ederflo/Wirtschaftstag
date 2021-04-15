package at.eder.wirtschaftstagmobileapp.models

data class TimeSlot(
    var id: Long?,
    var starts: String?,
    var ends: String?,
    var maxParticipants: Int?
) {
    override fun toString(): String = "$id - $starts - $ends"
}