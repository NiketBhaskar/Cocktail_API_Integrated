package com.revia.drinks.viewModel

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.revia.drinksapi.model.Repository
import com.revia.drinksapi.model.response.Drinks
import com.revia.drinksapi.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

public class MainViewModel(val app: Application, val repositoy: Repository) : AndroidViewModel(app) {
    private val _drinksList: MutableLiveData<Resource<Drinks>> = MutableLiveData<Resource<Drinks>>()
    public var drinksList: LiveData<Resource<Drinks>> = _drinksList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    public fun searchDrink(key: String)=viewModelScope.launch { getDrinksDataSearched(key)}

    public suspend fun getDrinksDataSearched(key: String) {
        _drinksList.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(app)) {
                val response = repositoy.getDrinksDataSearched(key)
                _drinksList.postValue(handleCountry(response))
            }else {
                _drinksList.postValue(Resource.Error("No internet connection"))
                Log.d(ContentValues.TAG,"Loading: No Internet")
            }
        }catch (t:Throwable){
            when(t) {
                is IOException -> _drinksList.postValue(Resource.Error("Network Failure $t"))
                else -> _drinksList.postValue(Resource.Error("Conversion error $t"))
            }
        }
    }
    private fun handleCountry(response: Response<Drinks>) :Resource<Drinks>{
        if (response.isSuccessful){
            response.body()?.let { resultCode->
                return Resource.Success(resultCode)
            }
        }
        return Resource.Error(response.message())
    }

    public fun hasInternetConnection(application: Application):Boolean{
        val connectivityManager=application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            Log.d(ContentValues.TAG,"SDK_M")
            val activeNetwork=connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else ->false
            }
        }else {
            Log.d(ContentValues.TAG,"SDK_N")
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}