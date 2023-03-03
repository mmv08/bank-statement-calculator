import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

data class Transaction(
    val bookingDate: String,
    val valueDate: String,
    val type: String,
    val description: String,
    val amount: Double,
    val currency: String,
    val fxRate: String?,
    val includedMarkup: String?
)

fun parseStatement(): List<Transaction>? {
    val csv = object {}.javaClass.getResourceAsStream("csv/statement1.csv")

    return csv?.let { it ->
        csvReader().readAllWithHeader(it).map { tx ->
            Transaction(
                tx["Booking Date"]!!,
                tx["Value Date"]!!,
                tx["Type"]!!,
                tx["Description"]!!,
                tx["Amount"]!!.replace(" ", "").toDouble(),
                tx["Currency"]!!,
                tx["FX-rate"]!!,
                tx["Included Markup"]!!
            )
        }
    }
}

fun main() {
    var totalInbound = 0.0
    var totalOutbound = 0.0

    parseStatement()?.forEach() {
        if (it.amount > 0) {
            println(it.amount)
            totalInbound += it.amount
        } else {
            totalOutbound += it.amount
        }
    }

    println("Total Inbound: $totalInbound")
    println("Total Outbound: $totalOutbound")
}
