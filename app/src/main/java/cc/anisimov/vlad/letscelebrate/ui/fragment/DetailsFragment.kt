package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.DetailsViewModel
import cc.anisimov.vlad.letscelebrate.ui.common.setupErrorHandling
import cc.anisimov.vlad.letscelebrate.util.DateUtils
import cc.anisimov.vlad.letscelebrate.util.load
import coil.load
import kotlinx.android.synthetic.main.fragment_birthday.*
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.*


class DetailsFragment : Fragment() {
    companion object {
        const val PICK_IMAGE: Int = 1
    }

    private val viewModel: DetailsViewModel by activityViewModels()
    private lateinit var nav: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()
        setupUIComponents()
        setupErrorHandling(viewModel.oError)
        setupTransitions()
    }

    private fun setupTransitions() {
        viewModel.oGoToBirthdayEvent.observe(viewLifecycleOwner) { birthdayData ->
            if (birthdayData == null) {
                return@observe
            }
            val directions = DetailsFragmentDirections.actionDetailsToBirthday(birthdayData)
            nav.navigate(directions)
        }
    }

    private fun setupUIComponents() {
        etName.addTextChangedListener(NameTextWatcher())
        setupDatePicker()
        viewModel.oSubmitEnabled.observe(viewLifecycleOwner) { enable: Boolean? ->
            bSubmit.isEnabled = enable ?: false
        }
        viewModel.oImageData.observe(viewLifecycleOwner) { newImageData ->
            if (newImageData == null) {
                return@observe
            }
            ivBabyPhoto.load(newImageData)
        }
        setupChooseImage()
        bSubmit.setOnClickListener { viewModel.onSubmit() }
    }

    private fun setupChooseImage() {
        ivBabyPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
        }
    }

    private fun setupDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        if (viewModel.oBirthdayDate.value == null) {
            viewModel.oError.value = getString(R.string.some_error)
            return
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                viewModel.oError.value = getString(R.string.some_error)
                return
            }
            viewModel.oImageData.value = ImageData(null, data.data!!)
        }
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

