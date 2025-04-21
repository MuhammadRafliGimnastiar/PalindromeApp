package com.gimnastiar.palindromeapp.ui.input

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.gimnastiar.palindromeapp.R
import com.gimnastiar.palindromeapp.databinding.FragmentInputBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlin.properties.Delegates

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    private var isPalindromeData by Delegates.notNull<Boolean>()

    private var doubleBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValidation()
        clearAction()
        backAction()
    }

    private fun clearAction() = with(binding){
        btnReset.setOnClickListener {
            if (etInput.text != null) {
                etInput.setText("")
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun observeValidation() {
        val inputStream = validatePalindrome()
        inputStream.subscribe { isPalindrome ->
            showPalindromeAllert(isPalindrome)
            isPalindromeData = isPalindrome
        }

        val btnStream = validateButton()
        btnStream.subscribe { isNotNull ->
            btnClickAction(isNotNull)
        }
    }

    private fun btnClickAction(notNull: Boolean) = with(binding) {
        btnSubmit.setOnClickListener {
            if (notNull) {
                //action to detail
                val text = etInput.text.toString()
                val action  = InputFragmentDirections.actionInputFragmentToPalindromeFragment(text = text, isPalindrome = isPalindromeData)
                findNavController().navigate(action)
            } else {
                showToast("Input Text First!")
            }
        }

        if (!notNull) tvResponse.setText("")
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun validateButton(): Observable<Boolean> {
        return RxTextView.textChanges(binding.etInput)
            .skipInitialValue()
            .map { input ->
                input.isNotEmpty()
            }
    }

    private fun showPalindromeAllert(isPalindrome: Boolean) = with(binding) {
        val text = binding.etInput.text
        tvResponse.text = if (isPalindrome) "$text, IS POLINDROME" else "\"$text, is NOT POLINDROME\""
    }

    private fun validatePalindrome(): Observable<Boolean> {
        return RxTextView.textChanges(binding.etInput)
            .skipInitialValue()
            .map { input ->
                val data = input.toString().lowercase()
                data == data.reversed()
            }
    }

    private fun backAction() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                with(binding) {
                    if (doubleBackPressed) {
                        requireActivity().finish()
                    } else {
                        doubleBackPressed = true
                        Toast.makeText(requireContext(),
                            getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackPressed = false
                        }, 2000)
                    }
                }
            }
        })
    }
}