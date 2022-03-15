package com.example.core_ui.util

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Used to pass ViewBinding for a fragment
 */
typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * Used to pass ViewBinding for an activity
 */
typealias ActivityInflate<T> = (LayoutInflater) -> T
