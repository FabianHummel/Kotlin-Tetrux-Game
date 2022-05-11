package spg.tetrux.utility

import spg.tetrux.control.PieceController
import spg.tetrux.control.TileController
import javafx.scene.paint.Color
import spg.tetrux.model.PieceType

class PieceBuilder {

    private var type: PieceType = PieceType.JLSTZ
    fun type(type: PieceType) = apply { this.type = type }

    private var color: Color = Color.web("#a9c6f5")
    fun color(color: Color) = apply { this.color = color }

    private var position: Pair<Int, Int> = Pair(4, 1)
    fun position(position: Pair<Int, Int>) = apply { this.position = position }

    private var tiles: MutableList<TileController> = PieceController.pieceJ().toMutableList()
    fun tiles(tiles: MutableList<TileController>) = apply { this.tiles = tiles }

    fun build() = PieceController(
        type, tiles, color // ...
    ).apply {
        this.forceMovePiece(
            position
        )
    }
}