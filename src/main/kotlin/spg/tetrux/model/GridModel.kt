package spg.tetrux.model

import spg.tetrux.control.PieceController
import spg.tetrux.view.GamePane

class GridModel {

	/**
	 * The grid of the game.
	 * This basically serves as the collision model for the fixed pieces.
	 */
	private val pieces = Array(GamePane.wCells) {
		Array(GamePane.hCells) {
			false
		}
	}

	/**
	 * A list of all fixed pieces in the grid.
	 * Add an element to it using the [addFixed] method.
	 */
	private val fixedPieces = mutableListOf<PieceController>()

	/**
	 * The currently falling piece.
	 * There's only one piece at a time.
	 * Set it using the [setCur] method.
	 */
	private var currentPiece = PieceController.supply.get()

	/**
	 * The current game status.
	 */
	private var gameOver = false

	/**
	 * Checks if the game is over.
	 * @return true if the game is over, false otherwise.
	 */
	fun isGameOver() = gameOver

	/**
	 * Sets the game over status.
	 */
	fun gameOver() {
		gameOver = true
	}

	/**
	 * @return the current piece
	 */
	fun getCur() : PieceController {
		return currentPiece
	}

	/**
	 * @param piece the new current piece
	 */
	fun setCur(piece: PieceController) {
		currentPiece = piece
	}

	/**
	 * The upcoming piece in the game.
	 * There's only one of it at the same time.
	 * Set it using the [setNext] method.
	 */
	private var nextPiece : PieceController? = null

	/**
	 * @return the upcoming piece
	 */
	fun getNext() : PieceController? {
		return nextPiece
	}

	/**
	 * @param piece the upcoming piece
	 */
	fun setNext(piece: PieceController) {
		nextPiece = piece
	}

	/**
	 * @return true if the given coordinates are within the grid
	 */
	fun inBounds(x: Int, y: Int): Boolean {
		return x in 0 until GamePane.wCells && y >= 0 && y < GamePane.hCells
	}

	/**
	 * @return true if there is already a tile at the given position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	fun isOccupied(x: Int, y: Int): Boolean {
		return pieces[x][y]
	}

	/**
	 * @return true if there is no tile at the given position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	fun isClear(x: Int, y: Int): Boolean {
		return !isOccupied(x, y)
	}

	/**
	 * Occupies the given position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	fun add(x: Int, y: Int) {
		pieces[x][y] = true
	}

	/**
	 * Clears the tile at the given position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	fun rem(x: Int, y: Int) {
		pieces[x][y] = false
	}

	/**
	 * Adds a piece to the fixed grid
	 * @param piece the piece to add
	 */
	fun addFixed(piece: PieceController) {
		fixedPieces.add(piece)
	}

	/**
	 * Fixes the given piece
	 */
	fun fix(piece: PieceController) {
		addFixed(piece)
		piece.forEachTile {
			add(it.getX(), it.getY())
		}

		GlobalObject.gamePane.gridControl.onFix()
	}

	/**
	 * Executes code for each fixed piece
	 */
	fun forFixed(action: (PieceController) -> Unit) {
		fixedPieces.forEach(action)
	}
}