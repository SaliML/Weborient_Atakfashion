package com.weborient.atakfashion.handlers.service

import android.bluetooth.BluetoothManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object PhoneServiceHandler {

    /**
     * Ellenőrzi a hálózati kapcsolat állapotát
     * Mobilkapcsolat, WIFI, Ethernet és VPN kapcsolatokat ellenőrzi
     */
    fun checkNetworkState(context: Context): Boolean{
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)

        capabilities?.let{
            if(it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                return true
            }
            else if(it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return true
            }
            else if(it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                return true
            }
            else if(it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                return true
            }
        }
        return false
    }

    fun checkWifiState(context: Context): Boolean{
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)

        capabilities?.let{
            if(it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return true
            }
        }
        return false
    }

    /**
     * Ellenőrzi a Bluetooth állapotát
     */
    fun checkBluetoothState(context: Context): Boolean{
        val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter = bluetoothManager?.adapter

        bluetoothAdapter?.let{
            if(it.isEnabled){
                return true
            }
        }

        return false
    }
}