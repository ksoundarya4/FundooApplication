package com.bridgelabz.fundoonotes.repository.user

import android.util.Log
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDatabaseManager
import com.bridgelabz.fundoonotes.repository.user.web_services.UserApi
import com.bridgelabz.fundoonotes.repository.user.web_services.UserLoginModel
import com.bridgelabz.fundoonotes.repository.user.web_services.UserSignUpModel
import com.bridgelabz.fundoonotes.repository.user.web_services.UserSignUpResponseModel
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImplementation(
    private val userApi: UserApi,
    private val userTableManager: UserDatabaseManager
) : UserRepository {
    private val tag = "UserRepository"

    override fun insertUser(user: User) {
        val call = userApi.userSignUp(user.getUserSignUpModel())
        call.enqueue(object : Callback<UserSignUpResponseModel> {
            override fun onFailure(call: Call<UserSignUpResponseModel>, t: Throwable) {
                Log.i(tag, t.message!!)
            }

            override fun onResponse(
                call: Call<UserSignUpResponseModel>,
                response: Response<UserSignUpResponseModel>
            ) {
                if (!response.isSuccessful) {
                    Log.i(tag, response.message())
                    return
                }

                val userSignUpResponse = response.body() as UserSignUpResponseModel
                Log.i(tag, userSignUpResponse.message!!)
            }

        })
    }

    override fun updateUser(user: User) {

    }

    override fun deleteUser(user: User) {

    }

    override fun fetchUser(email: String): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

private fun User.getUserSignUpModel(): UserSignUpModel {
    val userSignUpModel = UserSignUpModel()
    userSignUpModel.firstName = this.firstName
    userSignUpModel.lastName = this.lastName
    userSignUpModel.email = this.email
    userSignUpModel.phoneNumber = this.phoneNumber
    userSignUpModel.password = this.password
    userSignUpModel.role = "user"
    userSignUpModel.service = "advanced"
    return userSignUpModel
}

private fun User.getUserLoginModel(): UserLoginModel {
    val userLoginModel = UserLoginModel()
    userLoginModel.email = this.email
    userLoginModel.password = this.password
    return userLoginModel
}