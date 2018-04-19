package com.teamgehem.englishspeakingpractice.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.teamgehem.englishspeakingpractice.db.EnglishSentence
import io.realm.RealmResults

class SentenceRecyclerAdapter(val context:Context, val sentenceList:RealmResults<EnglishSentence>): RecyclerView.Adapter<SentenceRecyclerAdapter.Holder>(){
    override fun getItemCount(): Int {

        return sentenceList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val sentence = sentenceList[position]
        holder.title.text = sentence.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(view)
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView!!.findViewById(android.R.id.text1)
    }
}