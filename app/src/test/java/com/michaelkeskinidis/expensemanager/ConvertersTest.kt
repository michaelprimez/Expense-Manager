/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.michaelkeskinidis.expensemanager

import com.michaelkeskinidis.expensemanager.data.db.converters.BigDecimalConverter
import com.michaelkeskinidis.expensemanager.data.db.converters.LocaleConverter
import com.michaelkeskinidis.expensemanager.data.db.converters.OffsetDateTimeConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.YEAR

class ConvertersTest {

    private val stringDate = "2020-03-23T23:43:22.707+01:00"
    private val date = OffsetDateTime.parse(stringDate)

    private val stringBigDecimal = "-100.12"
    private var bigDecimalNumber = BigDecimal(stringBigDecimal)

    private val stringLocal = "de-DE"
    private val loacale = Locale.GERMANY

    @Test
    fun toOffsetDateTimeCorrectlly() {
        assertEquals(date, OffsetDateTimeConverter.toOffsetDateTime(stringDate))
    }

    @Test
    fun fromOffsetDateTimeCorrectlly() {
        assertEquals(stringDate, OffsetDateTimeConverter.fromOffsetDateTime(date))
    }

    @Test
    fun toBigDecimalCorrectlly() {
        assertEquals(bigDecimalNumber, BigDecimalConverter.toBigDecimal(stringBigDecimal))
    }

    @Test
    fun fromBigDecimalCorrectlly() {
        assertEquals(stringBigDecimal, BigDecimalConverter.fromBigDecimal(bigDecimalNumber))
    }

    @Test
    fun toLocaleCorrectlly() {
        assertEquals(loacale, LocaleConverter.toLocale(stringLocal))
    }

    @Test
    fun fromLocaleCorrectlly() {
        assertEquals(stringLocal, LocaleConverter.fromLocale(loacale))
    }
}