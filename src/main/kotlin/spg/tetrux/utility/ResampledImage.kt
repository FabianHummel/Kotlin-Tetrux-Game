package spg.tetrux.utility

import javafx.scene.image.Image
import javafx.scene.image.WritableImage

class ResampledImage() {
	companion object {
		fun resample(input : Image) : Image {
			val scaleFactor = 10
			val w = input.width.toInt()
			val h = input.height.toInt()
			val output = WritableImage(
				w * scaleFactor,
				h * scaleFactor
			)
			val reader = input.pixelReader
			val writer = output.pixelWriter
			for (y in 0 until h) {
				for (x in 0 until w) {
					val argb = reader.getArgb(x, y)
					for (dy in 0 until scaleFactor) {
						for (dx in 0 until scaleFactor) {
							writer.setArgb(x * scaleFactor + dx, y * scaleFactor + dy, argb)
						}
					}
				}
			}
			return output
		}
	}
}