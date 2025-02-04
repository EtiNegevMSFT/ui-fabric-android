/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.officeuifabric.drawer

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatDialogFragment
import android.view.View

// TODO investigate why over scroll "bow" animation is not showing and fix
// TODO add callbacks for Dismiss etc. See iOS implementation
/**
 * [Drawer] is used for displaying an expanding and collapsing modal dialog inside of a Fragment that retains state.
 */
open class Drawer : AppCompatDialogFragment(), OnDrawerContentCreatedListener {
    companion object {
        private const val CONTENT_LAYOUT_ID = "contentLayoutId"

        /**
         * @param contentLayoutId the layout id of the drawer contents.
         */
        fun newInstance(@LayoutRes contentLayoutId: Int): Drawer {
            val bundle = Bundle()
            bundle.putInt(CONTENT_LAYOUT_ID, contentLayoutId)

            val drawer = Drawer()
            drawer.arguments = bundle
            return drawer
        }
    }

    private var contentLayoutId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = savedInstanceState ?: arguments
        contentLayoutId = bundle?.getInt(CONTENT_LAYOUT_ID) ?: 0

        val drawerDialog = DrawerDialog(context!!)
        drawerDialog.onDrawerContentCreatedListener = this
        drawerDialog.setContentView(contentLayoutId)
        return drawerDialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CONTENT_LAYOUT_ID, contentLayoutId)
    }

    override fun onDrawerContentCreated(drawerContents: View) {
        (parentFragment as? OnDrawerContentCreatedListener)?.onDrawerContentCreated(drawerContents)
        (activity as? OnDrawerContentCreatedListener)?.onDrawerContentCreated(drawerContents)
    }
}