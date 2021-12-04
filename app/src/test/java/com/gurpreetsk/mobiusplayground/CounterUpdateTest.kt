package com.gurpreetsk.mobiusplayground

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CounterUpdateTest {

    @ParameterizedTest
    @ValueSource(ints = [1, 20, 700, 140399])
    fun `should increment value when INCREMENT event is received`(model: Int) {
        val brains = CounterUpdate()
        val output = brains.update(model, CounterEvent.INCREMENT)

        assertThat(output.modelUnsafe())
            .isEqualTo(model + 1)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 200, 7450, 140399])
    fun `should decrement value when DECREMENT event is received`(model: Int) {
        val brains = CounterUpdate()
        val output = brains.update(model, CounterEvent.DECREMENT)

        assertThat(output.modelUnsafe())
            .isEqualTo(model - 1)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -200, -74500])
    fun `should never decrement when value is 0 or less and DECREMENT event is received`(model: Int) {
        val brains = CounterUpdate()
        val output = brains.update(model, CounterEvent.DECREMENT)

        assertThat(output.hasModel())
            .isFalse()
        assertThat(output.effects())
            .isEqualTo(setOf(CounterEffect.NEGATIVES_NOT_ALLOWED))
    }
}
