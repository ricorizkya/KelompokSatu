package com.example.kelompoksatu.guide.model

class Guide {
    var id: String? = null
    var namaGuide: String? = null
    var nomorGuide: String? = null
    var emailGuide: String? = null
    var alamatGuide: String? = null
    var umur: String? = null
    var fotoGuide: String? = null
    var motto: String? = null

    constructor() { }

    constructor(id: String?, namaGuide: String?, nomorGuide: String?, emailGuide: String?, alamatGuide: String?, umur: String?, fotoGuide: String?, motto: String?) {
        this.id = id
        this.namaGuide = namaGuide
        this.nomorGuide = nomorGuide
        this.emailGuide = emailGuide
        this.alamatGuide = alamatGuide
        this.umur = umur
        this.fotoGuide = fotoGuide
        this.motto = motto
    }
}