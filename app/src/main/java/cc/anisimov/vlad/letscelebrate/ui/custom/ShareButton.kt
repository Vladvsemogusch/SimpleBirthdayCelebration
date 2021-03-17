package cc.anisimov.vlad.letscelebrate.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import cc.anisimov.vlad.letscelebrate.R

class ShareButton : LinearLayout {
    lateinit var tvShareButtonTitle: TextView

    constructor(context: Context) : super(context) {
        initializeViews(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeViews(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        initializeViews(context, attrs)
    }

    private fun initializeViews(context: Context, attrs: AttributeSet?) {

        isClickable = true
        isFocusable = true
        background = ResourcesCompat.getDrawable(resources, R.drawable.btn_share, null)
        var titleText: String? = null
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.ShareButton, 0, 0
            )
            titleText = a.getString(R.styleable.ShareButton_titleText)
            a.recycle()
        }
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_view_share_button, this, true)
        tvShareButtonTitle = findViewById(R.id.tvShareButtonTitle)
        tvShareButtonTitle.text = titleText
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    // Consume all clicks
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

}