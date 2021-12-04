package com.gurpreetsk.mobiusplayground.util

fun <T> unsafeLazy(function: () -> T) = lazy(LazyThreadSafetyMode.NONE, function)
