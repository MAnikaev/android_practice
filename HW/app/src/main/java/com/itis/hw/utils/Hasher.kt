package com.itis.hw.utils

import java.security.MessageDigest

object Hasher {
    fun getHash(s: String) : String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(s.toByteArray())
        val hexString = StringBuilder(hashBytes.size * 2)
        for (byte in hashBytes) {
            val hex = String.format("%02X", byte)
            hexString.append(hex)
        }
        return hexString.toString().lowercase()
    }
}