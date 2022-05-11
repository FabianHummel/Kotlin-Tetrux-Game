package spg.tetrux.view

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import spg.tetrux.utility.ResampledImage


class NiceButton(text: String, graphic: Image, onClick: EventHandler<MouseEvent>) : HBox() {
	init {
		this.padding = Insets(10.0)
		this.spacing = 10.0

		this.children.addAll(
			VeryCoolText(text),
			ImageView(
				ResampledImage.resample(
					graphic
				)
			).apply {
				this.fitWidth = 15.0
				this.fitHeight = 15.0
			}
		)

		this.onMouseClicked = onClick
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
				BorderWidths(5.0)
			)
		)

		this.onMouseEntered = EventHandler {
			this.background = Background(
				BackgroundFill(
					Color.web("#2b2d2f"),
					CornerRadii.EMPTY,
					Insets.EMPTY
				)
			)
		}

		this.onMouseExited = EventHandler {
			this.background = Background(
				BackgroundFill(
					Color.web("#202124"),
					CornerRadii.EMPTY,
					Insets.EMPTY
				)
			)
		}
	}
}