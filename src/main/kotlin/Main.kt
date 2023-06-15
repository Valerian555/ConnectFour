package connectfour

val player1 = Players()
val player2 = Players()
val board = Board()
val game = Game()

var multipleGames = false

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
        multipleGames = true
    }

    //affichage début de jeu
    println("${player1.name} VS ${player2.name}")
    println("${board.rows} X ${board.columns} board")
    if (multipleGames) {
        println("Total $numberOfGame games")
    }

    //lancement mache(s)
    var firstMoveGame = player1.name

    for (i in 1..numberOfGame) {
        if (multipleGames) {
            //changement du joueur qui commence en premier lors d'une nouvelle manche
            firstMoveGame = if (i % 2 == 1) player1.name else player2.name

            println("Game #$i")
        } else {
            println("Single game")
        }
        board.Display(board.columns, boardList)
        game.playAGame(player1.name, player2.name, firstMoveGame, boardList)
        boardList = MutableList(board.rows) { MutableList(board.columns) { " " } }
    }
    println("Game over!")
}

