package com.weborient.inventory.repositories.item

import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.newproduct.ArrayElement

object ItemRepository {
    var items = arrayListOf(
        ItemModel("62fe2acc77432", "Bőröv", "adsfasdfasdfasdasdasdfasdfasdadsgasdgsd", "https://static.yoursurprise.com/galleryimage/78/78d2e5452edf380227b19b62b4afbcac/bor-ov.jpg?width=900&crop=1%3A1", false),
        ItemModel("62f3af3cbe728", "HUDSON szürke farmernadrág", "XS Waist: 60 cm\n" +
                "Hips: 74 cm\n" +
                "Total length: 95 cm\n" +
                "S Waist: 64 cm\n" +
                "Hip: 80 cm\n" +
                "Total length: 96 cm\n" +
                "M Waist: 68 cm\n" +
                "Hip: 86 cm\n" +
                "Total length: 97 cm\n" +
                "L Waist: 76 cm\n" +
                "Hip: 88 cm\n" +
                "Total length: 99 cm\n" +
                "XL Waist: 78 cm\n" +
                "Hip: 90 cm\n" +
                "Total length: 100 cm elastic material Material composition:\n" +
                "74% Cotton\n" +
                "24% Polyester\n" +
                "2% Elastane", "https://minibox.cdn.shoprenter.hu/custom/minibox/image/cache/w900h1200wt1/2022-05/0528/Whitemotoros02.png?lastmod=1653904569.1613307118", false),
        ItemModel("62f3aa1bb66ef", "RUNAWAY skinny farmernadrág", "RUNAWAY skinny jeans are your new favorite. Classic, narrow leg style, high waist, comfortable fit. The legs are not the same length at the front and back, so your shoes and sandals can stand out very well in the set. Our best news is that you can find sizes from XS to XXL with us.", "https://minibox.cdn.shoprenter.hu/custom/minibox/image/cache/w900h1200wt1/2022-07/0709/RUNAWAYnadrag03.png?lastmod=1659793076.1613307118", false),
    )
    val testItem = ItemModel("62fe2acc77432", "Bőröv", "Teszt leírás aaaa aaaaa a aaaaaa", "https://static.yoursurprise.com/galleryimage/78/78d2e5452edf380227b19b62b4afbcac/bor-ov.jpg?width=900&crop=1%3A1", false)

    var selectedItem: ItemModel? = null
    var lastUploadedID: String? = null

    var categories: ArrayList<ArrayElement>? = null
    var templates: ArrayList<ArrayElement>? = null
    var units: ArrayList<ArrayElement>? = null
    var packagetypes: ArrayList<ArrayElement>? = null
    var productstatuses: ArrayList<ArrayElement>? = null
}