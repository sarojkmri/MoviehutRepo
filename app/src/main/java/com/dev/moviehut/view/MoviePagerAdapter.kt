package com.dev.moviehut.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlin.reflect.KClass

class MoviePagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments =
        listOf(MovieListFragment.newInstance(), MovieDetailsFragment.newInstance())

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.count()
    }

    fun <T : Any> getFragmentPosition(fragmentClass: KClass<T>): Int {
        return fragments.indexOfFirst {
            it::class == fragmentClass
        }

    }
}