package spg.tetrux.utility

import spg.tetrux.control.PieceController
import javafx.scene.paint.Color
import spg.tetrux.model.PieceType
import java.util.*
import java.util.function.Supplier

class PieceSupply : Supplier<PieceController> {

	private val random = Random()

	override fun get(): PieceController {
		return when (random.nextInt(7)) {
			0 -> {
				PieceBuilder()
					.type(PieceType.I)
					.tiles(PieceController.pieceI().toMutableList())
					.color(Color.web("#aae1f2"))
					.build()
			}

			1 -> {
				PieceBuilder()
					.type(PieceType.O)
					.tiles(PieceController.pieceO().toMutableList())
					.color(Color.web("#edeba6"))
					.build()
			}

			2 -> {
				PieceBuilder()
					.type(PieceType.JLSTZ)
					.tiles(PieceController.pieceJ().toMutableList())
					.color(Color.web("#8c8aeb"))
					.build()
			}

			3 -> {
				PieceBuilder()
					.type(PieceType.JLSTZ)
					.tiles(PieceController.pieceL().toMutableList())
					.color(Color.web("#e6b77a"))
					.build()
			}

			4 -> {
				PieceBuilder()
					.type(PieceType.JLSTZ)
					.tiles(PieceController.pieceS().toMutableList())
					.color(Color.web("#7ae683"))
					.build()
			}

			5 -> {
				PieceBuilder()
					.type(PieceType.JLSTZ)
					.tiles(PieceController.pieceT().toMutableList())
					.color(Color.web("#b38ee6"))
					.build()
			}

			6 -> {
				PieceBuilder()
					.type(PieceType.JLSTZ)
					.tiles(PieceController.pieceZ().toMutableList())
					.color(Color.web("#eb8383"))
					.build()
			}

			else -> {
				PieceBuilder()
					.build()
			}
		}
	}
}