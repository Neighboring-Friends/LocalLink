package com.example.nextdoorfriend.translator

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class Translator {

    private val TAG = "Dirtfy"

    companion object {
        private val keOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.KOREAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        private val koreanEnglishTranslator = Translation.getClient(keOptions)

        private val kcOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.KOREAN)
            .setTargetLanguage(TranslateLanguage.CHINESE)
            .build()
        private val koreanChineseTranslator = Translation.getClient(kcOptions)
    }

    init {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        koreanEnglishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
                Log.d(TAG, "download success")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                // ...
                Log.d(TAG, "download fail")
            }
        koreanChineseTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
                Log.d(TAG, "download success")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                // ...
                Log.d(TAG, "download fail")
            }
    }

    fun translateE(korean: String, ifSuccess: (String)->Unit, ifFail: (Exception)->Unit) {
        koreanEnglishTranslator.translate(korean)
            .addOnSuccessListener(ifSuccess)
            .addOnFailureListener(ifFail)
    }

    fun translateC(korean: String, ifSuccess: (String)->Unit, ifFail: (Exception)->Unit) {
        koreanChineseTranslator.translate(korean)
            .addOnSuccessListener(ifSuccess)
            .addOnFailureListener(ifFail)
    }
}