/**
 * Fundoo Notes
 * @description RegexUtilTest class to test
 * functions of RegexUtil Class.
 * @file RegexUtilTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes

import com.bridgelabz.fundoonotes.user_module.regex_util.RegexUtil
import org.junit.Assert.assertEquals
import org.junit.Test

class RegexUtilTest {
    private val regexUtil = RegexUtil()

    /**Function to test validateEmail function. */
    @Test
    fun testValidateEmail_isCorrect() {
        val emailOne = "ksoundarya4@gmail.com"
        val emailTwo = "@soundarya@gamil.com"
        assertEquals(true, regexUtil.validateEmail(emailOne))
        assertEquals(false, regexUtil.validateEmail(emailTwo))
    }

    /**Function to test validatePassword function*/
    @Test
    fun testValidatePassword_isCorrect() {
        val passwordOne = "sound23567"
        val passwordTwo = ".Soundarya@23"
        val passwordThree = "arya"
        assertEquals(true, regexUtil.validatePassword(passwordOne))
        assertEquals(true, regexUtil.validatePassword(passwordTwo))
        assertEquals(false, regexUtil.validatePassword(passwordThree))
    }

    /**Function to test validateNamefunction*/
    @Test
    fun testValidateName_isCorrect() {
        val one = "soundarya"
        val two = "Soundarya Krishna"
        val three = "167gscb"
        val four = "fhaisnnaknaa"
        assertEquals(true, regexUtil.validateName(one))
        assertEquals(true, regexUtil.validateName(two))
        assertEquals(false, regexUtil.validateName(three))
        assertEquals(true,regexUtil.validateName(four))
    }

    /**Function to test validatePhone function*/
    @Test
    fun testValidatePhone_isCorrect(){
        val one = "8150080490"
        val two = "815-008-0490"
        val three = "86864876"
        assertEquals(true,regexUtil.validatePhone(one))
        assertEquals(false,regexUtil.validatePhone(two))
        assertEquals(false,regexUtil.validatePhone(three))
    }

    /**Function to test ValidateDOB function*/
    @Test
    fun testValidateDOB_isCOrrect(){
        val date1 = "30/03/2020"
        val date2 = "30-01-2020"
        val date3 = "221220202"
        assertEquals(true,regexUtil.validateDOB(date1))
        assertEquals(true,regexUtil.validateDOB(date2))
        assertEquals(false,regexUtil.validateDOB(date3))
    }
}