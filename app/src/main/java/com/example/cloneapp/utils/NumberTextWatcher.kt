package com.example.cloneapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*

class NumberTextWatcher(private val et: EditText) : TextWatcher {

    private val df: DecimalFormat
    private val dfnd: DecimalFormat
    private var hasFractionalPart: Boolean = false

    init {
        df = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
        df.isDecimalSeparatorAlwaysShown = true
        dfnd = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
        hasFractionalPart = false
    }

    override fun afterTextChanged(s: Editable) {
        et.removeTextChangedListener(this)

        try {
            val inilen: Int
            val endlen: Int
            inilen = et.text.length
            val v = s.toString().replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
            if (!v.trim { it <= ' ' }.isEmpty()) {
                val n = df.parse(v)
                val cp = et.selectionStart
                if (hasFractionalPart) {
                    et.setText(df.format(n))
                } else {
                    et.setText(dfnd.format(n))
                }
                endlen = et.text.length
                val sel = cp + (endlen - inilen)
                if (sel > 0 && sel <= et.text.length) {
                    et.setSelection(sel)
                } else {
                    // place cursor at the end?
                    et.setSelection(et.text.length - 1)
                }
            }
        } catch (nfe: NumberFormatException) {
            // do nothing?
            nfe.printStackTrace()
        } catch (nfe: ParseException) {
            nfe.printStackTrace()
        }

        et.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasFractionalPart =
            s.toString().contains(df.decimalFormatSymbols.decimalSeparator.toString())
    }

    companion object {
        private val TAG = "NumberTextWatcher"
    }

}