package com.teamgehem.englishspeakingpractice.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.teamgehem.englishspeakingpractice.R
import com.teamgehem.englishspeakingpractice.db.EnglishSentence
import com.teamgehem.englishspeakingpractice.recyclerview.SentenceRecyclerAdapter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    val realm by lazy {
        Realm.getDefaultInstance()
    }

    var allSentences: RealmResults<EnglishSentence>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        Realm.init(requireContext())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

/*        button_list.setOnClickListener { v ->
            val cmdList = "list"
            jsonVolley(cmdList)
        }

        button_updated.setOnClickListener{v->
            jsonVolley("updated")
        }*/

        val context = requireContext()

        sentence_recycler_view.adapter = SentenceRecyclerAdapter(context, realm.where<EnglishSentence>().findAll())
        sentence_recycler_view.layoutManager = LinearLayoutManager(context)
        sentence_recycler_view.setHasFixedSize(true)

    }

    fun jsonVolley(cmd:String){
        val url = "https://script.google.com/macros/s/AKfycbyXSCGdKWXKA2abHGpMiDTryWg0SorY_A1r6BhJVH5EH-E716w/exec?cmd=";

        val queue = Volley.newRequestQueue(this.context);
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, "$url$cmd", null,
                Response.Listener<JSONObject> { response ->
                    println("서버 response 수신: $response")
                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", "서버 Response 가져오기 실패: $error")
                }
        )
        queue.add(jsonObjectRequest)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
