package com.bridgelabz.fundoonotes.repository.user

import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDatabaseManager
import com.bridgelabz.fundoonotes.repository.user.web_services.UserApi
import com.bridgelabz.fundoonotes.user_module.registration.model.User

class UserRepositoryImplementation(
    private val userApi: UserApi,
    private val userTableManager: UserDatabaseManager
) : UserRepository {
    override fun insertUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUser(email: String): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}