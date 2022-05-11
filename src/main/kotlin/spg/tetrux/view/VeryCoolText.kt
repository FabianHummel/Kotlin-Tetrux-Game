package spg.tetrux.view

import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import spg.tetrux.utility.ResourceManager
import java.io.File
import java.io.FileInputStream

class VeryCoolText(text : String = "", size : Double = 13.0) : Text(text) {
	init {
		this.fill = Color.WHITE
		this.font = Font.loadFont(
			ResourceManager.getRes("font/pressstart.ttf").toExternalForm(), size
		)
	}
}