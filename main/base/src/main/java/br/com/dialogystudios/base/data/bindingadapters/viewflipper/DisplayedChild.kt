package br.com.dialogystudios.base.data.bindingadapters.viewflipper

import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter

@BindingAdapter("displayChild")
fun displayChild(view: ViewFlipper, child: Int) {
    view.displayedChild = child
}