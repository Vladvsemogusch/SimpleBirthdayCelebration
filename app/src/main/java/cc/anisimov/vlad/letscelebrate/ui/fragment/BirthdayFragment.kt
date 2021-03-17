package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.model.Age
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.BirthdayViewModel
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.BirthdayViewModel.*
import cc.anisimov.vlad.letscelebrate.ui.common.setupErrorHandling
import cc.anisimov.vlad.letscelebrate.util.FileUtils
import cc.anisimov.vlad.letscelebrate.util.load
import cc.anisimov.vlad.letscelebrate.util.toPx
import kotlinx.android.synthetic.main.fragment_birthday.*
import kotlinx.android.synthetic.main.fragment_birthday.view.*
import kotlinx.android.synthetic.main.view_button_camera.*
import kotlinx.android.synthetic.main.view_button_camera.view.*


class BirthdayFragment : Fragment() {
    companion object {
        const val CAMERA_VIEW_WIDTH_DP = 32
        const val CAMERA_VIEW_HEIGHT_DP = 32
        const val SCREENSHOT_FILE_NAME = "temp.jpg"
    }

    private val viewModel: BirthdayViewModel by activityViewModels()
    private val birthdayFragmentArgs: BirthdayFragmentArgs by navArgs()
    private lateinit var nav: NavController
    private var imagePlaceholderResId: Int = 0
    private var cameraIconResId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_birthday, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initWith(birthdayFragmentArgs.birthdayData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()
        setupErrorHandling(viewModel.oError)
        setupUIOption()
        setupUIComponents()
    }

    private fun setupUIComponents() {
        setupAgeIndicator()
        tvTodayNameIs.text = getString(R.string.today_x_old, viewModel.name)
        ivClose.setOnClickListener { nav.navigateUp() }
        viewModel.oImageData.observe(viewLifecycleOwner) { newImageData ->
            if (newImageData == null) {
                return@observe
            }
            ivChildImage.load(newImageData, imagePlaceholderResId, viewModel.oError, false)
        }
        rlCameraContainer.post { setupCameraButton() }
        bShare.setOnClickListener { shareScreenshot() }
    }


    private fun setupAgeIndicator() {
        viewModel.oAge.observe(viewLifecycleOwner) { age: Age? ->
            if (age == null) {
                return@observe
            }
            val periodName: String
            val imageResId = if (age.years == 0 || (age.years == 1 && age.months == 0)) {
                periodName = resources.getQuantityString(R.plurals.month, age.months)
                if (age.years == 1) {
                    R.drawable.n12
                } else {
                    when (age.months) {
                        0 -> R.drawable.n0
                        1 -> R.drawable.n1
                        2 -> R.drawable.n2
                        3 -> R.drawable.n3
                        4 -> R.drawable.n4
                        5 -> R.drawable.n5
                        6 -> R.drawable.n6
                        7 -> R.drawable.n7
                        8 -> R.drawable.n8
                        9 -> R.drawable.n9
                        10 -> R.drawable.n10
                        11 -> R.drawable.n11
                        else -> throw IllegalArgumentException("Month ${age.months} doesn't exist")
                    }
                }
            } else if (age.years == 1 && age.months in 6..11) {
                periodName = resources.getQuantityString(R.plurals.year, age.years)
                R.drawable.n1_half
            } else if (age.years < 12) {
                periodName = resources.getQuantityString(R.plurals.year, age.years)
                when (age.years) {
                    0 -> R.drawable.n0
                    1 -> R.drawable.n1
                    2 -> R.drawable.n2
                    3 -> R.drawable.n3
                    4 -> R.drawable.n4
                    5 -> R.drawable.n5
                    6 -> R.drawable.n6
                    7 -> R.drawable.n7
                    8 -> R.drawable.n8
                    9 -> R.drawable.n9
                    10 -> R.drawable.n10
                    11 -> R.drawable.n11
                    12 -> R.drawable.n12
                    else -> throw IllegalArgumentException("Something went horribly wrong")
                }
            } else {
                throw IllegalArgumentException("Ages above 12 are not supported")
            }
            tvPeriodName.text = getString(R.string.period_old, periodName)
            ivAge.setImageResource(imageResId)
        }
    }

    private fun setupUIOption() {
        val backgroundImageRes: Int
        val generalBackgroundColorId: Int
        when (viewModel.uiOption) {
            UIOption.Fox -> {
                backgroundImageRes = R.drawable.android_fox_popup_wide
                generalBackgroundColorId = R.color.fox_bg
                cameraIconResId = R.drawable.camera_icon_green
                imagePlaceholderResId = R.drawable.default_place_holder_green
            }
            UIOption.Elephant -> {
                backgroundImageRes = R.drawable.android_elephant_popup_wide
                generalBackgroundColorId = R.color.elephant_bg
                cameraIconResId = R.drawable.camera_icon_yellow
                imagePlaceholderResId = R.drawable.default_place_holder_yellow
            }
            UIOption.Pelican -> {
                backgroundImageRes = R.drawable.android_pelican_popup_wide
                generalBackgroundColorId = R.color.pelican_bg
                cameraIconResId = R.drawable.camera_icon_blue
                imagePlaceholderResId = R.drawable.default_place_holder_blue
            }
        }
        ivThemeBackground.setImageResource(backgroundImageRes)
        val resolvedColor = ResourcesCompat.getColor(resources, generalBackgroundColorId, null)
        clContainer.setBackgroundColor(resolvedColor)
    }

    private fun setupCameraButton() {
        //  Find photo frame circle perimeter coordinates for 45 degrees
        val cameraButtonView: View =
            layoutInflater.inflate(R.layout.view_button_camera, rlCameraContainer, false)
        val containerWidth = rlCameraContainer.width
        val containerHeight = rlCameraContainer.height
        val cameraViewWidthPx = CAMERA_VIEW_WIDTH_DP.toPx(requireContext())
        val cameraViewHeightPx = CAMERA_VIEW_HEIGHT_DP.toPx(requireContext())
        val params = RelativeLayout.LayoutParams(cameraViewWidthPx, cameraViewHeightPx)
        // Let's place camera view on rlCameraContainer diagonal. rlCameraContainer is a square
        params.leftMargin = containerWidth * 11 / 16 - cameraViewWidthPx / 2
        params.topMargin = (containerHeight - containerHeight * 11 / 16) - cameraViewHeightPx / 2
        rlCameraContainer.addView(cameraButtonView, params)
        cameraButtonView.setOnClickListener { viewModel.onCameraClick() }
        ivCamera.setImageResource(cameraIconResId)
    }

    private fun takeCustomScreenshot(): Bitmap {
        val metrics = resources.displayMetrics
        val bitmap = Bitmap.createBitmap(
            metrics.widthPixels,
            metrics.heightPixels, Bitmap.Config.ARGB_8888
        )
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.fragment_birthday, null)
        prepareScreenshotView(view)
        val canvas = Canvas(bitmap)
        view.measure(
            MeasureSpec.makeMeasureSpec(canvas.width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(canvas.height, MeasureSpec.EXACTLY)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    private fun prepareScreenshotView(view: View) {
        view.tvTodayNameIs.text = tvTodayNameIs.text
        view.ivAge.setImageDrawable(ivAge.drawable)
        view.tvPeriodName.text = tvPeriodName.text
        view.ivChildImage.setImageDrawable(ivChildImage.drawable)
        view.clContainer.background = clContainer.background
        view.ivThemeBackground.setImageDrawable(ivThemeBackground.drawable)
        view.ivClose.visibility = GONE
        view.bShare.visibility = GONE
        //  ivCamera is added programmatically
    }

    private fun shareScreenshot() {
        val bitmap = takeCustomScreenshot()
        val file = FileUtils.storeScreenShot(bitmap, SCREENSHOT_FILE_NAME, requireContext())
        FileUtils.shareImage(file, requireContext())
    }
}