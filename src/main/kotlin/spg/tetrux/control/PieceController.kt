package spg.tetrux.control

import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import spg.tetrux.model.GlobalObject
import spg.tetrux.model.PieceType
import spg.tetrux.utility.Logger
import spg.tetrux.utility.PieceSupply
import spg.tetrux.view.Cell

class PieceController(private val type: PieceType, private val tiles: MutableList<TileController>, private val color: Color) {

    private val logger = Logger.getLogger("Piece-Control")

    companion object {
        private val JLSTZ_OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0)),
            arrayOf(Pair(0, 0), Pair(1, 0), Pair(0, 0), Pair(-1, 0)),
            arrayOf(Pair(0, 0), Pair(1, -1), Pair(0, 0), Pair(-1, -1)),
            arrayOf(Pair(0, 0), Pair(0, 2), Pair(0, 0), Pair(0, 2)),
            arrayOf(Pair(0, 0), Pair(1, 2), Pair(0, 0), Pair(-1, 2)),
        )

        private val I_OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0), Pair(-1, 0), Pair(-1, 1), Pair(0, 1)),
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(1, 1), Pair(0, 1)),
            arrayOf(Pair(2, 0), Pair(0, 0), Pair(-2, 1), Pair(0, 1)),
            arrayOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1)),
            arrayOf(Pair(2, 0), Pair(0, -2), Pair(-2, 0), Pair(0, 2)),
        )

        private val O_OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0), Pair(0, -1), Pair(-1, -1), Pair(-1, 0)),
        )

        fun pieceJ() = listOf(
            TileController(1, 1), // Center tile
            TileController(0, 0),
            TileController(0, 1),
            TileController(2, 1)
        )

        fun pieceL() = listOf(
            TileController(1, 1), // Center tile
            TileController(2, 0),
            TileController(0, 1),
            TileController(2, 1)
        )

        fun pieceS() = listOf(
            TileController(1, 1), // Center tile
            TileController(1, 0),
            TileController(2, 0),
            TileController(0, 1)
        )

        fun pieceT() = listOf(
            TileController(1, 1), // Center tile
            TileController(1, 0),
            TileController(0, 1),
            TileController(2, 1)
        )

        fun pieceZ() = listOf(
            TileController(1, 1), // Center tile
            TileController(0, 0),
            TileController(1, 0),
            TileController(2, 1)
        )

        fun pieceI() = listOf(
            TileController(2, 1), // Center tile
            TileController(0, 1),
            TileController(1, 1),
            TileController(3, 1)
        )

        fun pieceO() = listOf(
            TileController(0, 1), // Center tile
            TileController(0, 0),
            TileController(1, 0),
            TileController(1, 1)
        )

        val supply = PieceSupply()
    }

    private var rotationIndex = 0
    private var timesMoved = 0

    /**
     * Rotates the piece clockwise or counterclockwise.
     */
    fun rotate(dir: Boolean, offset: Boolean) {
        // Calculate the new rotation index
        val oldIndex = rotationIndex
        rotationIndex += if (dir) 1 else -1
        rotationIndex = mod(rotationIndex, 4)

        // Rotate tiles
        tiles.forEach {
            it.rotate(
                tiles[0].getX(), tiles[0].getY(), dir
            )
        }

        if (offset) {
            if (!offset(oldIndex, rotationIndex)) {
                rotate(!dir, false)
                logger.warn("Piece offset failed, reverting rotation", nl = true)
            }
        }
    }

    /**
     * Performs a movement check and moves the piece by the given amount.
     */
    fun movePiece(offset: Pair<Int, Int>, down: Boolean = false) : Boolean {
        if (!canMovePiece(offset)) {
            if (down) {
                if (timesMoved <= 0 && !GlobalObject.gamePane.gridModel.isGameOver()) {
                    GlobalObject.gamePane.gridModel.gameOver()
                    GlobalObject.gamePane.gridControl.onGameOver()
                } else {
                    logger.warn("Cannot move piece down, fixing!", nl = true)
                    GlobalObject.gamePane.gridModel.fix(this)
                }
            } else {
                logger.warn("Cannot move piece by ${offset}!", nl = true)
            }

            return false
        }

        // Move the tiles to the new position
        tiles.forEach {
            it.move(offset)
        }

        return true
    }

    /**
     * Moves the piece down by one
     */
    fun movePieceDown() : Boolean {
        val moved = movePiece(
            Pair(0, 1), true
        )
        timesMoved++
        return moved
    }

    /**
     * Forcibly moves the piece by the given amount.
     */
    fun forceMovePiece(offset: Pair<Int, Int>) {
        tiles.forEach {
            it.move(offset)
        }
    }

    /**
     * Checks if a piece can be moved.
     * @return true if the piece can be moved, false otherwise
     */
    private fun offset(oldIndex: Int, newIndex: Int) : Boolean {
        val offsetData = when (this.type) {
            PieceType.JLSTZ -> JLSTZ_OFFSET_DATA
            PieceType.I -> I_OFFSET_DATA
            PieceType.O -> O_OFFSET_DATA
        }

        var offsetVal1 : Pair<Int, Int>
        var offsetVal2 : Pair<Int, Int>
        var endOffset = Pair(0, 0)

        var movePossible = false

        for (testIndex in 0 until 5) {
            offsetVal1 = offsetData[testIndex][oldIndex]
            offsetVal2 = offsetData[testIndex][newIndex]
            endOffset = Pair(
                offsetVal1.first - offsetVal2.first,
                offsetVal1.second - offsetVal2.second
            )

            if (canMovePiece(endOffset)) {
                movePossible = true
                break
            }
        }

        if (movePossible) {
            movePiece(endOffset)
        }

        return movePossible
    }

    private fun mod(x: Int, m: Int) : Int {
        return (x % m + m) % m
    }

    private fun canMovePiece(offset: Pair<Int, Int>) : Boolean {
        var canMove = true

        tiles.forEach {
            if (!it.canMove(offset)) {
                canMove = false
            }
        }

        return canMove
    }

    /**
     * Executes code on every tile of the piece.
     */
    fun forEachTile(action: (TileController) -> Unit) {
        tiles.forEach(action)
    }

    /**
     * Removes all given tiles from the piece.
     */
    fun removeTiles(predicate: (TileController) -> Boolean) {
        tiles.removeAll(
            tiles.filter(predicate)
        )
    }

    /**
     * Renders the own target to the given canvas.
     */
    fun render(g: GridPane) {
        tiles.forEach {
            g.add(
                Cell(color), it.getX(), it.getY()
            )
        }
    }

    /**
     * Renders the own target to the given preview grid.
     */
    fun preview(g: GridPane) {
        tiles.forEach {
            g.add(
                Cell(color),
                it.getX() - tiles[0].getX() + 2,
                it.getY() - tiles[0].getY() + 2
            )
        }
    }
}