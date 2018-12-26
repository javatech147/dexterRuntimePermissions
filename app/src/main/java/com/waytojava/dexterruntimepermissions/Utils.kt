package com.waytojava.dexterruntimepermissions

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar

/**
 * Created by Manish Kumar on 12/26/2018
 */

fun Context.snackBar(context: Context, message: String) {
    val snackBar =
        Snackbar.make((context as Activity).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    snackBar.show()
}