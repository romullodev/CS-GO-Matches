package com.fuzy.csgomatches.ui.loading

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fuzy.csgomatches.BR
import com.fuzy.csgomatches.R
import com.google.android.material.textview.MaterialTextView

private const val EMPTY_RESOURCE = -1

@SuppressLint("Recycle")
class
LoadingDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs) {

    private lateinit var dialog: Dialog

    private lateinit var binding: ViewDataBinding

    private var dialogVisibility: Int = GONE
        set(value) {
            field = value
            if (value == VISIBLE) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

    var bindingData: LoadingDialogViewModel? = null
        set(value) {
            field = value
            value?.let { viewModel ->
                binding.setVariable(BR.viewModel, viewModel)
            }
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.LoadingDialogView, defStyleAttr, 0).use {
            @StyleRes
            val dialogStyle: Int =
                it.getResourceId(R.styleable.LoadingDialogView_dialogLayout, EMPTY_RESOURCE)
            require(dialogStyle != EMPTY_RESOURCE) {
                "Dialog style must be defined"
            }

            @LayoutRes
            val dialogLayout =
                it.getResourceId(R.styleable.LoadingDialogView_dialogLayout, EMPTY_RESOURCE)
            require(dialogLayout != EMPTY_RESOURCE) {
                "Dialog layout must be defined"
            }

            createDialog(context, dialogLayout, dialogStyle)
        }
    }

    private fun createDialog(context: Context, @LayoutRes dialogLayout: Int, @StyleRes dialogStyle: Int) {
        val frameLayout = FrameLayout(context)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            dialogLayout,
            frameLayout,
            true
        )

        dialog = CustomDialog(context, dialogStyle).apply {
            setContentView(frameLayout)
            setCancelable(false)
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

    override fun setVisibility(visibility: Int) {
        dialogVisibility = visibility
    }

    override fun getVisibility() = dialogVisibility

    /**
     * Sometimes while showing the dialog we need to replace its holder fragment or activity. In this case we
     * need to dismiss dialog.
     */
    override fun onDetachedFromWindow() {
        dialog.dismiss()
        super.onDetachedFromWindow()
    }
}

@BindingAdapter("dialogViewModel")
fun LoadingDialogView.bindText(dialogViewModel: LoadingDialogViewModel? = null) {
    bindingData = dialogViewModel
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean?) {
    isVisible?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    }
}