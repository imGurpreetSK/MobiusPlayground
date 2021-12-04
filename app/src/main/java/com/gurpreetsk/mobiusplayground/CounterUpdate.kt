package com.gurpreetsk.mobiusplayground

import com.spotify.mobius.Next
import com.spotify.mobius.Update

class CounterUpdate : Update<Int, CounterEvent, CounterEffect> {
    override fun update(model: Int, event: CounterEvent): Next<Int, CounterEffect> {
        return when (event) {
            CounterEvent.INCREMENT -> Next.next(model + 1)
            CounterEvent.DECREMENT -> {
                if (model <= 0) return Next.dispatch(setOf(CounterEffect.NEGATIVES_NOT_ALLOWED))

                Next.next(model - 1)
            }
        }
    }
}
