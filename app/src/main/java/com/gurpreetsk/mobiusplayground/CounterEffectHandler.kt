package com.gurpreetsk.mobiusplayground

import android.content.Context
import android.widget.Toast
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

class CounterEffectHandler(
    private val context: Context
) : Connectable<CounterEffect, CounterEvent> {
    override fun connect(output: Consumer<CounterEvent>): Connection<CounterEffect> {
        return object : Connection<CounterEffect> {
            override fun accept(value: CounterEffect) {
                Toast
                    .makeText(context, "Negatives not allowed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun dispose() {}
        }
    }
}
