package spg.tetrux.view

import spg.tetrux.control.ButtonHandler
import spg.tetrux.control.GridController
import spg.tetrux.control.InputController
import javafx.event.EventHandler
import javafx.geometry.*
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import spg.tetrux.model.GridModel
import spg.tetrux.model.ScoreModel
import spg.tetrux.utility.ResourceManager

class GamePane : BorderPane() {

	companion object {
		const val cellSize = 20.0
		const val wCells = 10
		const val hCells = 20
	}

	val gridModel = GridModel()
	val scoreModel = ScoreModel()

	private val grid = GridPane()
	private val preview = GridPane()

	val gridControl = GridController(
		grid, preview, gridModel, scoreModel
	)
	val inputControl = InputController(
		gridControl
	)

	val pauseScreen = BorderPane().apply {
		this.isVisible = false
	}

	init {
		this.center = StackPane(
			BorderPane().apply {
				this.background = Background(
					BackgroundFill(
						Color.web("#202124"),
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)

				this.top = HBox(
					VeryCoolText("Score: ", 20.0), VeryCoolText(size = 20.0).apply {
						this.textProperty().bind(
							scoreModel.getScoreProperty().asString()
						)
					}
				).apply {
					this.alignment = Pos.CENTER
					this.padding = Insets(20.0)
					this.prefHeight = 50.0
				}

				this.bottom = HBox(

				).apply {
					this.padding = Insets(20.0)
					this.prefHeight = 50.0
				}

				this.right = BorderPane(
					VBox(
						VeryCoolText(
							"Up Next:", 14.0
						).apply {
							this.textAlignment = TextAlignment.CENTER
						},

						preview.apply {
							this.alignment = Pos.CENTER

							with(this.columnConstraints) {
								for (i in 0 until 5)
									this.add(ColumnConstraints().apply {
										this.halignment = HPos.CENTER
									} )
							}

							with(this.rowConstraints) {
								for (i in 0 until 5)
									this.add(RowConstraints().apply {
										this.valignment = VPos.CENTER
									} )
							}
						}
					).apply {
						this.spacing = 30.0
						this.alignment = Pos.TOP_CENTER
						this.isFillWidth = true
					}

				).apply {
					this.padding = Insets(20.0)
					this.prefWidth = 150.0
					this.background = Background(
						BackgroundFill(
							Color.web("#202124"),
							CornerRadii.EMPTY,
							Insets.EMPTY
						)
					)
					this.border = Border(
						BorderStroke(
							Color.web("#35363b"),
							BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY,
							BorderWidths(5.0),
							Insets(10.0)
						)
					)
				}

				this.center = StackPane(
					grid.apply {
						this.alignment = Pos.CENTER
						this.padding = Insets(5.0, 5.0, 5.0, 5.0)

						with(this.columnConstraints) {
							for (i in 0 until wCells) {
								this.add(
									ColumnConstraints().apply {
										this.halignment = HPos.CENTER
									}
								)
							}
						}

						with(this.rowConstraints) {
							for (i in 0 until hCells) {
								this.add(
									RowConstraints().apply {
										this.valignment = VPos.CENTER
									}
								)
							}
						}
					}
				)
			},

			pauseScreen.apply {
				this.background = Background(
					BackgroundFill(
						Color.web("#000000", 0.5),
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)
				this.padding = Insets(30.0)
				this.center = BorderPane().apply {
					this.padding = Insets(10.0)

					this.background = Background(
						BackgroundFill(
							Color.web("#202124"),
							CornerRadii.EMPTY,
							Insets.EMPTY
						)
					)

					this.border = Border(
						BorderStroke(
							Color.web("#35363b"),
							BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY,
							BorderWidths(5.0),
							Insets.EMPTY
						)
					)

					this.top = HBox(
						NiceButton("Resume",
							Image(
								ResourceManager.getRes("img/Resume.png").toExternalForm()
							), ButtonHandler()
						).apply {
							this.background = Background(
								BackgroundFill(
									Color.web("#35363b"),
									CornerRadii.EMPTY,
									Insets.EMPTY
								)
							)
							this.onMouseEntered = EventHandler {  }
							this.onMouseExited = EventHandler {  }
							this.border = Border.EMPTY
							this.userData = "resume"
						}
					).apply {
						this.alignment = Pos.CENTER_LEFT
					}

					this.center = VBox(
						VeryCoolText("Game Paused", 25.0),
						HBox(
							VeryCoolText("Score: ", 25.0), VeryCoolText(size = 25.0).apply {
								this.textProperty().bind(
									scoreModel.getScoreProperty().asString()
								)
							}
						).apply {
							this.alignment = Pos.CENTER
						}
					).apply {
						this.spacing = 30.0
						this.alignment = Pos.CENTER
					}

					this.bottom = HBox(
						NiceButton("Home", Image(
								ResourceManager.getRes("img/Home.png").toExternalForm()
							), ButtonHandler()
						).apply {
							this.userData = "home"
						},

						NiceButton("Restart", Image(
								ResourceManager.getRes("img/Restart.png").toExternalForm()
							), ButtonHandler()
						).apply {
							this.userData = "restart"
						}
					).apply {
						this.spacing = 30.0
						this.alignment = Pos.CENTER
					}
				}
			}
		)
	}
}