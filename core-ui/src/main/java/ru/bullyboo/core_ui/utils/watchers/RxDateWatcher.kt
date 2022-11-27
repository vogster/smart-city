package ru.bullyboo.core_ui.utils.watchers

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.EditText
import java.util.*

class RxDateWatcher: RxWatcher<String>(){

    companion object {
        private const val MAX_LINES = 1
        private const val MAX_LENGTH = 10
    }

    private var currentYearEnding = Calendar.getInstance().get(Calendar.YEAR) % 100

    override fun setupEditText(editText: EditText) {
        super.setupEditText(editText)

        editText.maxLines = MAX_LINES
        editText.filters = arrayOf(InputFilter.LengthFilter(MAX_LENGTH))
        editText.keyListener = DigitsKeyListener.getInstance("0123456789.")
        editText.inputType = InputType.TYPE_CLASS_PHONE or
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or
                InputType.TYPE_NUMBER_FLAG_DECIMAL or
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    override fun convert(text: String) = text

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)

        val source = s.toString()

        val isDeleting = count == 0

        removeWatcher()

        val editable = s as Editable

        when(source.length){
//            day
            1 -> {
                val first = editable.getDigit(0)

                if(first > 3 && first != 0){
                    editable.insert(0, "0")
                    appendDot(isDeleting, editable)
                }
            }
            2 -> {
                val first = editable.getDigit(0) * 10
                val second = editable.getDigit(1)

                val result = first + second

                when{
                    result > 31 -> { editable.replace(0, 2, "31") }
                    result <= 0 -> { editable.replace(0, 2, "01") }
                }

                appendDot(isDeleting, editable)
            }

//            month
            4 -> {
                val first = editable.getDigit(3)

                if(first > 1 && first != 0){
                    editable.insert(3, "0")
                    appendDot(isDeleting, editable)
                }
            }
            5 -> {
                val first = editable.getDigit(3) * 10
                val second = editable.getDigit(4)

                val result = first + second

                when{
                    result > 12 -> { editable.replace(3, 5, "12") }
                    result <= 0 -> { editable.replace(3, 5, "01") }
                }

                appendDot(isDeleting, editable)
            }

//            year
            7 -> {
                when(editable.getDigit(6)) {
                    0 -> editable.insert(6, "2")
                    1, 2 -> { /* nothing */ }
                    3, 4, 5, 6, 7, 8 -> editable.insert(6, "19")
                    else -> editable.insert(6, "1")
                }
            }
            8 -> {
                val first = editable.getDigit(6) * 10
                val second = editable.getDigit(7)

                val result = first + second

                when{
                    result == 19 || result == 20 -> { /* noting */ }
                    result > currentYearEnding -> {
                        editable.insert(6, "19")
                    }
                    else -> {
                        editable.insert(6, "20")
                    }
                }
            }
            9 -> {
                editable.getDigit(8)
            }
            10 -> {
                editable.getDigit(9)
            }
        }
        addWatcher()
    }

    private fun appendDot(isDeleting: Boolean, editable: Editable){
        if(!isDeleting) {
            editable.append(".")
        }
    }

    private fun Editable.getDigit(index: Int): Int {
        val character = this[index]

        return if(character.isDigit()){
            character.toString().toInt()
        } else {
            replace(index, index + 1, "0")
            0
        }
    }
}