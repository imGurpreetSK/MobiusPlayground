package com.gurpreetsk.mobiusplayground

import android.content.Context
import android.widget.Toast
import com.spotify.mobius.rx3.RxMobius
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import java.lang.ref.WeakReference

object ReactiveCounterEffectHandler {
    fun get(context: WeakReference<Context>): ObservableTransformer<CounterEffect, CounterEvent> = RxMobius
        .subtypeEffectHandler<CounterEffect, CounterEvent>()
        .addAction(
            CounterEffect.NEGATIVES_NOT_ALLOWED::class.java,
            {
                context.get()?.let {
                    Toast
                        .makeText(it, "Negatives not allowed", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            AndroidSchedulers.mainThread()
        )
        .build()
}
