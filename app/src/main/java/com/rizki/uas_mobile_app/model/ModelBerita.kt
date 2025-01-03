package com.rizki.crud_berita_user.model

data class ModelBerita(
    val id : Int,
    val judul : String,
    val isi_berita : String,
    val gambar_berita : String,
    val tgl_berita : String
)
