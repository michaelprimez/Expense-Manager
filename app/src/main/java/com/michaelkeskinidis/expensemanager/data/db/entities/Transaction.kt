package com.michaelkeskinidis.expensemanager.data.db.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.room.*
import com.michaelkeskinidis.expensemanager.data.db.converters.LocaleConverter
import com.michaelkeskinidis.expensemanager.data.db.converters.OffsetDateTimeConverter
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

@Entity(
    tableName = "transaction_table",
    foreignKeys = [
        ForeignKey(entity = Account::class, parentColumns = ["name"], childColumns = ["account_name"])
    ],
    indices = [
        Index("account_name")
    ]
)
data class Transaction(
    val amount: BigDecimal,
    val locale: Locale,
    val date: OffsetDateTime,
    val categoryType: String,
    @ColumnInfo(name = "account_name")
    val accountName: String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        BigDecimal(parcel.readString()!!),
        LocaleConverter.toLocale(parcel.readString())!!,
        OffsetDateTimeConverter.toOffsetDateTime(parcel.readString())!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount.toString())
        parcel.writeString(LocaleConverter.fromLocale(locale))
        parcel.writeString(OffsetDateTimeConverter.fromOffsetDateTime(date))
        parcel.writeString(categoryType)
        parcel.writeString(accountName)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}