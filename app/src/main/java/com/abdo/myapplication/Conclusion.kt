package com.abdo.myapplication

import java.io.Serializable

data class Conclusion(
    val titleCategory:String,
    val exclusive:ArrayList<Exclusive>
    ): Serializable
