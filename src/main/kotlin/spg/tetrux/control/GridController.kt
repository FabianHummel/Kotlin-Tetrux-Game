package spg.tetrux.control

import javafx.animation.PauseTransition
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.tetrux.model.GlobalObject
import spg.tetrux.model.GridModel
import spg.tetrux.model.ScoreModel
import spg.tetrux.model.SoundClip
import spg.tetrux.utility.Logger
import spg.tetrux.utility.ResourceManager
import spg.tetrux.view.Cell
import spg.tetrux.view.EndPane
import spg.tetrux.view.GamePane
import spg.tetrux.view.Window
import java.util.*

class GridController(private val grid : GridPane, private val preview : GridPane, private val model : GridModel, private val score : ScoreModel) {

    private val logger = Logger.getLogger("Grid-Control")

    private var paused = false
    private lateinit var autoMoveDown : PauseTransition

    init {
        renderPreview()
        updatePreview()
        moveDown()
        renderGrid()
    }

    /**
     * Runs when the user presses the left mouse button
     */
    fun onRotateLeft() {
        if (paused)
            return
        logger.log("Rotate left", nl = true)
        model.getCur().rotate(
            dir = false, offset = true
        ).run {
            renderGrid()
        }
    }

    /**
     * Runs when the user presses the right mouse button
     */
    fun onRotateRight() {
        if (paused)
            return
        logger.log("Rotate right", nl = true)
        model.getCur().rotate(
            dir = true, offset = true
        ).run {
            renderGrid()
        }
    }

    /**
     * Runs when the user presses the left arrow key
     */
    fun onMoveLeft() {
        if (paused)
            return
        logger.log("Move left", nl = true)
        model.getCur().movePiece(
            Pair(-1, 0)
        ).run {
            renderGrid()
        }
    }

    /**
     * Runs when the user presses the right arrow key
     */
    fun onMoveRight() {
        if (paused)
            return
        logger.log("Move right", nl = true)
        model.getCur().movePiece(
            Pair(1, 0)
        ).run {
            renderGrid()
        }
    }

    /**
     * Runs when the user presses the down arrow key
     */
    fun onMoveDown() {
        if (paused)
            return
        logger.log("Drop Piece!", nl = true)
        GlobalObject.gamePane.gridModel.getCur().let {
            while (it.movePieceDown())
                continue
        }.run {
            renderGrid()
        }
    }

    fun onPause() {
        paused = !paused

        GlobalObject.gamePane.pauseScreen.isVisible = paused

        if (paused) {
            autoMoveDown.pause()
            logger.log("Paused", nl = true)
        } else {
            autoMoveDown.play()
            logger.log("Resumed", nl = true)
        }
    }

    /**
     * Runs when a new piece can't spawn. The game is over.
     */
    fun onGameOver() {
        logger.warn("Piece cannot spawn, Game Over!", nl = true)

        GlobalObject.soundControl.playSound(
            SoundClip(
                ResourceManager.getRes(
                    "sound/Game Over ${
                        Random().nextInt(1, 4)
                    }.wav"
                ),
                "Game Over Sound"
            )
        )

        GlobalObject.lastScore = score.getScore()

        Window.STAGE.scene = Scene(
            EndPane().apply {
                GlobalObject.endPane = this
            }, 450.0, 600.0
        )
    }

    /**
     * Runs when the piece has reached the bottom of the grid
     */
    fun onFix() {
        logger.log("Fixing Piece", nl = true)

        renderPreview()
        updatePreview()
        checkTetrus()
    }

    /**
     * Sets the current piece to the one in the preview window
     * and generate a new upcoming piece
     */
    private fun updatePreview() {
        preview.let { grid ->
            model.let {
                if (it.getNext() != null)
                    it.setCur(it.getNext()!!)
            }

            PieceController.supply.get()
                .let { piece ->
                    piece.preview(grid)
                    model.setNext(piece)
                }
        }
    }

    /**
     * Checks for tetrus' in the grid
     */
    private fun checkTetrus() {
        for (y in 0 until 20) {
            var tetrus = true
            for (x in 0 until 10)
                if (model.isClear(x, y))
                    tetrus = false

            if (tetrus) {
                logger.log("Tetrus found at Y=$y", nl = true)

                score.onTetris()

                GlobalObject.soundControl.playSound(
                    SoundClip(
                        ResourceManager.getRes(
                            "sound/Piece Fix ${
                                Random().nextInt(1, 4)
                            }.wav"
                        ),
                        "Piece Fix Sound"
                    )
                )

                model.let { grid ->
                    grid.forFixed { piece -> run {
                        piece.forEachTile {
                            grid.rem(
                                it.getX(), it.getY()
                            )
                        }

                        piece.removeTiles {
                            it.getY() == y
                        }

                        piece.forEachTile {
                            if (it.getY() < y) {
                                it.move(
                                    Pair(0, 1)
                                )
                            }

                            grid.add(
                                it.getX(), it.getY()
                            )
                        }
                    } }
                }
            }
        }

        renderGrid()
    }

    /**
     * Moves the current piece down one row and repeats this step
     */
    private fun moveDown() {
        PauseTransition(Duration.millis(400.0)).apply {
            autoMoveDown = this
            this.setOnFinished {
                if (model.isGameOver())
                    return@setOnFinished

                score.onMoveDown()
                model.getCur().movePieceDown()

                moveDown()
                renderGrid()
            }
        }.play()
    }

    /**
     * (Re)renders the visual grid
     */
    private fun renderGrid() {
        Logger.getLogger("Renderer")
            .log("Rendering grid", nl = true)

        // Clear grid
        grid.children.clear()

        for (y in 0 until GamePane.hCells) {
            for (x in 0 until GamePane.wCells) {
                grid.add(
                    Cell(Color.web("#35363b")), x, y
                )
            }
        }

        model.let {
            it.getCur().render(
                grid
            )
            it.forFixed { piece -> piece.render(
                grid
            ) }
        }
    }

    /**
     * (Re)renders the visual up next grid
     */
    private fun renderPreview() {
        preview.children.clear()
        preview.let { grid ->
            for (y in 0 until 4)
                for (x in 0 until 5)
                    grid.add(
                        Cell(Color.web("#35363b")), x, y
                    )
        }
    }
}