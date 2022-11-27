package ru.bullyboo.core_ui.utils

import android.os.Handler
import android.os.Looper

private typealias OnTick = (Long) -> Unit
private typealias OnEvent = () -> Unit

class Timer private constructor(builder: Builder) {

    companion object {

        private const val DEFAULT_STEP = 1000L
    }

    private val from: Long
    private val to: Long
    private val step: Long
    private var tick: Long = 0

    private val onTickListener: OnTick?
    private val onTickStartListener: OnEvent?
    private val onTickStopListener: OnEvent?
    private val onTickEndListener: OnEvent?
    private val onTickResumeListener: OnTick?
    private val onTickPauseListener: OnTick?

    private val isIncrease: Boolean
    private val timerHandler: Handler

    private val tickRunnable = getRunnable()

    private var isStarted = false
    private var isPaused = false

    init {
        from = builder.from
        to = builder.to
        step = builder.step
        onTickListener = builder.onTickListener
        onTickStartListener = builder.onTickStartListener
        onTickStopListener = builder.onTickStopListener
        onTickEndListener = builder.onTickEndListener
        onTickResumeListener = builder.onTickResumeListener
        onTickPauseListener = builder.onTickPauseListener
        timerHandler = Handler(Looper.getMainLooper())
        isIncrease = builder.from < builder.to
    }


    fun start() {
        if (isStarted) return

        isStarted = true
        onTickStartListener?.invoke()
        tick = from
        nextTick()
    }

    fun stop() {
        if (!isStarted) return

        isStarted = false
        onTickStopListener?.invoke()
        timerHandler.removeCallbacks(tickRunnable)
    }

    fun resume() {
        isPaused = false
        onTickResumeListener?.invoke(tick)
        nextTick()
    }

    fun pause() {
        isPaused = true
        onTickPauseListener?.invoke(tick)
        timerHandler.removeCallbacks(tickRunnable)
    }

    fun isStarted(): Boolean = isStarted

    fun isPaused(): Boolean = isPaused

    private fun getRunnable(): Runnable{
        return Runnable {
            if (tick != to) {
                nextTick()
            } else {
                return@Runnable
            }
            if (isIncrease) {
                if (tick + step <= to) {
                    tick += step
                } else {
                    tick = to
                }
            } else {
                if (tick - step >= to) {
                    tick -= step
                } else {
                    tick = to
                }
            }
            onTickListener?.invoke(tick)
            if (tick == to) {
                onTickEndListener?.invoke()
            }
        }
    }

    private fun nextTick() {
        timerHandler.postDelayed(tickRunnable, step)
    }

    class Builder {

        var from: Long = 0
        var to: Long = 0
        var step: Long
        var onTickListener: OnTick? = null
        var onTickStartListener: OnEvent? = null
        var onTickStopListener: OnEvent? = null
        var onTickEndListener: OnEvent? = null
        var onTickResumeListener: OnTick? = null
        var onTickPauseListener: OnTick? = null

        init {
            step = DEFAULT_STEP
        }

        fun setFrom(from: Long): Builder {
            this.from = from
            return this
        }

        fun setTo(to: Long): Builder {
            this.to = to
            return this
        }

        fun setStep(step: Long): Builder {
            this.step = step
            return this
        }

        fun setOnTickListener(onTickListener: OnTick): Builder {
            this.onTickListener = onTickListener
            return this
        }

        fun setOnTickStartListener(onTickStartListener: OnEvent): Builder {
            this.onTickStartListener = onTickStartListener
            return this
        }

        fun setOnTickStopListener(onTickStopListener: OnEvent): Builder {
            this.onTickStopListener = onTickStopListener
            return this
        }

        fun setOnTickEndListener(onTickEndListener: OnEvent): Builder {
            this.onTickEndListener = onTickEndListener
            return this
        }

        fun setOnTickResumeListener(onTickResumeListener: OnTick): Builder {
            this.onTickResumeListener = onTickResumeListener
            return this
        }

        fun setOnTickPauseListener(onTickPauseListener: OnTick): Builder {
            this.onTickPauseListener = onTickPauseListener
            return this
        }

        fun build(): Timer {
            return Timer(this)
        }
    }
}