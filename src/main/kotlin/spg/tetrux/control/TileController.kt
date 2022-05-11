package spg.tetrux.control

import spg.tetrux.model.GlobalObject

class TileController(private var pX: Int, private var pY: Int) {
    companion object {
        private val CLOCKWISE_MAT = arrayOf(
            arrayOf(0, -1),
            arrayOf(1, 0)
        )

        private val COUNTERCLOCKWISE_MAT = arrayOf(
            arrayOf(0, 1),
            arrayOf(-1, 0)
        )
    }

    fun getX(): Int {
        return pX
    }

    fun getY(): Int {
        return pY
    }

    /**
     * Rotates the tile clockwise or counterclockwise
     * @param dir true if clockwise, false if counterclockwise
     * @param cX the x coordinate of the centerpiece
     * @param cY the y coordinate of the centerpiece
     */
    fun rotate(cX : Int, cY : Int, dir : Boolean) {
        val relX = cX - this.pX
        val relY = cY - this.pY

        applyMat(relX, relY,
            if (dir) CLOCKWISE_MAT else COUNTERCLOCKWISE_MAT
        ).also {
            this.pX = it.first + cX
            this.pY = it.second + cY
        }
    }

    /**
     * Moves the tile to the given coordinates
     * @param offset the movement offset
     */
    fun move(offset: Pair<Int, Int>) {
        pX += offset.first
        pY += offset.second
    }

    /**
     * Applies a matrix to a point
     * @param rX relative x coordinate
     * @param rY relative y coordinate
     */
    private fun applyMat(rX : Int, rY : Int, mat : Array<Array<Int>>) : Pair<Int, Int> {
        val nX = rX * mat[0][0] + rY * mat[1][0]
        val nY = rX * mat[0][1] + rY * mat[1][1]
        return Pair(
            nX, nY
        )
    }

    /**
     * Checks if the tile is able to move to the given coordinates
     * @param offset the movement offset
     */
    fun canMove(offset: Pair<Int, Int>) : Boolean {
        if (!GlobalObject.gamePane.gridModel.inBounds(pX + offset.first, pY + offset.second)) {
            return false
        }
        if (!GlobalObject.gamePane.gridModel.isClear(pX + offset.first, pY + offset.second)) {
            return false
        }

        return true
    }
}