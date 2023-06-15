package connectfour

class Board {
    var rows = 6
    var columns = 7

    fun testValidSizeBoard() {
        var validInput = false
        while (!validInput) {
            print("Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7): ")
            val input = readln().replace(" ", "").replace("\t", "")

            if (input.isBlank()) {  //si l'input est vide, taille par défaut
                validInput = true
            } else {
                val boardSizeInput = input.split("x", "X")

                if (boardSizeInput.size != 2 || !Regex("""^\d+[xX]\d+$""").matches(input)) { //si le format n'est pas bon
                    println("Invalid input")
                } else {
                    rows = boardSizeInput[0].toInt()
                    columns = boardSizeInput[1].toInt()

                    if (rows !in 5..9) {
                        println("Board rows should be from 5 to 9")
                    } else if (columns !in 5..9) {
                        println("Board columns should be from 5 to 9")
                    } else {
                        validInput = true
                    }
                }
            }
        }
    }

    fun Display(columns: Int, board: MutableList<MutableList<String>>) {
        print(" ")
        //affichage numéro de colonne
        println((1..columns).joinToString(postfix = " ", separator = " "))

        // Affichage du tableau
        board.forEach { println(it.joinToString("║", prefix = "║", postfix = "║")) }

        // Affichage fond du tableau
        println("╚${"═╩".repeat(columns - 1)}═╝")
    }
}