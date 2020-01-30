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

    /**
     * Function to test validateEmail function.
     */
    @Test
    fun getInstanceRegexUtil_testValidateEmail_isCorrect(){
        val regexUtil = RegexUtil()
        val emailOne = "ksoundarya4@gmail.com"
        val emailTwo = "@soundarya@gamil.com"
        assertEquals(true,regexUtil.validateEmail(emailOne))
        assertEquals(false,regexUtil.validateEmail(emailTwo))
    }
}