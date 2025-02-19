package com.emil.videovoyage.presentation.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.emil.videovoyage.R
import com.emil.videovoyage.databinding.DialogErrorBinding


class ErrorDialogFragment : DialogFragment() {

    private var _binding: DialogErrorBinding? = null
    private val binding get() = _binding!!

    private var retryAction: (() -> Unit)? = null

    fun setRetryAction(action: () -> Unit) {
        retryAction = action

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogErrorBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext(), R.style.RoundedDialog).apply {
            setContentView(binding.root)

        }

        binding.actionButton.setOnClickListener {
            dismiss()
            retryAction?.invoke()

        }
        binding.errorMessageTextView.text = arguments?.getString("message")

        return dialog
    }




    companion object {
        fun newInstance(message: String, retryAction: () -> Unit): ErrorDialogFragment {
            return ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("message", message)
                }
                setRetryAction(retryAction)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
