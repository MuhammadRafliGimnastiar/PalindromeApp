package com.gimnastiar.palindromeapp.ui.polindrom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.gimnastiar.palindromeapp.R
import com.gimnastiar.palindromeapp.data.local.entity.Palindrome
import com.gimnastiar.palindromeapp.databinding.FragmentPalindromeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PalindromeFragment : Fragment() {

    private var _binding: FragmentPalindromeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PalindromeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPalindromeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text: String? = arguments?.getString("text")
        val isPalindrome: Boolean? = arguments?.getBoolean("isPalindrome")

        if (text != null && isPalindrome != null)
            setupData(text, isPalindrome)


        viewModel.getAllData()
        observeData()
        onClick()
    }

    private fun onClick() = with(binding) {
        appBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                Log.i("DATA POLIN", data.size.toString())
                binding.rvData.visibility = View.VISIBLE
                setupDataView(data)
            } else {
                binding.rvData.visibility = View.GONE
            }
        }
    }

    private fun setupDataView(data: List<Palindrome>) = with(binding){
        val adapter = LocalPalindromeAdapter()
        adapter.setOnItemClickCallback(object: LocalPalindromeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Palindrome) {
                viewModel.deleteData(data.id)
                Log.i("DATA POLIN", "DELETE DATA ${data.id}")
            }
        })
        adapter.submitList(data)

        rvData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvData.adapter = adapter
    }

    private fun setupData(text: String, isPalindrome: Boolean) = with(binding) {
        etInput.setText(text)
        tvResponse.text = if (isPalindrome) "$text, IS POLINDROME" else "\"$text, is NOT POLINDROME\""

        btnSave.setOnClickListener {
            viewModel.inputData(text, isPalindrome)
        }
    }

}