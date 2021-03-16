package cc.anisimov.vlad.letscelebrate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.ui.domain.viewmodel.DetailsViewModel


class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }
}