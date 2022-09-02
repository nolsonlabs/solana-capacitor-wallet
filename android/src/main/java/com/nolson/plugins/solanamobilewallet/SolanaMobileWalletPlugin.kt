package com.nolson.plugins.solanamobilewallet

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.PluginMethod
import com.getcapacitor.PluginCall
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.nolson.plugins.solanamobilewallet.usecase.ClientTrustUseCase
import kotlinx.coroutines.launch

private val INCOMING_REQUEST: String = "incomingRequest"
private val VERIFICATION_STATE: String = "verificationState"

@CapacitorPlugin(name = "SolanaMobileWallet")
class SolanaMobileWalletPlugin : Plugin() {
    private val application = Application();
    private val implementation: SolanaMobileWallet = SolanaMobileWallet(application)


    @PluginMethod
    fun processLaunch(call: PluginCall) {
        val value = call.getString("uri")
        Log.d("urlString", value.toString())

        val intent = Intent().setData(value.toString().toUri());
        val callingPackage = activity.callingPackage

        implementation.processLaunch(intent, callingPackage, activity)
        activity.lifecycleScope.launch {
            implementation.mobileWalletAdapterServiceEvents.collect {
                    request ->
                when (request) {
                    is SolanaMobileWallet.MobileWalletAdapterServiceRequest.None ->
                        Unit
                    is SolanaMobileWallet.MobileWalletAdapterServiceRequest.AuthorizeDapp -> {

                        when (request.sourceVerificationState) {
                            is ClientTrustUseCase.VerificationInProgress -> {

                                notifyListeners(INCOMING_REQUEST, makeAuthorizeDappResponseObject(
                                    "AuthorizeDapp",
                                    request.request.cluster,
                                    request.request.iconRelativeUri.toString(),
                                    request.request.identityName.toString(),
                                    request.request.identityUri.toString(),
                                    "In Progress"
                                ))
                            }

                            is ClientTrustUseCase.NotVerifiable -> {
                                notifyListeners(INCOMING_REQUEST, makeAuthorizeDappResponseObject(
                                    "AuthorizeDapp",
                                    request.request.cluster,
                                    request.request.iconRelativeUri.toString(),
                                    request.request.identityName.toString(),
                                    request.request.identityUri.toString(),
                                    "Not Verifiable"
                                ))

                            }
                            is ClientTrustUseCase.VerificationFailed -> {
                                notifyListeners(INCOMING_REQUEST, makeAuthorizeDappResponseObject(
                                    "AuthorizeDapp",
                                    request.request.cluster,
                                    request.request.iconRelativeUri.toString(),
                                    request.request.identityName.toString(),
                                    request.request.identityUri.toString(),
                                    "Verification failed"
                                ))

                            }
                            is ClientTrustUseCase.VerificationSucceeded -> {
                                notifyListeners(INCOMING_REQUEST, makeAuthorizeDappResponseObject(
                                    "AuthorizeDapp",
                                    request.request.cluster,
                                    request.request.iconRelativeUri.toString(),
                                    request.request.identityName.toString(),
                                    request.request.identityUri.toString(),
                                    "Verification failed"
                                ))

                            }
                        }
                    }

                    is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SignPayloads -> {
                        request.request.authorizationScope
                        notifyListeners(INCOMING_REQUEST, makeSignPayloadsResponseObject(
                            "SignPayloads",
                            request.request.cluster,
                            request.request.iconRelativeUri.toString(),
                            request.request.identityName.toString(),
                            request.request.identityUri.toString(),
                            request.request.payloads.size
                            )
                        )
                    }

                    is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SignAndSendTransactions -> {
                        notifyListeners(INCOMING_REQUEST, makeSignAndSendTransactionsResponseObject(
                            "SignAndSendTransactions",
                            request.request.cluster,
                            request.request.iconRelativeUri.toString(),
                            request.request.identityName.toString(),
                            request.request.identityUri.toString(),
                            request.request.payloads.size
                        ))
                    }
                    is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SessionTerminated -> {
                        activity.finish()
                    }
                }
            }
        }
    }

    @PluginMethod
    fun userConfirmation(call: PluginCall) {
        val authorized = call.getBoolean("authorized");

        activity.lifecycleScope.launch {
            implementation.mobileWalletAdapterServiceEvents.collect {
                    request ->
                            when (request) {
                                is SolanaMobileWallet.MobileWalletAdapterServiceRequest.None ->
                                    Unit
                                is SolanaMobileWallet.MobileWalletAdapterServiceRequest.AuthorizeDapp -> {
                                    val authDappObj = SolanaMobileWallet.MobileWalletAdapterServiceRequest.AuthorizeDapp(
                                        request.request,
                                        request.sourceVerificationState
                                    )
                                    if (authorized != null) {
                                        implementation.authorizeDapp(authDappObj, authorized, activity)
                                    }
                                }

                                is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SignPayloads -> {
                                    if (authorized == true) {
                                        implementation.signPayloadsSimulateSign(request, activity)
                                    } else {
                                        implementation.signPayloadsDeclined(request)
                                    }
                                }

                                is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SignAndSendTransactions -> {
                                    if (authorized == true) {
                                        implementation.signAndSendTransactionsSimulateSign(request)
                                    } else {
                                        implementation.signAndSendTransactionsDeclined(request)
                                    }
                                }

                                is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SessionTerminated ->
                                    Unit
                            }

                if (request is SolanaMobileWallet.MobileWalletAdapterServiceRequest.SessionTerminated) {
                    activity.finish()
                }

            }

        }

    }

    private fun makeAuthorizeDappResponseObject(
        requestType: String,
        cluster: String,
        iconRelativeUri: String,
        identityName: String,
        identityUri: String,
        verified: String
    ): JSObject {
        val ret = JSObject()
        ret.put("requestType", requestType)
        ret.put("cluster", cluster)
        ret.put("iconRelativeUri", iconRelativeUri)
        ret.put("identityName", identityName)
        ret.put("identityUri", identityUri)
        ret.put("verified", verified)
        return ret
    }

    private fun makeSignPayloadsResponseObject(
        requestType: String,
        cluster: String,
        iconRelativeUri: String,
        identityName: String,
        identityUri: String,
        size: Int
    ): JSObject {
        val ret = JSObject()
        ret.put("requestType", requestType)
        ret.put("cluster", cluster)
        ret.put("iconRelativeUri", iconRelativeUri)
        ret.put("identityName", identityName)
        ret.put("identityUri", identityUri)
        ret.put("payloadCount", size)
        return ret
    }

    private fun makeSignAndSendTransactionsResponseObject(
        requestType: String,
        cluster: String,
        iconRelativeUri: String,
        identityName: String,
        identityUri: String,
        size: Int
    ): JSObject {
        val ret = JSObject()
        ret.put("requestType", requestType)
        ret.put("cluster", cluster)
        ret.put("iconRelativeUri", iconRelativeUri)
        ret.put("identityName", identityName)
        ret.put("identityUri", identityUri)
        ret.put("payloadCount", size)
        return ret
    }


}