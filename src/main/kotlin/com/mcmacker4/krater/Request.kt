package com.mcmacker4.krater


class Request(
    val method: String,
    val path: String,
    val version: String,
    val headers: Headers,
    val body: String?
)