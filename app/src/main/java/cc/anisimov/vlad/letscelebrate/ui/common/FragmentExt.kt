package cc.anisimov.vlad.letscelebrate.ui.common

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.util.SingleLiveEvent

fun Fragment.setupErrorHandling(oError: SingleLiveEvent<String?>) {
    oError.observe(viewLifecycleOwner) { errorText ->
        if (errorText != null) {
            showSimpleDialog(errorText)
        }
    }
}

fun Fragment.showSimpleDialog(errorText: String?) {
    AlertDialog.Builder(requireContext()).setTitle("Alert")
        .setMessage(errorText)
        .setTitle(R.string.error_title)
        .setPositiveButton(
            "OK"
        ) { dialog, _ -> dialog.dismiss() }
        .show()
}