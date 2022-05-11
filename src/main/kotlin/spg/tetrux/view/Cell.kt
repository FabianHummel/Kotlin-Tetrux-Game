package spg.tetrux.view

import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.BorderPane
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color

class Cell(color: Color) : BorderPane() {
    init {
        this.prefWidth = GamePane.cellSize
        this.prefHeight = GamePane.cellSize
        this.background = Background(
            BackgroundFill(
                color,
                CornerRadii(1.0),
                Insets(1.0, 1.0, 1.0, 1.0)
            )
        )
    }
}