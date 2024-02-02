package com.revia.drinksapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revia.drinks.viewModel.MainViewModel
import com.revia.drinks.viewModel.MainViewModelFactory
import com.revia.drinksapi.R
import com.revia.drinksapi.adapter.DrinksAdapter
import com.revia.drinksapi.model.Repository
import com.revia.drinksapi.model.response.Drink
import com.revia.drinksapi.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), DrinksAdapter.OnItemClickListener {
    private lateinit var mainViewModel: MainViewModel
    lateinit var drinksAdapter: DrinksAdapter
    private var search: EditText? = null
    private var progressbar: ProgressBar? = null
    lateinit var recyclerview: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_home, container, false)
        progressbar = view?.findViewById(R.id.progress)
        recyclerview=view.findViewById(R.id.recyclerview)
        layoutManager= GridLayoutManager(activity,2)
        recyclerview.layoutManager = layoutManager
        recyclerview.apply {
            itemAnimator= DefaultItemAnimator()
            isNestedScrollingEnabled=false
            setHasFixedSize(true)
        }
        val repository = Repository()
        mainViewModel= ViewModelProvider(
            this,
            MainViewModelFactory(requireActivity().application, repository)
        )[MainViewModel::class.java]
        mainViewModel.searchDrink(searchKey)
        mainViewModel.drinksList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let {
                        Log.d("TAG-debugger", "Observe $it")
                        initRecyclerview(it.drinks)
                    }

                }

                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { message ->
                        Log.d("TAG-debugger", "Error : $message")
                        Toast.makeText(
                            activity,
                            "An error occured: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        })
        search = view?.findViewById(R.id.search)
        var job: Job? = null
        search!!.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(800L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        closeKeyboard()
                        searchKey = editable.toString().trim()
                        mainViewModel.searchDrink(searchKey)
                        mainViewModel.drinksList.observe(viewLifecycleOwner, Observer { response ->
                            when (response) {
                                is Resource.Success -> {
                                    hideProgressbar()
                                    response.data?.let {
                                        Log.d("TAG-debugger", "Observe $it")
                                         initRecyclerview(it.drinks)
                                    }

                                }

                                is Resource.Error -> {
                                    hideProgressbar()
                                    response.message?.let { message ->
                                        Log.d("TAG-debugger", "Error : $message")
                                        Toast.makeText(
                                            activity,
                                            "An error occured: $message",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                is Resource.Loading -> {
                                    showProgressbar()
                                }
                            }
                        })
                    }
                }
            }
        }
        return view
    }
    private fun initRecyclerview(list: ArrayList<Drink>){
        if (list.size>0){
            drinksAdapter= DrinksAdapter(requireActivity(),list,this)
            recyclerview.adapter = drinksAdapter
            drinksAdapter.submitList(list)
        }else{
            Toast.makeText(requireActivity(),"Something is wrong.",Toast.LENGTH_LONG).show()
        }

    }
    fun closeKeyboard() {
        val view: View? = requireActivity().currentFocus
        val imm =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private fun hideProgressbar() {
        progressbar?.visibility = View.GONE
    }

    private fun showProgressbar() {
        progressbar?.visibility = View.VISIBLE
    }

    override fun onItemClick(item: Drink, position: Int) {
        val bundle = Bundle().apply {
            putParcelable("da", item)
        }
        val fragment = DrinkFragment()
        fragment.arguments = bundle
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.HostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    companion object{
        var searchKey: String = "Vodka"
    }
}