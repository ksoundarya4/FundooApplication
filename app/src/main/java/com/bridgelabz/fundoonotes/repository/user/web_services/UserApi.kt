/**
 * Fundoo Notes
 * @description UserApi interface is used to communicate
 * with web server.
 * @file UserApi.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/login")
    fun userLogin(@Body userLoginModel: UserLoginModel): Call<UserLoginResponseModel>

    @POST("/user/userSignUp")
    fun userSignUp(@Body userSignUpModel: UserSignUpModel): Call<UserSignUpResponseModel>
}