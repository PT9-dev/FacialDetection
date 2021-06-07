I don package com.example.facialdetection

// A POJO class

class Image() {
    private lateinit var name: String
    private lateinit var url: String


    constructor(name: String, url: String): this(){
        this.name = name
        this.url = url
    }

    fun getName(): String {
        return this.name
    }

    fun setName(name:String){
        this.name = name
    }

    fun getUrl(): String {
        return this.url
    }

    fun setUrl(url:String){
        this.url = url
    }

}