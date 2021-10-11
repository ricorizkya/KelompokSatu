package com.example.user.paket.model

class Paket {
    var id: String? = null
    var namaPaket: String? = null
    var domisili: String? = null
    var durasi: String? = null
    var harga: String? = null
    var alamatlengkap: String? = null
    var deskripsi: String? = null
    var imagePoster: String? = null

    constructor() {}

    constructor(
            id: String?,
            namaPaket: String?,
            domisili: String?,
            durasi: String?,
            harga: String?,
            alamatlengkap: String?,
            deskripsi: String?,
            imagePoster: String?
    ) {
        this.id = id
        this.namaPaket = namaPaket
        this.domisili = domisili
        this.durasi = durasi
        this.harga = harga
        this.alamatlengkap = alamatlengkap
        this.deskripsi = deskripsi
        this.imagePoster = imagePoster
    }
}