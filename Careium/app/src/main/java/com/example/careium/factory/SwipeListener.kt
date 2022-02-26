package com.example.careium.factory

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class SwipeListener(context: Context) : GestureDetector.OnGestureListener {
    private var gestureDetector: GestureDetector = GestureDetector(context, this)

    private var x1: Float = 0.0f
    private var x2: Float = 0.0f
    private var y1: Float = 0.0f
    private var y2: Float = 0.0f

    companion object {
        const val SWIPE_THRESHOLD = 150
    }

    private fun getSwipeDirection(event: MotionEvent?): SwipeDirection {
        gestureDetector.onTouchEvent(event)
        when (event?.action) {
            // When swiping starts
            0 -> {
                x1 = event.x
                y1 = event.y
            }
            // When swiping ends
            1 -> {
                x2 = event.x
                y2 = event.y

                val deltaX: Float = x2 - x1
                val deltaY: Float = y2 - y1

                if (abs(deltaX) > SWIPE_THRESHOLD) {
                    return if (x2 > x1) SwipeDirection.RIGHT else SwipeDirection.LEFT
                } else if (abs(deltaY) > SWIPE_THRESHOLD) {
                    return if (y2 > y1) SwipeDirection.DOWN else SwipeDirection.UP
                }
            }
        }

        return SwipeDirection.NONE
    }


    fun isSwipedUp(event: MotionEvent?): Boolean {
        val swipeDirection: SwipeDirection = getSwipeDirection(event)
        return swipeDirection == SwipeDirection.UP
    }

    fun isSwipedDown(event: MotionEvent?): Boolean {
        val swipeDirection: SwipeDirection = getSwipeDirection(event)
        return swipeDirection == SwipeDirection.DOWN
    }

    fun isSwipedLeft(event: MotionEvent?): Boolean {
        val swipeDirection: SwipeDirection = getSwipeDirection(event)
        return swipeDirection == SwipeDirection.LEFT
    }

    fun isSwipedRight(event: MotionEvent?): Boolean {
        val swipeDirection: SwipeDirection = getSwipeDirection(event)
        return swipeDirection == SwipeDirection.RIGHT
    }

    // **************** Overridden Methods (Unimplemented) **************** //
    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

}