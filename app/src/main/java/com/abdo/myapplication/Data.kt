package com.abdo.myapplication

import java.io.Serializable

data class Data(
    val categories: List<Category>?,
    val exclusive: List<Exclusive>?,
    val vendor: Vendor?
):Serializable

data class Category(
    val adverts: List<Any>?,
    val id: Int?,
    val image: String?,
    val sub_categories: List<SubCategory>?,
    val title: String?
):Serializable

data class Exclusive(
    val adverts: List<Any>?,
    val carat: String?,
    val description: String?,
    val dimensions: Dimensions?,
    val exclusive: Boolean?,
    val gold_workmanship: String?,
    val id: Int?,
    val image: String?,
    val images: List<Image>?,
    val most_popular: Boolean?,
    val new_arrival: Boolean?,
    val offer: Any?,
    val origin_price: Any?,
    val price: String?,
    val products_options: List<ProductsOption>?,
    val qty: Int?,
    val sharable_link: String?,
    val short_description: String?,
    val sku: String?,
    val sort: Int?,
    val tags: List<Any>?,
    val title: String?,
    val variations_values: List<VariationsValue>?
):Serializable

data class Vendor(
    val description: String?,
    val id: Int?,
    val image: String?,
    val opening_status: OpeningStatus?,
    val title: String?
):Serializable

data class SubCategory(
    val adverts: List<Any>?,
    val id: Int?,
    val image: String?,
    val sub_categories: List<SubCategory>?,
    val title: String?
):Serializable

data class Dimensions(
    val weight: String?
):Serializable

data class Image(
    val id: Int?,
    val image: String?
):Serializable

data class ProductsOption(
    val id: Int?,
    val option_id: Int?,
    val option_values: List<OptionValue>?,
    val title: String?
):Serializable

data class VariationsValue(
    val dimensions: Dimensions?,
    val id: Int?,
    val image: String?,
    val offer: Any?,
    val price: String?,
    val qty: Int?,
    val sku: String?,
    val variations: List<Variation>?
):Serializable

data class OptionValue(
    val id: Int?,
    val option_value: String?,
    val option_value_id: Int?
):Serializable

data class Variation(
    val id: Int?,
    val option_value: String?,
    val option_value_id: Int?
):Serializable

data class OpeningStatus(
    val accepting_orders: Int?,
    val id: Int?,
    val status: String?
):Serializable

