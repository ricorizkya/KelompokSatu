package com.example.kelompoksatu.pesanan.model

class Pesanan {
    var idPesanan: String? = null
    var idUser: String? = null
    var tglIni: String? = null
    var namaDepan: String? = null
    var namaBelakang: String? = null
    var nomorTelepon: String? = null
    var emailUser: String? = null
    var alamatUser: String? = null
    var tglBerangkat: String? = null
    var fotoKTP: String? = null
    var namaPaket: String? = null
    var imagePaket: String? = null
    var durasiPaket: String? = null
    var alamatPaket: String? = null
    var hargaPaket: String? = null
    var guide: String? = null
    var emailUserNamaPaket: String? = null
    var statusPembayaran: String? = null
    var idUserStatusPembayaran: String? = null
    var fotoPembayaran: String? = null

    constructor() {}

    constructor(idPesanan: String?,
                idUser: String?,
                tglIni: String?,
                namaDepan: String?,
                namaBelakang: String?,
                nomorTelepon: String?,
                emailUser: String?,
                alamatUser: String?,
                tglBerangkat: String?,
                fotoKTP: String?,
                namaPaket: String?,
                imagePaket: String?,
                durasiPaket: String?,
                alamatPaket: String?,
                hargaPaket: String?,
                guide: String?,
                emailUserNamaPaket: String?,
                statusPembayaran: String?,
                idUserStatusPembayaran: String?,
                fotoPembayaran: String? ) {
        this.idPesanan = idPesanan
        this.idUser = idUser
        this.tglIni = tglIni
        this.namaDepan = namaDepan
        this.namaBelakang = namaBelakang
        this.nomorTelepon = nomorTelepon
        this.emailUser = emailUser
        this.alamatUser = alamatUser
        this.tglBerangkat = tglBerangkat
        this.fotoKTP = fotoKTP
        this.namaPaket = namaPaket
        this.imagePaket = imagePaket
        this.durasiPaket = durasiPaket
        this.alamatPaket = alamatPaket
        this.hargaPaket = hargaPaket
        this.guide = guide
        this.emailUserNamaPaket = emailUserNamaPaket
        this.statusPembayaran = statusPembayaran
        this.idUserStatusPembayaran = idUserStatusPembayaran
        this.fotoPembayaran = fotoPembayaran
    }
}