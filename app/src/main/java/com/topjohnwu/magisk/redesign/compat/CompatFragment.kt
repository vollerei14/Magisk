package com.topjohnwu.magisk.redesign.compat

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.topjohnwu.magisk.base.BaseFragment
import com.topjohnwu.magisk.model.events.ViewEvent

abstract class CompatFragment<ViewModel : CompatViewModel, Binding : ViewDataBinding>
    : BaseFragment<ViewModel, Binding>(), CompatView<ViewModel> {

    override val viewRoot: View get() = binding.root
    override val navigation by lazy { compatActivity.navigation }

    private val delegate by lazy { CompatDelegate(this) }

    private val compatActivity get() = requireActivity() as CompatActivity<*, *>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        delegate.onCreate()
    }

    override fun onResume() {
        super.onResume()

        delegate.onResume()
    }

    override fun onEventDispatched(event: ViewEvent) {
        delegate.onEventExecute(event, this)
    }

}