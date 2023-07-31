package com.tengyeekong.githubusers.ui.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * Network Utility to detect availability or unavailability of Internet connection
 */
object NetworkUtils : ConnectivityManager.NetworkCallback() {

    private val networkStateFlow = MutableStateFlow<Boolean?>(null)

    /**
     * Init network listener to observe for network changes.
     */
    fun initNetworkListener(context: Context) {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.registerDefaultNetworkCallback(this)

            var isConnected = false

            // Retrieve current status of connectivity
            val networkCapability =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ))
                ) {
                    isConnected = isUrlAccessible()
                }
            }

            networkStateFlow.value = isConnected
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
    }

    override fun onAvailable(network: Network) {
        try {
            networkStateFlow.value = isUrlAccessible()
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        try {
            val hasConnectivity =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ))
            networkStateFlow.value = if (hasConnectivity) isUrlAccessible() else false
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
    }

    override fun onLost(network: Network) {
        networkStateFlow.value = false
    }

    private fun isUrlAccessible(url: String? = "1.1.1.1"): Boolean {
        val ipAddress = InetAddress.getByName(url)
        return !ipAddress.equals("")
    }

    fun getNetworkStateFlow() = networkStateFlow
}
