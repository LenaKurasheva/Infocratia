package com.lenakurasheva.infocratia.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}