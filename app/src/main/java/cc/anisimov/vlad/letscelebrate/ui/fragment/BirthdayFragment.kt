package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.model.Age
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.BirthdayViewModel
import cc.anisimov.vlad.letscelebrate.ui.common.setupErrorHandling
import kotlinx.android.synthetic.main.fragment_birthday.*

class BirthdayFragment : Fragment() {
    private val viewModel: BirthdayViewModel by activityViewModels()
    private val birthdayFragmentArgs: BirthdayFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_birthday, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initWith(birthdayFragmentArgs.birthdayData)
        setupErrorHandling(viewModel.oError)
        setupUIComponents()
    }

    private fun setupUIComponents() {
        viewModel.oAge.observe(viewLifecycleOwner) { age: Age? ->
            if (age == null) {
                return@observe
            }
            val periodName: String
            val imageResId = if (age.years == 0 || (age.years == 1 && age.months == 0)) {
                periodName = resources.getQuantityString(R.plurals.month,age.months)
                if (age.years==1){
                    R.drawable.n12
                }else {
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
                periodName = resources.getQuantityString(R.plurals.year,age.years)
                R.drawable.n1_half
            } else if (age.years < 12) {
                periodName = resources.getQuantityString(R.plurals.year,age.years)
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

}