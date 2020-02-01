/**
 * Fundoo NOtes
 * @description DataBaseTest class to test
 * UserDbMangerImpl class and UserDbHelper class.
 * @file DataBaseTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 31/01/2020
 */
@file:Suppress("DEPRECATION")

package com.bridgelabz.fundoonotes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDatabaseManager
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataBaseTest {

    private lateinit var dbHelper: UserDbHelper
    private lateinit var dbManager: UserDatabaseManager
    private var id: Long = 0

    /**To Create Database table called UserEntries*/
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = UserDbHelper(context)
        dbManager = UserDbManagerImpl(dbHelper)
    }

    /**To test whether every user is assigned with
     * unique primary key or not*/
    @Test
    fun insertUsers_testUserEntryId_isIncrementing() {
        val userOne =
            User(
                "soundarya",
                "k",
                "07/10/1997",
                "ksoundarya4@gamil.com",
                "soundarya",
                "8150080490"
            )
        val userTwo =
            User(
                "shabnam",
                "seema",
                "01/01/1996",
                "tasleema@gamil.com",
                "tasleema",
                "8372542547"
            )
        val userOneId = dbManager.insert(userTwo)
        val userTwoId = dbManager.insert(userOne)
        dbHelper.close()
        dbManager.fetch()
        assertEquals(userTwoId, userOneId + 1)
    }

    /**To test inserting function of UserDbManager
     * and check how many columns are present*/
    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInColumnTest() {

        val user =
            User(
                "soundarya",
                "k",
                "07/10/1997",
                "ksoundarya4@gamil.com",
                "soundarya",
                "8150080490"
            )
        id = dbManager.insert(user)
        val cursor = dbManager.fetch()
        val columnNumbers = cursor.columnCount
        dbHelper.close()
        assertEquals(7, columnNumbers)
    }

    /**To Test whether the over sized name are accepted
     * and are stored in User Entry*/
    @Test
    fun setOutOfSizeFirstName_testInsertUSer_shouldNotBeInserted() {

        val user =
            User(
                "soundaryaaaaaaaaaaa",
                "k",
                "07/10/1997",
                "ksoundarya4@gamil.com",
                "soundarya",
                "8150080490"
            )
        id = dbManager.insert(user)
        dbManager.fetch()
        dbHelper.close()
        assertEquals(id, id)
    }

    /**To test the update function of UserDbManagerImpl
     * function*/
    @Test
    @Throws(Exception::class)
    fun updateUserAndReadInColumn_testRowIsUpdated() {
        val user =
            User(
                "shabnam",
                "seema",
                "01/01/1996",
                "tasleema@gamil.com",
                "tasleema",
                "8372542547"
            )
        dbManager.update(2L, user)
        dbHelper.close()
        val cursor = dbManager.fetch()
        dbHelper.close()
        assertEquals(7, cursor.columnCount)
    }

    /**
     * Function to test deleteAll function of
     * UserDbManagerImpl
     */
    @Test
    fun clearDatabaseEntries_testDatabaseManagerDeleteFunction() {
        dbManager.deleteAll()
    }
}