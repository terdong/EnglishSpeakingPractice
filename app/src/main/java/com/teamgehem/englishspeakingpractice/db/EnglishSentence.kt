package com.teamgehem.englishspeakingpractice.db

import io.realm.RealmObject

open class EnglishSentence(
    var question: String = "",

    var answer: String = "",

    var tryCount: Int = 0,

    var successCount: Int = 0
):RealmObject(){
    fun setSentences(question:String, answer:String){
        this.question = question
        this.answer = answer
    }

    override fun toString(): String {
        return "Q: $question\nA: $answer"
    }
}