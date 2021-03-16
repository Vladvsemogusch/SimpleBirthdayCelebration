package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.DetailsViewModel
import cc.anisimov.vlad.letscelebrate.util.DateUtils
import coil.load
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.*


class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIComponents()
    }

    private fun setupUIComponents() {
        etName.addTextChangedListener(NameTextWatcher())
        setupDatePicker()
        viewModel.oSubmitEnabled.observe(viewLifecycleOwner) { enable: Boolean? ->
            bSubmit.isEnabled = enable ?: false
        }
        viewModel.oImageUrl.observe(viewLifecycleOwner) { newUrl ->
            ivBabyPhoto.load(newUrl)
        }

    }

    private fun setupDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = viewModel.oBirthdayDate.value!!
        dpBirthday.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, dayOfMonth ->
            val date = DateUtils.getDate(dayOfMonth, month, year)
            viewModel.oBirthdayDate.value = date
        }
        dpBirthday.maxDate = viewModel.initialDate.time
        dpBirthday.minDate = viewModel.minDate.time
    }


    inner class NameTextWatcher : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            viewModel.oName.value = editable.toString()
        }

    }
}

