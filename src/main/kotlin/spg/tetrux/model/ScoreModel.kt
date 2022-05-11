package spg.tetrux.model

import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty

class ScoreModel {
	var score: IntegerProperty = SimpleIntegerProperty()

	fun getScore(): Int {
		return score.get()
	}

	private fun setScore(score: Int) {
		this.score.set(score)
	}

	private fun addScore(score: Int) {
		this.score.set(this.score.get() + score)
	}

	private fun remScore(score: Int) {
		addScore(-score)
		if (getScore() < 0) {
			setScore(0)
		}
	}

	fun getScoreProperty(): IntegerProperty {
		return score
	}

	fun onTetris() {
		addScore(100)
	}

	fun onMoveDown() {
		remScore(2)
	}
}