package com.bridgelabz.fundoonotes.repository.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDatabaseManager
import com.bridgelabz.fundoonotes.repository.user.web_services.*
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImplementation(
    private val userApi: UserApi,
    private val userTableManager: UserDatabaseManager
) : UserRepository {
    private val tag = "UserRepository"

    override fun insertUser(user: User): LiveData<RegistrationStatus> {
        val registrationStatus = MutableLiveData<RegistrationStatus>()
        val call = userApi.userSignUp(user.getUserSignUpModel())

        call.enqueue(object : Callback<UserSignUpResponseModel> {

            override fun onFailure(call: Call<UserSignUpResponseModel>, t: Throwable) {
                Log.i(tag, t.message!!)
                registrationStatus.value = RegistrationStatus.Failed
            }

            override fun onResponse(
                call: Call<UserSignUpResponseModel>,
                response: Response<UserSignUpResponseModel>
            ) {
                if (!response.isSuccessful) {
                    Log.i(tag, response.message())
                    registrationStatus.value = RegistrationStatus.Failed
                    return
                }

                val userSignUpResponse = response.body() as UserSignUpResponseModel
                Log.i(tag, userSignUpResponse.message!!)
                userTableManager.insert(user)
                registrationStatus.value = RegistrationStatus.Successful
            }
        })

        return registrationStatus
    }

    override fun updateUser(user: User) {

    }

    override fun deleteUser(user: User) {

    }

    override fun fetchUser(email: String, password: String): LiveData<AuthState> {
        val authState = MutableLiveData<AuthState>()
        val user = userTableManager.fetchUser(email)
        if (user != null) {
            if (UserRepositoryUtils.isUserAuthenticated(
                    user = user,
                    email = email,
                    password = password
                )
            ) {

                val call = userApi.userLogin(user.getUserLoginModel())
                call.enqueue(object : Callback<UserLoginResponseModel> {
                    override fun onFailure(call: Call<UserLoginResponseModel>, t: Throwable) {
                        Log.i(tag, t.message!!)
                    }

                    override fun onResponse(
                        call: Call<UserLoginResponseModel>,
                        response: Response<UserLoginResponseModel>
                    ) {

                        if (!response.isSuccessful) {
                            Log.i(tag, response.code().toString())
                            return
                        }

                        val userLoginResponseModel = response.body()
                        val getUserWithServerId = userLoginResponseModel!!.getUser(user)
                        userTableManager.update(user.id!!.toLong(), getUserWithServerId)
                        authState.value = AuthState.AUTH
                    }
                })
            } else {
                authState.value = AuthState.AUTH_FAILED
            }
            authState.value = AuthState.NOT_AUTH
        }
        return authState
    }
}

private fun UserLoginResponseModel.getUser(user: User): User {
    user.id = this.userId
    return user
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