/**
 * Fundoo Notes
 * @description UserApi interface is used to communicate
 * with web server.
 * @file UserApi.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.user_module.api

import com.bridgelabz.fundoonotes.repository.web_service.user_module.models.UserLoginModel
import com.bridgelabz.fundoonotes.repository.web_service.user_module.models.UserLoginResponseModel
import com.bridgelabz.fundoonotes.repository.web_service.user_module.models.UserSignUpModel
import com.bridgelabz.fundoonotes.repository.web_service.user_module.models.UserSignUpResponseModel
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("user/login")
    fun userLogin(@Body userLoginModel: UserLoginModel): Call<UserLoginResponseModel>

    @POST("user/userSignUp")
    fun userSignUp(@Body userSignUpModel: UserSignUpModel): Call<UserSignUpResponseModel>

    @FormUrlEncoded
    @POST("user/reset-password")
    fun resetPassword(
        @Field("newPassword") newPassword: String,
        @Query("access_token") accessToken: String
    ): Call<String>
}