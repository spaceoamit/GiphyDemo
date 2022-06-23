package com.giphy.gifdemo.models

data class GifrootModel(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)