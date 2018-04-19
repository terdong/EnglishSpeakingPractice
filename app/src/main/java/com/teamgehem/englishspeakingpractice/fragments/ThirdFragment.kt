package com.teamgehem.englishspeakingpractice.fragments

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamgehem.englishspeakingpractice.*
import com.teamgehem.englishspeakingpractice.db.EnglishSentence
import com.teamgehem.englishspeakingpractice.network.DbCommand
import com.teamgehem.englishspeakingpractice.network.VolleyHelper
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import kotlinx.android.synthetic.main.fragment_setting.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ThirdFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       button_check_updated2.setOnClickListener{ v->

           val progress = ProgressDialog(requireContext())
           progress.setTitle("Data DownLoading")
           progress.setMessage("Wait while data downloading")
           progress.setCancelable(false)
           progress.show()

            val context = requireContext()

            VolleyHelper.request(context, DbCommand.updated) { jsonObject ->

                val date = jsonObject.getLong(Date)

                val sharedPref = context.getSharedPreferences(param1, Context.MODE_PRIVATE)

                val localDate = sharedPref.getLong(Date, 0)

                if(localDate < date){

                    //sharedPref.edit().putLong(Date,date).commit()

                    Realm.init(context)
                    val realm = Realm.getDefaultInstance()
                    realm.executeTransaction{realm->
                        realm.delete<EnglishSentence>()
                    }
                    VolleyHelper.request(context, DbCommand.list){ sentenceList->

                        val list = sentenceList.getJSONArray(List)

                        for (i in 0..(list.length() - 1)) {
                            val item = list.getJSONObject(i)
                            //val sentence = EnglishSentence(, item.getString(Answer))
                            //Log.d(LogTitle, "${sentence.question}: ${sentence.answer}")

                            realm.executeTransaction(Realm.Transaction { realm->
                                val es = realm.createObject<EnglishSentence>()
                                es.setSentences(item.getString(Question), item.getString(Answer))
                            })
                        }
/*
                        val all = realm.where<EnglishSentence>().findAll()!!
                        for(es in all){
                            Log.d(LogTitle, es.toString())
                        }*/
                        progress.dismiss()
                    }
                }
                //Log.d(LogTitle, jsonObject.toString())
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ThirdFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
