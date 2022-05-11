package spg.tetrux.control

import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import spg.tetrux.model.GlobalObject


class InputController(private val gridControl : GridController) : EventHandler<Event> {
    private val bindings = GlobalObject.keyBindingModel

    override fun handle(event: Event) {
        if (event is KeyEvent) {
            when (bindings.getIdentifier(event.code)) {
                "moveRight" -> {
                    gridControl.onMoveRight()
                }

                "moveLeft" -> {
                    gridControl.onMoveLeft()
                }

                "rotRight" -> {
                    gridControl.onRotateRight()
                }

                "rotLeft" -> {
                    gridControl.onRotateLeft()
                }

                "drop" -> {
                    gridControl.onMoveDown()
                }

                else -> {
                    // return;
                }
            }

            when (event.code) {
                KeyCode.ESCAPE -> {
                    gridControl.onPause()
                }

                else -> {
                    // return;
                }
            }
        }

//        else if (event is MouseEvent) {
//            when (event.button) {
//                MouseButton.SECONDARY -> {
//                    gridControl.onRotateRight()
//                }
//
//                MouseButton.PRIMARY -> {
//                    gridControl.onRotateLeft()
//                }
//
//                else -> {
//                    // return;
//                }
//            }
//        }
    }
}