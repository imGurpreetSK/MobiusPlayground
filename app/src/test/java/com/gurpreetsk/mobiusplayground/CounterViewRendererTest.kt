package com.gurpreetsk.mobiusplayground

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class CounterViewRendererTest {

    @Test
    fun `render updated count`() {
        val view = mock<CounterView>()
        val renderer = CounterViewRenderer(view)

        renderer.render(45)

        verify(view).updateCounter(45)
    }
}