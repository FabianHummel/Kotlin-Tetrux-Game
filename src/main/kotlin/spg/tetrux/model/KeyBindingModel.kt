package spg.tetrux.model

import javafx.scene.input.KeyCode

class KeyBindingModel {
	private val keyBindings = mutableMapOf<String, KeyCode>(
		"moveRight" to KeyCode.D,
		"moveLeft" to KeyCode.A,
		"rotRight" to KeyCode.W,
		"rotLeft" to KeyCode.S,
		"drop" to KeyCode.SPACE,
	)

	fun getKeyBind(key: String): KeyCode {
		return keyBindings[key] ?: KeyCode.UNDEFINED
	}

	fun setKeyBind(key: String, bind: KeyCode) {
		keyBindings[key] = bind
	}

	fun getIdentifier(key: KeyCode): String? {
		return keyBindings.entries.firstOrNull {
			it.value == key
		}?.key
	}
}