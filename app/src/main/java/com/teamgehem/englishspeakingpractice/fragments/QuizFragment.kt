package com.teamgehem.englishspeakingpractice.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamgehem.englishspeakingpractice.R
import com.teamgehem.englishspeakingpractice.db.EnglishSentence
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuizFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */


val sampleSentence = arrayOf<String>("what are you doing?", "whed did you go there?")


class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    var index = 0;

    val random = Random(System.nanoTime())

    val realm by lazy {
        Realm.getDefaultInstance()
    }

    var allSentences: RealmResults<EnglishSentence>? = null
    var allSentencesSize: Int = 0
    var currentSentence:EnglishSentence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        Realm.init(requireContext())


        allSentences = realm.where<EnglishSentence>().findAll()
        allSentences?.let {
            allSentencesSize = it.size
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)

        button_know.setOnClickListener { v ->
            //textView_quiz.setText(sampleSentence[index++ % sampleSentence.size]);
            showRandomQuiz()
        }

        button_dont_know.setOnClickListener({ v ->

        })

        button_start.setOnClickListener({ v ->
            showRandomQuiz()
        })

        button_answer.setOnClickListener { v ->
            showAnswer()
        }
    }

    private fun showRandomQuiz() {
        textView_answer.visibility = View.INVISIBLE
        allSentences?.let {
            currentSentence = it.get(random.nextInt(allSentencesSize))
            currentSentence?.let{
                textView_quiz.setText(it.question)
            }
        }
    }

    private fun showAnswer(){
        currentSentence?.let{
            textView_answer.visibility = View.VISIBLE
            textView_answer.setText(it.answer)
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
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                QuizFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
