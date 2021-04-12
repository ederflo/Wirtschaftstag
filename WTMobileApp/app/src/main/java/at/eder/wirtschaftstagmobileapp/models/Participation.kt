package at.eder.wirtschaftstagmobileapp.models

data class Participation(
    var id: Long?,
    var price: Double?,
    var paidAlready: Double?,
    var comments: String?,
    var event: Event?,
    var company: Company?,
    var responsible: User?,
    var timeSlotOffer: List<TimeSlot>?,
    var interestedDepartments: List<Department>?
) {
    override fun toString(): String = "$id - ${event?.label} <-- ${company?.name}"
}