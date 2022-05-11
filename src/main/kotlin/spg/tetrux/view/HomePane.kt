package spg.tetrux.view

import spg.tetrux.control.ButtonHandler
import javafx.animation.*
import javafx.event.EventHandler
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.media.AudioSpectrumListener
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.tetrux.model.GlobalObject
import spg.tetrux.utility.PieceSupply
import spg.tetrux.utility.ResourceManager
import java.util.*

class HomePane : BorderPane() {

	private val supply = PieceSupply()

	private var pieceCounter = 0
	private var piece = supply.get()
	private val grid : GridPane

	init {
		this.padding = Insets(20.0)

		this.background = Background(
			BackgroundFill(
				Color.web("#202124"),
				CornerRadii.EMPTY,
				Insets.EMPTY
			)
		)

		this.center = StackPane(
			HBox().apply {
				this.isFillHeight = false
				this.alignment = Pos.TOP_CENTER
				this.spacing = 20.0

				for (i in 0 until 10)
					this.children.add(
						Pane().apply {
							this.prefHeight = 10.0
							this.prefWidth = 20.0
							this.background = Background(
								BackgroundFill(
									Color.web("#35363b"),
									CornerRadii.EMPTY,
									Insets.EMPTY
								)
							)
						}
					)

				GlobalObject.soundControl.soundMusic.audioSpectrumListener = AudioSpectrumListener { _, _, magnitudes, _ -> run {
					for (i in 0 until 10) {
						(this.children[i] as Pane).prefHeight = (60.0 + magnitudes[i] ) * 4.0
					}
				} }
			},

			HBox(
				ImageView(
					Image(
						ResourceManager.getRes("img/Logo.png").toExternalForm()
					)
				).apply {
					this.fitWidth = 1368 / 3.5
					this.fitHeight = 512 / 3.5
				}
			).apply {
				this.alignment = Pos.TOP_CENTER
			},

			GridPane().apply {
				grid = this
				this.alignment = Pos.CENTER
				this.padding = Insets(5.0, 5.0, 5.0, 5.0)

				with(this.columnConstraints) {
					for (i in 0 until 5) {
						this.add(
							ColumnConstraints().apply {
								this.halignment = HPos.CENTER
							}
						)
					}
				}

				with(this.rowConstraints) {
					for (i in 0 until 5) {
						this.add(
							RowConstraints().apply {
								this.valignment = VPos.CENTER
							}
						)
					}
				}
			}.also {
				updatePiece()
				startAutoUpdate()
			}
		)

		this.bottom = HBox(
			NiceButton("Play", Image(
				ResourceManager.getRes("img/Play.png").toExternalForm()
			), ButtonHandler() ).apply {
				this.userData = "restart"
			},

			NiceButton("Controls", Image(
				ResourceManager.getRes("img/Controls.png").toExternalForm()
			), ButtonHandler() ).apply {
				this.userData = "controls"
			}
		).apply {
			this.spacing = 30.0
			this.alignment = Pos.CENTER
		}
	}

	private fun autoUpdate() {
		PauseTransition(Duration.millis(400.0)).apply {
			this.onFinished = EventHandler {
				startAutoUpdate()
			}
			this.play()
		}
	}

	private fun startAutoUpdate() {
		pieceCounter++
		if (pieceCounter >= 4) {
			pieceCounter = 0
			updatePiece()
		}

		ScaleTransition(Duration.millis(400.0), grid).apply {
			this.fromX = 2.0
			this.fromY = 2.0
			this.toX = 1.8
			this.toY = 1.8
		}.play()

		RotateTransition(Duration.millis(400.0), grid).apply {
			this.interpolator = Interpolator.EASE_OUT
			this.fromAngle = Random().nextDouble(-10.0, 10.0)
			this.toAngle = 0.0
		}.play()

		FadeTransition(Duration.millis(400.0), grid).apply {
			this.interpolator = Interpolator.EASE_OUT
			this.fromValue = 1.0
			this.toValue = 0.5
		}.play()

		autoUpdate()
	}

	private fun updatePiece() {
		piece = supply.get()

		grid.children.clear()
		piece.render(
			grid
		)
	}
}