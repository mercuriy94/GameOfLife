package ru.inmoso.core.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T : Any> LiveData<T>.observe(target: LifecycleOwner, onChange: (T) -> Unit) {
    observe(target, Observer {
        it?.let(onChange)
    })
}

/**
 * This function is recommended to use for fragment to avoid isMultiple observers
 * regarding to the
 * @see <a href="issue">https://github.com/googlesamples/android-architecture-components/issues/47</a>
 */
fun <T : Any> LiveData<T>.observe(target: Fragment, onChange: (T) -> Unit) {
    observe(target.viewLifecycleOwner, Observer {
        onChange(it)
    })
}

fun <T : Any> MutableLiveData<T>.reduceState(reduce: (T) -> T) {
    value?.let {
        value = reduce(it)
    }
}
