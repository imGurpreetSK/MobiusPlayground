package com.gurpreetsk.mobiusplayground

class CounterViewRenderer(
    private val view: CounterView
) {

    fun render(model: Int) {
        view.updateCounter(model)
    }
}
