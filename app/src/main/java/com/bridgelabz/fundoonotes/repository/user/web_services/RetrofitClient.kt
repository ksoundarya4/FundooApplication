/**
 * Fundoo Notes
 * @description RetrofitClient object returns an instance of
 * retrofit object.
 * @file RetrofitClient
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val baseUrl = "http://fundoonotes.incubation.bridgelabz.com/api"
    private var retrofit: Retrofit? = null

    fun getRetrofitClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}