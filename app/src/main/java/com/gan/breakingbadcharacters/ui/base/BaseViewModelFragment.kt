package com.gan.breakingbadcharacters.ui.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.gan.breakingbadcharacters.ui.utils.ImageLoader
import org.koin.android.ext.android.inject

abstract class BaseViewModelFragment : Fragment() {

    val imageLoader: ImageLoader by inject()

    protected fun navigateTo(@IdRes resId: Int? = null, action: NavDirections? = null) {
        view ?: return

        if (resId != null) {
            try {
                Navigation.findNavController(requireView()).navigate(resId)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }

        if (action != null) {
            try {
                findNavController().navigate(action)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }
}
