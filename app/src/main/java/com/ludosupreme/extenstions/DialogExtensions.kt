package com.ludosupreme.extenstions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ludosupreme.R


fun showBottomSheetDialog(
    fragmentManager: FragmentManager,
    bottomSheet: BottomSheetDialogFragment
) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    val fragment = fragmentManager.findFragmentByTag(bottomSheet::class.java.simpleName)
    if (fragment != null) {
        fragmentTransaction.remove(fragment)
    }
    fragmentTransaction.addToBackStack(null)
    bottomSheet.show(fragmentTransaction, bottomSheet::class.java.simpleName)
}


fun Fragment.commandDialogYesNo(title: String, message: String, callback: (Int) -> Unit) {
    val dialog = Dialog(requireContext())
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(R.layout.dialog_info_yes_no)
    dialog.setCanceledOnTouchOutside(false)

    val textViewDialogTitle = dialog.findViewById(R.id.textViewDialogTitle) as AppCompatTextView
    textViewDialogTitle.text = title
    val textViewInfo = dialog.findViewById(R.id.textViewInfo) as AppCompatTextView
    textViewInfo.text = message
    val buttonYes = dialog.findViewById(R.id.buttonYes) as AppCompatButton
    buttonYes.setOnClickListener {
        dialog.dismiss()
        callback(1)
    }

    val buttonNo = dialog.findViewById(R.id.buttonNo) as AppCompatButton
    buttonNo.setOnClickListener {
        dialog.dismiss()
        callback(0)
    }
    if (dialog.isShowing.not()) {
        dialog.show()
    }
}
