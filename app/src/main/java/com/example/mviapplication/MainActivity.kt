package com.example.mviapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var numberTv :TextView
    lateinit var addNumberBtn:Button

   // private var viewModel:AddNumberViewModel?=null
   // private val viewModel: AddNumberViewModel by viewModels()

    private var viewModel:AddNumberViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
       viewModel = ViewModelProvider(this).get(AddNumberViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberTv = findViewById(R.id.textView)
        addNumberBtn = findViewById(R.id.button)
        render()
        addNumberBtn.setOnClickListener {
            // send
            lifecycleScope.launch {
                viewModel!!.intentChannel.send(MainIntent.AddNumber)

            }
        }



    }

    private fun render(){
        //render
        lifecycleScope.launchWhenStarted {
            viewModel?.state?.collect {
                when(it){
                    is MainViewState.Idle -> numberTv.text = "Idle"
                    is MainViewState.Number -> numberTv.text = it.number.toString()
                    is MainViewState.Error -> numberTv.text = it.error
                }
            }
        }
    }
}