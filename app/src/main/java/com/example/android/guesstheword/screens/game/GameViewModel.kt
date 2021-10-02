package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.time.milliseconds

class GameViewModel : ViewModel(){

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>()
    val word : LiveData<String>  //Mutable list needs to be ued for LiveData. Mutable value only accessible within this class.
        get() = _word

    // The current score
    private var _eventGameFinish = MutableLiveData<Boolean>() //Mutable list needs to be ued for LiveData. Mutable value only accessible within this class.
    val eventGameFinish : LiveData<Boolean>  //Value accessible from other classes.
        get() = _eventGameFinish

    // The current score
    private var _score = MutableLiveData<Int>() //Mutable list needs to be ued for LiveData. Mutable value only accessible within this class.
    val score : LiveData<Int>  //Value accessible from other classes.
        get() = _score

    // The current time
    private var _time = MutableLiveData<String>() //Mutable list needs to be ued for LiveData. Mutable value only accessible within this class.
    val time : LiveData<String>  //Value accessible from other classes.
        get() = _time

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>


    init {
        _eventGameFinish.value = false
        Log.i("GameViewModel","GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0

        //Create countdown timer which triggers the end of the game when it finishes.
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                //_time.value = (millisUntilFinished / 1000).toString()
                _time.value = DateUtils.formatElapsedTime(millisUntilFinished /1000)
            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _eventGameFinish.value = true
            }
        }

        timer.start()
    }


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //_eventGameFinish.value = true
            resetList()
        }

        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModel cleared!")
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }


}