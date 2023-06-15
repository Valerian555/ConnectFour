package connectfour

//taille du tableau par défaut
val player1 = Players()
val player2 = Players()
val board = Board()

var multipleGame = false

fun main() {
    //Paramétrage du jeu
    println("Connect Four")
    print("First player's name: ")
    player1.name = readln()
    print("Second player's name: ")
    player2.name = readln()

    //modification de la taille du tableau et vérification du format
    board.testValidSizeBoard()

    //création de la liste du tableau
    var boardList = MutableList(board.rows) { MutableList(board.columns) { " " } }

    // choix et validation nombre de jeux
    var numberOfGame = 1

    while (true) {
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")

        val userInput = readln()

        // L'utilisateur a appuyé sur Enter, utilise la valeur par défaut
        if (userInput.isEmpty()) {
            break
        }

        try {
            numberOfGame = userInput.toInt()

            if (numberOfGame == 0) {
                println("Invalid input")
            } else {
                break
            }
        } catch (e: NumberFormatException) {
            println("Invalid input")
        }
    }

    if (numberOfGame > 1) {
        multipleGame = true
    }

    //affichage début de jeu
    println("${player1.name} VS ${player2.name}")
    println("${board.rows} X ${board.columns} board")
    if (multipleGame) {
        println("Total $numberOfGame games")
    }
    var firstMoveGame = player1.name

    for (i in 1..numberOfGame) {
        if (multipleGame) {
            println("Game #$i")
            firstMoveGame = if (i % 2 == 1) player1.name else player2.name
        } else {
            println("Single game")
        }
        board.Display(board.columns, boardList)
        game(player1.name, player2.name, firstMoveGame, boardList)
        boardList = MutableList(board.rows) { MutableList(board.columns) { " " } }
    }
    println("Game over!")
}

fun game(player1Name: String, player2Name: String, currentPlayer: String, boardList: MutableList<MutableList<String>>) {
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
            if (columnChoiceInputIndex0 in 0 until board.columns) {
                var tokenPlaced = false // Variable pour vérifier si le jeton a été placé
                var rowsIndex0 = board.rows - 1 //calqué sur l'index d'une liste

                //placer jeton si emplacement vide sinon placé au-dessus dans la même colonne
                while (rowsIndex0 >= 0 && !tokenPlaced) {
                    if (boardList[rowsIndex0][columnChoiceInputIndex0] == " ") {
                        boardList[rowsIndex0][columnChoiceInputIndex0] =
                            if (currentPlayerOfGames == player1Name) "o" else "*"
                        tokenPlaced = true
                    }
                    rowsIndex0--
                }

                if (!tokenPlaced) {
                    println("Column $columnChoiceInput is full")
                } else {
                    currentPlayerOfGames = if (currentPlayerOfGames == player1Name) player2Name else player1Name

                    board.Display(board.columns, boardList)
                    totalTokensPlaced++

                    if (totalTokensPlaced == board.rows * board.columns) {
                        println("It is a draw")
                        player1.score += 1
                        player2.score += 1
                        break
                    }
                }
            } else {
                println("The column number is out of range (1 - ${board.columns})")
            }
        } catch (e: NumberFormatException) {
            println("Incorrect column number")
        }

        //Victory check
        //horizontal test
        for (i in 0 until board.rows) {
            for (j in 0..board.columns - 4)
                if (boardList[i][j] == boardList[i][j + 1] && boardList[i][j + 1] == boardList[i][j + 2] && boardList[i][j + 2] == boardList[i][j + 3] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                    if (boardList[i][j] == "o") {
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
        for (j in 0 until board.columns) {
            for (i in 0..board.rows - 4)
                if (boardList[i][j] == boardList[i + 1][j] && boardList[i + 1][j] == boardList[i + 2][j] && boardList[i + 2][j] == boardList[i + 3][j] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                    if (boardList[i][j] == "o") {
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
        for (j in 0..board.columns - 4) {
            for (i in 0..board.rows - 4)
                if (boardList[i][j] == boardList[i + 1][j + 1] && boardList[i + 1][j + 1] == boardList[i + 2][j + 2] && boardList[i + 2][j + 2] == boardList[i + 3][j + 3] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                    if (boardList[i][j] == "o") {
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
        for (i in board.rows - 1 downTo 3) {
            for (j in 0..board.columns - 4)
                if (boardList[i][j] == boardList[i - 1][j + 1] && boardList[i - 1][j + 1] == boardList[i - 2][j + 2] && boardList[i - 2][j + 2] == boardList[i - 3][j + 3] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                    if (boardList[i][j] == "o") {
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

    if (multipleGame) {
        println("Score")
        println("$player1Name: ${player1.score} $player2Name: ${player2.score}")
    }
}


