package at.eder.wirtschaftstagmobileapp.models

import java.io.File

data class Mail(
    var id: Long?,
    var nr: Int?,
    var date: String?,
    var time: String?,
    var subject: String?,
    var content: String?,
    var attachment1: File?,
    var attachment2: File?,
    var attachment3: File?,
) {
    override fun toString(): String = "$id - $subject: $date"
}