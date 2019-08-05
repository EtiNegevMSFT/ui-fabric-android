/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.officeuifabricdemo.demos

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.microsoft.officeuifabric.persona.AvatarSize
import com.microsoft.officeuifabric.persona.AvatarView
import com.microsoft.officeuifabric.snackbar.Snackbar
import com.microsoft.officeuifabricdemo.DemoActivity
import com.microsoft.officeuifabricdemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_snackbar.*
import java.util.*

class SnackbarActivity : DemoActivity(), View.OnClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_snackbar_single_line.setOnClickListener(this)
        btn_snackbar_single_line_custom_view.setOnClickListener(this)
        btn_snackbar_single_line_action.setOnClickListener(this)
        btn_snackbar_single_line_action_custom_view.setOnClickListener(this)

        btn_snackbar_multiline.setOnClickListener(this)
        btn_snackbar_multiline_custom_view.setOnClickListener(this)
        btn_snackbar_multiline_action.setOnClickListener(this)
        btn_snackbar_multiline_action_custom_view.setOnClickListener(this)
        btn_snackbar_multiline_long_action.setOnClickListener(this)

        btn_snackbar_announcement.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val avatarView = AvatarView(this)
        avatarView.avatarSize = AvatarSize.MEDIUM
        avatarView.name = resources.getString(R.string.persona_name_johnie_mcconnell)

        val thumbnailImageView = ImageView(this)
        val thumbnailBitmap = BitmapFactory.decodeResource(resources, R.drawable.thumbnail_example_32)
        val roundedCornerThumbnailDrawable = RoundedBitmapDrawableFactory.create(resources, thumbnailBitmap)
        roundedCornerThumbnailDrawable.cornerRadius = resources.getDimension(R.dimen.uifabric_snackbar_background_corner_radius)
        thumbnailImageView.setImageDrawable(roundedCornerThumbnailDrawable)

        when (v.id) {
            // Single line

            R.id.btn_snackbar_single_line ->
                Snackbar.make(root_view, getString(R.string.snackbar_single_line)).show()

            R.id.btn_snackbar_single_line_custom_view -> {
                val circularProgress = ProgressBar(this, null, 0, R.style.Widget_UIFabric_CircularProgress_Small)
                circularProgress.indeterminateDrawable.setColorFilter(
                    ContextCompat.getColor(this, R.color.snackbar_circular_progress_drawable),
                    PorterDuff.Mode.SRC_IN
                )

                Snackbar.make(root_view, getString(R.string.snackbar_single_line), Snackbar.LENGTH_LONG)
                    .setCustomView(circularProgress)
                    .show()
            }

            R.id.btn_snackbar_single_line_action ->
                Snackbar.make(root_view, getString(R.string.snackbar_single_line))
                    .setAction(getString(R.string.snackbar_action), View.OnClickListener {
                        // handle click here
                    })
                    .show()

            R.id.btn_snackbar_single_line_action_custom_view ->
                Snackbar.make(root_view, getString(R.string.snackbar_single_line))
                    .setCustomView(avatarView, Snackbar.CustomViewSize.MEDIUM)
                    .setAction(getString(R.string.snackbar_action), View.OnClickListener {
                        // handle click here
                    })
                    .show()

            // Multiline

            R.id.btn_snackbar_multiline ->
                Snackbar.make(root_view, getString(R.string.snackbar_multiline), Snackbar.LENGTH_LONG).show()

            R.id.btn_snackbar_multiline_custom_view -> {
                val doneIconImageView = ImageView(this)
                doneIconImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_done_white))

                Snackbar.make(root_view, getString(R.string.snackbar_multiline), Snackbar.LENGTH_LONG)
                    .setCustomView(doneIconImageView)
                    .show()
            }

            R.id.btn_snackbar_multiline_action -> {
                val snackbar = Snackbar.make(root_view, getString(R.string.snackbar_multiline), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.snackbar_action), View.OnClickListener {
                        // handle click here
                    })

                snackbar.show()

                Timer().schedule(object : TimerTask() {
                        override fun run() {
                            snackbar.view.post {
                                snackbar.setText(getString(R.string.snackbar_description_updated))
                            }
                        }
                    }, 2000)
            }

            R.id.btn_snackbar_multiline_action_custom_view ->
                Snackbar.make(root_view, getString(R.string.snackbar_multiline))
                    .setCustomView(thumbnailImageView, Snackbar.CustomViewSize.MEDIUM)
                    .setAction(getString(R.string.snackbar_action), View.OnClickListener {
                        // handle click here
                    })
                    .show()

            R.id.btn_snackbar_multiline_long_action ->
                Snackbar.make(root_view, getString(R.string.snackbar_multiline))
                    .setAction(getString(R.string.snackbar_action_long), View.OnClickListener {
                        // handle click here
                    })
                    .show()

            // Announcement style

            R.id.btn_snackbar_announcement -> {
                val announcementIconImageView = ImageView(this)
                announcementIconImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_birthday))

                Snackbar.make(root_view, getString(R.string.snackbar_announcement), style = Snackbar.Style.ANNOUNCEMENT)
                    .setCustomView(announcementIconImageView)
                    .setAction(getString(R.string.snackbar_action), View.OnClickListener {
                        // handle click here
                    })
                    .show()
            }
        }
    }
}