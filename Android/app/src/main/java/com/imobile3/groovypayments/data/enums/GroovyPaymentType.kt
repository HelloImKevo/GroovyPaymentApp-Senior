package com.imobile3.groovypayments.data.enums

enum class GroovyPaymentType(val id: Int) {

    Cash(1),
    Credit(2);

    companion object {

        @JvmStatic
        fun fromId(id: Int): GroovyPaymentType? = values().find { it.id == id }
    }
}
