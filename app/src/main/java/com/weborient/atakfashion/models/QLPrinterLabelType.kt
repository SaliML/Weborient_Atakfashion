package com.weborient.atakfashion.models

import com.brother.sdk.lmprinter.setting.QLPrintSettings

class QLPrinterLabelType(val id: QLPrintSettings.LabelSize, val name: String){
    override fun toString(): String {
        return name
    }
}
