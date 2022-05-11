package spg.tetrux.model

import spg.tetrux.control.SoundController
import spg.tetrux.view.ControlsPane
import spg.tetrux.view.EndPane
import spg.tetrux.view.GamePane
import spg.tetrux.view.HomePane

class GlobalObject {
	companion object {
		val soundControl = SoundController()
		val keyBindingModel = KeyBindingModel()

		lateinit var gamePane : GamePane
		lateinit var endPane : EndPane
		lateinit var homePane : HomePane
		lateinit var controlsPane : ControlsPane

		var lastScore : Int = 0
	}
}