package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import cc.anisimov.vlad.letscelebrate.util.load
import kotlinx.android.synthetic.main.fragment_birthday.*

class BirthdayFragment : Fragment() {
    private val viewModel: BirthdayViewModel by activityViewModels()
    private val birthdayFragmentArgs: BirthdayFragmentArgs by navArgs()
    private lateinit var nav: NavController
    
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
        setupUIComponents()
        setupUIOption()
    }

    private fun setupUIComponents() {
        setupAgeIndicator()
        tvTodayNameIs.text = getString(R.string.today_x_old, viewModel.name)
        bClose.setOnClickListener { nav.navigateUp() }
        viewModel.oImageData.observe(viewLifecycleOwner){ newImageData ->
            if (newImageData == null) {
              return@observe
            }
            ivChildImage.load(newImageData)
        }

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
            }
            UIOption.Elephant -> {
                backgroundImageRes = R.drawable.android_elephant_popup_wide
                generalBackgroundColorId = R.color.elephant_bg
            }
            UIOption.Pelican -> {
                backgroundImageRes = R.drawable.android_pelican_popup_wide
                generalBackgroundColorId = R.color.pelican_bg
            }
        }
        ivThemeBackground.setImageResource(backgroundImageRes)
        val resolvedColor = ResourcesCompat.getColor(resources, generalBackgroundColorId, null)
        clContainer.setBackgroundColor(resolvedColor)
    }

}