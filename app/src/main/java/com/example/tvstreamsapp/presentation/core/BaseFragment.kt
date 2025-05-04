package com.example.tvstreamsapp.presentation.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.tvstreamsapp.TVStreamsApp
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    @Inject
    protected lateinit var viewModelsFactory: ViewModelsFactory

    private val component by lazy {
        (requireActivity().application as TVStreamsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bind(inflater, container)
        val view = binding.root
        return view
    }

    protected abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): VB
}