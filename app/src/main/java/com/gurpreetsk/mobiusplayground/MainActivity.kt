package com.gurpreetsk.mobiusplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gurpreetsk.mobiusplayground.databinding.ActivityMainBinding
import com.gurpreetsk.mobiusplayground.util.unsafeLazy
import com.spotify.mobius.Connection
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.SchedulerWorkRunner
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(), CounterView {

    private val binding by unsafeLazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val counterViewRenderer by lazy {
        CounterViewRenderer(this@MainActivity)
    }

    private lateinit var controller: MobiusLoop.Controller<Int, CounterEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loopFactory = Mobius
            .loop(CounterUpdate(), CounterEffectHandler(this))
            .effectRunner { MainThreadWorkRunner.create() }
            .eventRunner { SchedulerWorkRunner(Schedulers.io()) }

        controller = MobiusAndroid.controller(loopFactory, 0)
        controller.connect(::connectViews)

        if (savedInstanceState != null) {
            controller.replaceModel(savedInstanceState.getInt("model"))
        }
    }

    override fun onStart() {
        super.onStart()
        controller.start()
    }

    override fun onStop() {
        controller.start()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("count", controller.model)
        super.onSaveInstanceState(outState)
    }

    override fun updateCounter(value: Int) {
        binding.counter.text = value.toString()
    }

    private fun connectViews(eventConsumer: Consumer<CounterEvent>): Connection<Int> {
        with(binding) {
            increment.setOnClickListener { eventConsumer.accept(CounterEvent.INCREMENT) }
            decrement.setOnClickListener { eventConsumer.accept(CounterEvent.DECREMENT) }
        }

        return object : Connection<Int> {
            override fun accept(model: Int) {
                counterViewRenderer.render(model)
            }

            override fun dispose() {
                with(binding) {
                    increment.setOnClickListener(null)
                    decrement.setOnClickListener(null)
                }
            }
        }
    }
}
