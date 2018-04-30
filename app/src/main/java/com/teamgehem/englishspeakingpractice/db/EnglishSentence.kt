package com.teamgehem.englishspeakingpractice.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class EnglishSentence(
        @PrimaryKey var index: Long = 0,

        var question: String = "",

        var answer: String = "",

        var tryCount: Int = 0,

        var successCount: Int = 0
) : RealmObject() {
    fun setSentences(index:Long, question: String, answer: String) {
        this.index = index
        this.question = question
        this.answer = answer
    }

    override fun toString(): String {
        return "idx: $index. Q: $question\nA: $answer"
    }
}