package connectfour

class Game {
    fun playAGame(player1Name: String, player2Name: String, currentPlayer: String, boardList: MutableList<MutableList<String>>) {
        var columnChoiceInput: String
        var totalTokensPlaced = 0
        var currentPlayerOfGame = currentPlayer
        var gameOver = false

        while (!gameOver) {
            println("$currentPlayerOfGame's turn:")
            columnChoiceInput = readln()

            if (columnChoiceInput == "end") {
                break
            }

            try {
                if (columnChoiceInput.toInt() - 1 in 0 until board.columns) {
                    var tokenPlaced = false // Variable pour vérifier si le jeton a été placé
                    var rowsIndex0 = board.rows - 1 //calqué sur l'index d'une liste

                    //placer jeton si emplacement vide sinon placé au-dessus dans la même colonne
                    while (rowsIndex0 >= 0 && !tokenPlaced) {
                        if (boardList[rowsIndex0][columnChoiceInput.toInt() - 1] == " ") {
                            boardList[rowsIndex0][columnChoiceInput.toInt() - 1] =
                                if (currentPlayerOfGame == player1Name) "o" else "*"
                            tokenPlaced = true
                        }
                        rowsIndex0--
                    }

                    if (!tokenPlaced) {
                        println("Column $columnChoiceInput is full")
                    } else {
                        //changement de tour dans la partie
                        currentPlayerOfGame = if (currentPlayerOfGame == player1Name) player2Name else player1Name

                        board.display(board.columns, boardList)
                        totalTokensPlaced++

                        //si le board est plein
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
                        addPoints(i,j, player1Name, player2Name, boardList)
                        gameOver = true
                    }
            }

            //vertical test
            for (j in 0 until board.columns) {
                for (i in 0..board.rows - 4)
                    if (boardList[i][j] == boardList[i + 1][j] && boardList[i + 1][j] == boardList[i + 2][j] && boardList[i + 2][j] == boardList[i + 3][j] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                        addPoints(i,j, player1Name, player2Name, boardList)
                        gameOver = true
                    }
            }

            //test oblique descendant
            for (j in 0..board.columns - 4) {
                for (i in 0..board.rows - 4)
                    if (boardList[i][j] == boardList[i + 1][j + 1] && boardList[i + 1][j + 1] == boardList[i + 2][j + 2] && boardList[i + 2][j + 2] == boardList[i + 3][j + 3] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                        addPoints(i,j, player1Name, player2Name, boardList)
                        gameOver = true
                    }
            }

            //test oblique montant
            for (i in board.rows - 1 downTo 3) {
                for (j in 0..board.columns - 4)
                    if (boardList[i][j] == boardList[i - 1][j + 1] && boardList[i - 1][j + 1] == boardList[i - 2][j + 2] && boardList[i - 2][j + 2] == boardList[i - 3][j + 3] && (boardList[i][j] == "*" || boardList[i][j] == "o")) {
                        addPoints(i,j, player1Name, player2Name, boardList)
                        gameOver = true
                    }
            }
        }

        if (multipleGames) {
            println("Score")
            println("$player1Name: ${player1.score} $player2Name: ${player2.score}")
        }

    }
    fun addPoints(i: Int, j: Int, player1Name: String, player2Name: String, boardList: MutableList<MutableList<String>>) {
        if (boardList[i][j] == "o") {
            println("Player $player1Name won")
            player1.score += 2
        } else {
            println("Player $player2Name won")
            player2.score += 2
        }
    }
}