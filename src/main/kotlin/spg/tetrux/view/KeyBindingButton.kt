package spg.tetrux.view

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import spg.tetrux.model.GlobalObject

class KeyBindingButton(private var identifier: String) : BorderPane() {

	private val text: VeryCoolText
	private var content: String
	private var canPress = false

	private val bindings = GlobalObject.keyBindingModel

	init {
		this.prefWidth = 150.0
		this.center = VeryCoolText(
			bindings.getKeyBind(identifier).getName()
		).apply {
			this@KeyBindingButton.text = this
			this@KeyBindingButton.content = this.text
		}
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

		this.onKeyPressed = EventHandler {
			if (canPress) {
				var key = it.code
				if (key == KeyCode.ESCAPE)
					key = KeyCode.UNDEFINED

				this.content = key.getName()
				this.bindings.setKeyBind(
					identifier, key
				)
			}
		}

		this.onMouseEntered = EventHandler {
			this.requestFocus()
			this.canPress = true
			this.text.text = "<Change>"
			this.background = Background(
				BackgroundFill(
					Color.web("#2b2d2f"),
					CornerRadii.EMPTY,
					Insets.EMPTY
				)
			)
		}

		this.onMouseExited = EventHandler {
			this.isFocused = false
			this.canPress = false
			this.text.text = this.content
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