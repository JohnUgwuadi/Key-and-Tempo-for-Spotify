package com.johnugwuadi.keyandtempo.ui.search

import android.animation.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.johnugwuadi.keyandtempo.R
import com.johnugwuadi.keyandtempo.data.remote.models.SearchResult
import com.johnugwuadi.keyandtempo.hideKeyboard
import com.johnugwuadi.keyandtempo.showKeyboard
import com.johnugwuadi.keyandtempo.ui.search.SearchViewModel.SearchState
import com.johnugwuadi.keyandtempo.ui.search.epoxy.SearchController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchController: SearchController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search, container, false)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (search_edittext.text.toString().isNotEmpty()) {
            showSearchEditText(false)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEditTextListeners()

        search_card.setOnClickListener {
            showSearchEditText(true)
        }

        search_button.setOnClickListener {
            if (viewModel.query.isNullOrEmpty()) {
                showSearchEditText(true)
            } else {
                search_edittext.requestFocus()
                showKeyboard(requireView())
            }
        }

        search_up_arrow.setOnClickListener {
            search_edittext.setText("")
            search_edittext.clearFocus()
            hideKeyboard(requireActivity())
            hideSearchEditText()
        }

        search_clear_button.setOnClickListener {
            search_edittext.setText("")
            search_edittext.requestFocus()
            showKeyboard(requireView())
        }

        searchController = SearchController(parentFragmentManager)

        viewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer { searchState ->
            val f = when (searchState) {
                is SearchState.Result -> renderResult(searchState.result)
                is SearchState.Error -> renderError(searchState.errorMessage)
                is SearchState.NoResults -> renderNoResults(searchState.failedQuery)
                is SearchState.NoSearch -> renderDefaultState()
            }
        })
        epoxy_recycler_view.setController(searchController)
    }

    private fun renderLoadingState() {
        searchController.setData(null)
        epoxy_recycler_view.visibility = View.VISIBLE
        no_result_error.visibility = View.GONE
        search_error.visibility = View.GONE
    }

    private fun renderResult(searchResult: SearchResult) {
        searchController.setData(searchResult)
        epoxy_recycler_view.visibility = View.VISIBLE
        no_result_error.visibility = View.GONE
        search_error.visibility = View.GONE
    }

    private fun renderError(error: String?) {
        epoxy_recycler_view.visibility = View.GONE
        no_result_error.visibility = View.GONE
        search_error.visibility = View.VISIBLE
        search_error.text = error
    }

    private fun renderDefaultState() {
        epoxy_recycler_view.visibility = View.GONE
        no_result_error.visibility = View.GONE
        search_error.visibility = View.GONE
    }

    private fun renderNoResults(failedQuery: String) {
        no_result_error.text = "Couldn't find any result for \"${failedQuery}\" "
        no_result_error.visibility = View.VISIBLE
        epoxy_recycler_view.visibility = View.GONE
    }

    private fun showSearchEditText(animated: Boolean) {
        //CardView margin removal animation
        val layoutParams = search_card.layoutParams as ConstraintLayout.LayoutParams
        val cardMarginShrink = ValueAnimator
            .ofInt(resources.getDimensionPixelSize(R.dimen.searchCardLayoutMargin), 0)
            .apply {
                addUpdateListener {
                    layoutParams.setMargins(it.animatedValue as Int)
                    search_card.layoutParams = layoutParams
                }
            }

        //CardView radius removal animation
        val cardCornerRadiusShrink = ValueAnimator
            .ofFloat(resources.getDimension(R.dimen.searchCardCornerRadius), 0f)
            .apply {
                addUpdateListener {
                    search_card.radius = it.animatedValue as Float
                }
            }

        //SearchLabel fade out animation
        val searchLabelFadeOut = ObjectAnimator.ofFloat(search_label, "alpha", 0f)
            .apply {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        search_label.visibility = View.GONE
                    }
                })
            }

        val searchEditTextFadeIn = ObjectAnimator.ofFloat(search_edittext, "alpha", 0f, 1f)
        search_edittext.visibility = View.VISIBLE

        val searchUpArrowFadeIn = ObjectAnimator.ofFloat(search_up_arrow, "alpha", 0f, 1f)
        search_up_arrow.visibility = View.VISIBLE

        val enlargeCardView = AnimatorSet().apply {
            play(cardMarginShrink)
            play(cardCornerRadiusShrink)
            play(searchLabelFadeOut)
            duration = if (animated) {
                140
            } else {
                0
            }
            interpolator = LinearInterpolator()
        }

        val fadeInContents = AnimatorSet().apply {
            play(searchEditTextFadeIn)
            play(searchUpArrowFadeIn)
            duration = if (animated) {
                140
            } else {
                0
            }
        }

        AnimatorSet().apply {
            play(enlargeCardView).before(fadeInContents)
            start()
        }

        search_edittext.requestFocus()
        showKeyboard(requireView())
    }

    private fun hideSearchEditText() {
        //Card margin animation
        val layoutParams = search_card.layoutParams as ConstraintLayout.LayoutParams
        val cardMarginAnimation = ValueAnimator
            .ofInt(0, resources.getDimensionPixelSize(R.dimen.searchCardLayoutMargin))
            .apply {
                addUpdateListener {
                    layoutParams.setMargins(it.animatedValue as Int)
                    search_card.layoutParams = layoutParams
                }
            }

        //Card corner animation
        val cardCornerAnimation = ValueAnimator
            .ofFloat(0f, resources.getDimension(R.dimen.searchCardCornerRadius))
            .apply {
                addUpdateListener {
                    search_card.radius = it.animatedValue as Float
                }
            }

        val searchUpArrowFadeOut = ObjectAnimator.ofFloat(search_up_arrow, "alpha", 1f, 0f)
        searchUpArrowFadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                search_up_arrow.visibility = View.GONE
            }
        })

        val searchEditTextFadeOut = ObjectAnimator.ofFloat(search_edittext, "alpha", 1f, 0f)
        searchEditTextFadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                search_edittext.visibility = View.GONE
            }
        })

        val searchLabelFadeIn = ObjectAnimator.ofFloat(search_label, "alpha", 1f)
        search_label.visibility = View.VISIBLE

        val shrinkCardView = AnimatorSet().apply {
            play(cardMarginAnimation)
            play(cardCornerAnimation)
            play(searchLabelFadeIn)
            interpolator = LinearInterpolator()
            duration = 140
        }

        val fadeOutContents = AnimatorSet().apply {
            play(searchUpArrowFadeOut)
            play(searchEditTextFadeOut)
            duration = 100
        }

        AnimatorSet().apply {
            play(fadeOutContents).with(shrinkCardView)
            start()
        }
    }

    @ExperimentalCoroutinesApi
    private fun setupEditTextListeners() {
        setupEditTextClearButtonListener()
        setupEditTextDoneButtonListener()
        setupEditTextBackPressedListener()
        setupEditTextQueryListener()
    }

    private fun setupEditTextDoneButtonListener() {
        search_edittext.setOnEditorActionListener { textView, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    search_edittext.clearFocus()
                    hideKeyboard(requireActivity())
                    if (textView.text.isBlank()) {
                        hideSearchEditText()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setupEditTextClearButtonListener() {
        search_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    search_clear_button.visibility = View.VISIBLE
                } else {
                    search_clear_button.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun setupEditTextBackPressedListener() {
        search_edittext.setOnBackListener(object :
            SearchEditText.OnBackPressedListener {
            override fun onBackPressed() {
                if (search_edittext.text.isNullOrBlank()) {
                    hideSearchEditText()
                }
                search_edittext.clearFocus()
            }
        })
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun setupEditTextQueryListener() {
        createSearchFlow()
            .onEach { renderLoadingState() }
            .debounce(3000)
            .onEach { viewModel.search(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    private fun createSearchFlow(): Flow<String> = callbackFlow {
        val callback = object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                sendBlocking(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
        search_edittext.addTextChangedListener(callback)
        awaitClose { search_edittext?.removeTextChangedListener(callback) }
    }
}