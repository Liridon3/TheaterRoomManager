val menu = """
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit"""

var selectedSeat = emptyArray<Pair<Int, Int>>()

fun main () {
    val rows = printAndEnter("Enter the number of rows:")
    val seatsPerRow = printAndEnter("Enter the number of seats in each row:")

    do {
        val menuSelected = printAndEnter (menu)
        when (menuSelected) {
            1 -> showSeats(rows, seatsPerRow)
            2 -> buyATicket(rows, seatsPerRow)
            3 -> showStats(rows, seatsPerRow)
            0 -> return

        }
    } while (menuSelected in 1..3)
}
fun printAndEnter(info: String): Int {
    println(info)
    return readLine()!!.toInt()
}

fun buyATicket (rows: Int, seatsPerRow: Int) {
    do {
        println("Enter a row number:")
        var rowEntered = readln().toInt()

        println("Enter a seat number in that row:")
        var seatsEntered = readln().toInt()

        val seatIsContained = rowEntered in 1..rows && seatsEntered in 1..seatsPerRow
        val seatIsNotAvailable = selectedSeat.contains(Pair(rowEntered, seatsEntered))

        if (seatIsContained == false) {
            println("Wrong input!")
            continue
        }

        if (seatIsNotAvailable == true) {
            println("That ticket has already been purchased!")
            continue
        }

        var totalSeats = rows * seatsPerRow

        var ticket = if (totalSeats <= 60) {
            10
        } else {
            var frontrows: Int = rows / 2
            if (rowEntered <= frontrows) {
                10
            } else {
                8
            }
        }
        println("Ticket price: $$ticket")

        selectedSeat += Pair(rowEntered, seatsEntered)
    } while (seatIsContained == false || seatIsNotAvailable == true)
}


fun showSeats (rows: Int, seatsPerRow: Int) {
    println("Cinema:")
    for (rowNumber in 0..rows) {
        for (seatNumber in 0..seatsPerRow) {
            if (rowNumber == 0 && seatNumber == 0) {
                print(" ")
            } else if (rowNumber == 0) {
                print(" $seatNumber")
            } else if (seatNumber == 0) {
                print("$rowNumber")
            } else {
                print(" ")
                print(getSeats(rowNumber, seatNumber))
            }
        }
        println()
    }

}

fun getSeats (rowNumber: Int, seatNumber: Int): String {
    return if (selectedSeat.contains(Pair(rowNumber, seatNumber))) "B" else "S"
}

fun showStats (rows: Int, seatsPerRow: Int) {
    println("Number of purchased tickets: ${selectedSeat.size}")
    println("Percentage: ${percent(rows, seatsPerRow)}%")
    println("Current income: $${currIncome(rows, seatsPerRow)}")
    println("Total income: $${totalIncome(rows, seatsPerRow)}")
}

fun percent (rows: Int, seatsPerRow: Int): String {
    val percentage = selectedSeat.size / (rows.toDouble() * seatsPerRow.toDouble()) * 100
    return String.format("%.2f", percentage)
}

fun currIncome (rows: Int, seatsPerRow: Int): Int {
    var currentInc = 0
    for (row in 1..rows) {
        for (seat in 1..seatsPerRow) {
            if (selectedSeat.contains(Pair(row, seat))) {
                currentInc += if (rows * seatsPerRow <= 60) {10}
                              else if (row <= rows / 2) {10}
                              else {8}
            }
        }
    }
    return currentInc
}

fun totalIncome (rows: Int, seatsPerRow: Int) :Int {
    var totalInc: Int = if (rows * seatsPerRow <= 60) {
        rows * seatsPerRow * 10
    }
    else {
        (rows / 2 * seatsPerRow * 10) + ((rows - (rows / 2)) * seatsPerRow * 8)
    }
    return totalInc
}
