package com.example.quizonline

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonline.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
     lateinit var quizModelList : MutableList<QuizModel>
     lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()


    }

    private fun setupRecyclerView(){
        binding.spinKit.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


    }



//Dommy data


//    private fun  getDataFromFirebase(){
//        //dummy data
//        val listQuestionModel = mutableListOf<QuestionModel>()
//        listQuestionModel.add(QuestionModel("What is Android ?", mutableListOf("Language", "OS", "Product", "None"),"OS"))
//        listQuestionModel.add(QuestionModel("What is own Android ?", mutableListOf("Microsoft", "Linux", "Google", "None"),"Google"))
//        listQuestionModel.add(QuestionModel("Which assistant android uses", mutableListOf("Language", "OS", "Product", "None"),"OS"))
//
//
//
//        quizModelList.add(QuizModel("1","Programming","All basic programming","10",listQuestionModel))
////        quizModelList.add(QuizModel("2","Android","All basic programming","10"))
////        quizModelList.add(QuizModel("3","Kotlin","All basic programming","30"))
//
//
//
//        setupRecyclerView()
//    }


    private fun getDataFromFirebase(){
        binding.spinKit.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }


    }


}