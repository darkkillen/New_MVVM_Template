package com.darkkillen.archmvvm.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replace(@IdRes idLayout: Int, fragment: Fragment) {
    val fragmentTransaction = this.beginTransaction()
    fragmentTransaction.replace(idLayout, fragment)
    fragmentTransaction.commit()
}

fun FragmentManager.add(@IdRes idLayout: Int, fragment: Fragment) {
    val fragmentTransaction = this.beginTransaction()
    fragmentTransaction.add(idLayout, fragment)
    fragmentTransaction.commit()
}