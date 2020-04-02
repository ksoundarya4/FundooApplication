package com.bridgelabz.fundoonotes.repository.user

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDatabaseManager
import com.bridgelabz.fundoonotes.repository.user.web_services.*
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImplementation(
    private val userApi: UserApi,
    private val userTableManager: UserDatabaseManager,
    private val preferences: SharedPreferences
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

        val call = userApi.userLogin(getUserLoginModel(email, password))
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
                    authState.value = AuthState.AUTH_FAILED
                    return
                }

                val userLoginResponseModel = response.body()
                addAccessTokenToPreference(preferences, userLoginResponseModel!!.id!!)
                updateLocalDb(userLoginResponseModel = userLoginResponseModel)
                authState.value = AuthState.AUTH
            }
        })

        return authState
    }

    override fun fetchUserFromLocalDb(email: String): User {
        val user = userTableManager.fetchUser(email)
        return user!!
    }

    private fun insertUserToLocalDb(userLoginResponseModel: UserLoginResponseModel) {
        val userTobeInserted = userLoginResponseModel.getUser()
        userTableManager.insert(userTobeInserted)
    }

    private fun updateLocalDb(userLoginResponseModel: UserLoginResponseModel) {
        val email = userLoginResponseModel.email
        val user = userTableManager.fetchUser(email!!)
        if (user != null)
            updateUserInLocalDb(user, userLoginResponseModel)
        else
            insertUserToLocalDb(userLoginResponseModel)
    }

    private fun updateUserInLocalDb(user: User, userLoginResponseModel: UserLoginResponseModel) {
        val userTobeUpdated = userLoginResponseModel.getUser()
        userTableManager.update(user.id!!.toLong(), userTobeUpdated)
    }

    private fun addAccessTokenToPreference(
        preferences: SharedPreferences,
        accessToken: String
    ) {
        FundooNotesPreference.editPreference(preferences, "access_token", accessToken)
    }

    private fun getUserLoginModel(email: String, password: String): UserLoginModel {
        val userLoginModel = UserLoginModel()
        userLoginModel.email = email
        userLoginModel.password = password
        return userLoginModel
    }
}

/**
 * Extension Function of UserLoginResponseModel data class.
 * @returns User
 */
private fun UserLoginResponseModel.getUser(): User {
    val firstName = this.firstName
    val lastName = this.lastName
    val email = this.email
    val image = this.imageUrl
    val userId = this.userId
    val user = User(
        firstName = firstName!!,
        lastName = lastName!!,
        email = email!!
    )
    user.image = image
    user.userId = userId
    return user
}

/**
 * Extension Function of User data class.
 * @return UserSignUpModel
 */
private fun User.getUserSignUpModel(): UserSignUpModel {
    val userSignUpModel = UserSignUpModel()
    userSignUpModel.firstName = this.firstName
    userSignUpModel.lastName = this.lastName
    userSignUpModel.email = this.email
    userSignUpModel.phoneNumber = this.phoneNumber
    userSignUpModel.password = this.password
    userSignUpModel.role = "user"
    userSignUpModel.service = "advanced"
    userSignUpModel.imageUrl = this.image
    return userSignUpModel
}

