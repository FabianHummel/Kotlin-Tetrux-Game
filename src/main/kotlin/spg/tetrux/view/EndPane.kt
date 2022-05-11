package spg.tetrux.view

import spg.tetrux.control.ButtonHandler
import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.media.AudioSpectrumListener
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.tetrux.model.GlobalObject
import spg.tetrux.utility.ResourceManager

class EndPane : BorderPane() {
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

			BorderPane(
				VBox(
					UltraCoolText(
						"Game Over", 30.0, Duration.seconds(0.5), Duration.seconds(0.6),
					),
					UltraCoolText(
						"Score: ${GlobalObject.lastScore}", 30.0, Duration.seconds(0.5), Duration.seconds(1.5),
					)
				).apply {
					this.alignment = Pos.CENTER
					this.spacing = 60.0
				}
			).apply {
				this.bottom = HBox(
					NiceButton("Home", Image(
							ResourceManager.getRes("img/Home.png").toExternalForm()
						), ButtonHandler()
					).apply {
						this.userData = "home"
						this.opacity = 0.0
					}.also { btn ->
						PauseTransition(Duration.seconds(0.5)).apply {
							this.onFinished = EventHandler {
								FadeTransition(Duration.seconds(0.3), btn).apply {
									this.toValue = 1.0
									this.play()
								}
							}
							this.play()
						}
					},

					NiceButton("Restart", Image(
							ResourceManager.getRes("img/Restart.png").toExternalForm()
						), ButtonHandler()
					).apply {
						this.userData = "restart"
						this.opacity = 0.0
					}.also { btn ->
						PauseTransition(Duration.seconds(0.8)).apply {
							this.onFinished = EventHandler {
								FadeTransition(Duration.seconds(0.3), btn).apply {
									this.toValue = 1.0
									this.play()
								}
							}
							this.play()
						}
					}
				).apply {
					this.spacing = 30.0
					this.alignment = Pos.CENTER
				}
			}
		)
	}
}