package com.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(val id: Int, val name: String)
