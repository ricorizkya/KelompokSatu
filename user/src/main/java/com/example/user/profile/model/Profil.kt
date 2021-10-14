package com.example.user.profile.model

class Profil {
    var idUser: String? = null
    var namaDepanUser: String? = null
    var namaBelakangUser: String? = null
    var emailUser: String? = null
    var nomorUser: String? = null
    var alamatUser: String? = null
    var fotoUser: String? = null
    var idFavoritPaket: String? = null

    constructor(){}

    constructor(idUser: String?, namaDepanUser: String?, namaBelakangUser: String?, emailUser: String?, nomorUser: String?, alamatUser: String?, fotoUser: String?, idFavoritPaket: String?) {
        this.idUser = idUser
        this.namaDepanUser = namaDepanUser
        this.namaBelakangUser = namaBelakangUser
        this.emailUser = emailUser
        this.nomorUser = nomorUser
        this.alamatUser = alamatUser
        this.fotoUser = fotoUser
        this.idFavoritPaket = idFavoritPaket
    }

}