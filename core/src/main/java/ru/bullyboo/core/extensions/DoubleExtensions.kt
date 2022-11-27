package ru.bullyboo.core.extensions

import java.math.BigDecimal

fun Double?.isNotNullOrZero(): Boolean {
    return this != null && this != 0.0
}

fun Double.format(
    addSpaces: Boolean = true,
    fractionLength: Int = 0,
    removeZeroFraction: Boolean = true
): String {
    val array = BigDecimal(this)
        .toString()
        .replace("-", "")
        .split(".")

    val builder = StringBuilder()

    val decimal = array[0]
    val decimalLength = decimal.length

    var count = 0

    if (addSpaces) {
        for (i in decimalLength - 1 downTo 0) {
            builder.insert(0, decimal[i])
            count++

            if (count >= 3 && i != 0) {
                count = 0
                builder.insert(0, " ")
            }
        }
    } else {
        builder.append(decimal)
    }

    if (array.size > 1) {
        var fraction = array[1]
        val length = fraction.length

        if (length != 0) {
            if (length == 1) {
                fraction += "0"
            }

            fraction = fraction.take(fractionLength)

            val zeroFraction = getZeroFraction(fractionLength)

            if (fraction == zeroFraction) {
                if (!removeZeroFraction) {
                    builder.append(".").append(fraction)
                }
            } else {
                builder.append(".").append(fraction)
            }
        }
    } else if (fractionLength != 0 && !removeZeroFraction) {
        val zeroFraction = getZeroFraction(fractionLength)
        builder.append(".").append(zeroFraction)
    }

    if (this < 0) {
        builder.insert(0, "-")
    }

    return builder.toString().trim()
}

private fun getZeroFraction(size: Int): String {
    val builder = StringBuilder()

    for (i in 0 until size) {
        builder.append("0")
    }

    return builder.toString()
}
