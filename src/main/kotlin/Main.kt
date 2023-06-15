package connectfour

//taille du tableau par défaut
var rows = 6
var columns = 7

val player1 = Players()
val player2 = Players()

var multipleGame = false

fun main() {
    //Paramétrage du jeu
    println("Connect Four")
    print("First player's name: ")
    player1.name = readln()
    print("Second player's name: ")
    player2.name = readln()

    //modification de la taille du tableau et vérification du format
    testValidSizeBoard()

    //création de la liste du tableau
    var board = MutableList(rows) { MutableList(columns) { " " } }
    var numberOfGame: Int = 1 // Valeur par défaut

// Validation nombre de jeu
    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")

        val userInput = readLine()

        if (userInput.isNullOrEmpty()) {
            // L'utilisateur a appuyé sur Enter, utilise la valeur par défaut
            break
        }

        try {
            numberOfGame = userInput!!.toInt()
            if (numberOfGame == 0) {
                println("Invalid input")
            } else {
                break
            }
        } catch (e: NumberFormatException) {
            println("Invalid input")
        }
    }


    if(numberOfGame > 1){
        multipleGame = true
    }

    //affichage début de jeu
    println("${player1.name} VS ${player2.name}")
    println("$rows X $columns board")
    if (multipleGame){
        println("Total $numberOfGame games")
    }
    var firstMoveGame = player1.name

    for(i in 1..numberOfGame){
        if (multipleGame){
            println("Game #$i")
            firstMoveGame = if (i % 2 == 1) player1.name else player2.name
        } else {
            println("Single game")
        }
        boardDisplay(columns, board)
        game(player1.name,player2.name,firstMoveGame, board)
        board = MutableList(rows) { MutableList(columns) { " " } }
    }
    println("Game over!")
}

fun boardDisplay(columns: Int, board: MutableList<MutableList<String>>) {
    print(" ")
    //affichage numéro de colonne
    println((1..columns).joinToString(postfix = " ", separator = " "))

    // Affichage du tableau
    board.forEach { println(it.joinToString("║", prefix = "║", postfix = "║")) }

    // Affichage fond du tableau
    println("╚${"═╩".repeat(columns - 1)}═╝")
}

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

fun game(player1Name: String, player2Name: String, currentPlayer: String, board: MutableList<MutableList<String>>){
    var columnChoiceInput: String
    var totalTokensPlaced = 0
    var currentPlayerOfGames = currentPlayer

    boucle@ while (true) {
        println("$currentPlayerOfGames's turn:")
        columnChoiceInput = readln()

        if (columnChoiceInput == "end") {
            break
        }

        try {
            val columnChoiceInputIndex0 = columnChoiceInput.toInt() - 1 //calqué sur l'index d'une liste
            if (columnChoiceInputIndex0 in 0 until columns) {
                var tokenPlaced = false // Variable pour vérifier si le jeton a été placé
                var rowsIndex0 = rows - 1 //calqué sur l'index d'une liste

                //placer jeton si emplacement vide sinon placé au-dessus dans la même colonne
                while (rowsIndex0 >= 0 && !tokenPlaced) {
                    if (board[rowsIndex0][columnChoiceInputIndex0] == " ") {
                        board[rowsIndex0][columnChoiceInputIndex0] = if (currentPlayerOfGames == player1Name) "o" else "*"
                        tokenPlaced = true
                    }
                    rowsIndex0--
                }

                if (!tokenPlaced) {
                    println("Column $columnChoiceInput is full")
                } else {
                    currentPlayerOfGames = if (currentPlayerOfGames == player1Name) player2Name else player1Name

                    boardDisplay(columns, board)
                    totalTokensPlaced++

                    if (totalTokensPlaced == rows * columns) {
                        println("It is a draw")
                        player1.score += 1
                        player2.score += 1
                        break
                    }
                }
            } else {
                println("The column number is out of range (1 - $columns)")
            }
        } catch (e: NumberFormatException) {
            println("Incorrect column number")
        }

        //Victory check
        //horizontal test
        for(i in 0 until rows){
            for (j in 0..columns -4)
                if(board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2] && board[i][j+2] == board[i][j+3] && (board[i][j] == "*"||board[i][j] == "o")){
                    if (board[i][j] == "o") {
                        println("Player $player1Name won")
                        player1.score += 2
                    } else {
                        println("Player $player2Name won")
                        player2.score += 2
                    }
                    break@boucle
                }
        }

        //vertical test
        for(j in 0 until columns){
            for (i in 0..rows -4)
                if(board[i][j] == board[i+1][j] && board[i+1][j] == board[i+2][j] && board[i+2][j] == board[i+3][j] && (board[i][j] == "*"||board[i][j] == "o")){
                    if (board[i][j] == "o") {
                        println("Player $player1Name won")
                        player1.score += 2
                    } else {
                        println("Player $player2Name won")
                        player2.score += 2
                    }
                    break@boucle
                }
        }

        //test oblique descendant
        for(j in 0 .. columns-4){
            for (i in 0..rows -4)
                if(board[i][j] == board[i+1][j+1] && board[i+1][j+1] == board[i+2][j+2] && board[i+2][j+2] == board[i+3][j+3] && (board[i][j] == "*"||board[i][j] == "o")){
                    if (board[i][j] == "o") {
                        println("Player $player1Name won")
                        player1.score += 2
                    } else {
                        println("Player $player2Name won")
                        player2.score += 2
                    }
                    break@boucle
                }
        }

        //test oblique montant
        for(i in rows-1 downTo 3){
            for (j in 0 .. columns-4)
                if(board[i][j] == board[i-1][j+1] && board[i-1][j+1] == board[i-2][j+2] && board[i-2][j+2] == board[i-3][j+3] && (board[i][j] == "*"||board[i][j] == "o")){
                    if (board[i][j] == "o") {
                        println("Player $player1Name won")
                        player1.score += 2
                    } else {
                        println("Player $player2Name won")
                        player2.score += 2
                    }
                    break@boucle
                }
        }
    }

    if (multipleGame){
        println("Score")
        println("$player1Name: ${player1.score} $player2Name: ${player2.score}")
    }
}


