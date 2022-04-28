package ca.qc.cstj.tp2.core

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

fun <T> MutableLiveData<T>.notify() {
    this.value = this.value
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.notifyAllItemChanged() {
    notifyItemRangeChanged(0, itemCount)
}

var TextInputLayout.text
    get() = editText!!.text.toString()
    set(value) = editText!!.setText(value)


fun ImageView.loadFromResource(context: Context, imageName:String) {
    val imageId = resources.getIdentifier(imageName, "drawable", context.packageName)
    this.setImageResource(imageId)
}


fun Context.getStringWithIdentifier(identifier:String) : String {
    val res = this.resources
    return res.getString(res.getIdentifier(identifier.lowercase(), "string", packageName))
}

