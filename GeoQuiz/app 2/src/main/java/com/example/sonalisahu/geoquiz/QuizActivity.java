package com.example.sonalisahu.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
   /* private ImageButton mNextButton;
    private ImageButton mPrevButton;*/
   private Button mNextButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;
    private int qa = 0;
    private static final String KEY_INDEX = "index";
    private static final String KEY_QA = "qa";

    private static final String TAG = "QuizActivity";

    private Question[] mQuestionBank = new Question[] {

            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            qa = savedInstanceState.getInt(KEY_QA,0);
        }

        Log.i(TAG, "mCurrentIndex :: "+mCurrentIndex);

       // if(mCurrentIndex < mQuestionBank.length) {
            mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

            mQuestionTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "mCurrentIndex :: " + mCurrentIndex);
                    if(qa < mQuestionBank.length)
                        updateQuestion();
                    else
                        finalScoring();
                }
            });

        /*int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);*/


            mTrueButton = (Button) findViewById(R.id.true_button);
            mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*Toast.makeText(QuizActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                    checkAnswer(true);
                }
            });

            mFalseButton = (Button) findViewById(R.id.false_button);
            mFalseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /*Toast.makeText(QuizActivity.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT).show();*/
                    mFalseButton.setEnabled(false);
                    mTrueButton.setEnabled(false);
                    checkAnswer(false);
                }
            });


            mNextButton = (Button) findViewById(R.id.next_button);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "mCurrentIndex :: " + mCurrentIndex);
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                /*int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);*/
                if(qa < mQuestionBank.length)
                    updateQuestion();
                    else
                        finalScoring();

                }
            });

            updateQuestion();


        /*mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex > 0)
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                else
                    mCurrentIndex = mQuestionBank.length -1;*/

                /*int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);*/
                /*updateQuestion();
            }
        });

        updateQuestion();*/
       /* }
        else
        Toast.makeText(this, "Your score is "+mCurrentScore+"/"+(mQuestionBank.length-1), Toast.LENGTH_LONG)
                .show();*/
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);
    }

    private void finalScoring() {
        mQuestionTextView.setText("Your score is "+mCurrentScore+"/"+(mQuestionBank.length-1));
        mQuestionTextView.setFocusable(true);
        mQuestionTextView.setHighlightColor(100);
        mFalseButton.setVisibility(View.GONE);
        mTrueButton.setVisibility(View.GONE);
        mNextButton.setVisibility(View.GONE);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        qa++;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCurrentScore++;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
        //Toast.makeText(this, String.valueOf(qa), Toast.LENGTH_SHORT)
                //.show();
        /*Toast.makeText(this, "Your score is "+mCurrentScore+"/"+(mQuestionBank.length-1), Toast.LENGTH_LONG)
                .show();*/
        Log.i(TAG, "mCurrentScore :: "+mCurrentScore);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_QA, qa);
    }
}

