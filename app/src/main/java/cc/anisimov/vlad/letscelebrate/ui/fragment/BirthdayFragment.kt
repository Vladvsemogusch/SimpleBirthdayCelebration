package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.viewmodel.BirthdayViewModel

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
    }

}