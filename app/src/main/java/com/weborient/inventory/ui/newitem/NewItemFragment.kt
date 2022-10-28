package com.weborient.inventory.ui.newitem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weborient.inventory.R
import com.weborient.inventory.databinding.FragmentNewItemBinding

class NewItemFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewItemBinding.inflate(layoutInflater)

        return binding.root
    }
}