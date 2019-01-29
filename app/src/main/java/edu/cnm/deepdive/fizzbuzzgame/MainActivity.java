package edu.cnm.deepdive.fizzbuzzgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private static final int UPPER_BOUND = 99;
  private static final int TIMEOUT_INTERVAL = 5000;

  private Random rng;
  private int value;

  private TextView numberView;
  private TextView activeCorrectView;
  private TextView passiveCorrectView;
  private TextView incorrectView;
  private ToggleButton fizzToggle;
  private ToggleButton buzzToggle;

  private int activeCorrect = 0;
  private int passiveCorrect = 0;
  private int incorrect = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    numberView = findViewById(R. id.number_view);
    fizzToggle = findViewById(R. id. fizz_toggle);
    buzzToggle = findViewById(R. id. buzz_toggle);
    activeCorrectView = findViewById(R.id.active_correct);
    passiveCorrectView = findViewById(R.id.passive_correct);
    incorrectView = findViewById(R.id.incorrect);
    rng = new Random();
    startTimer();
  }

  private void startTimer() {
    Timer timer = new Timer();
    timer.start();
  }
  private void updateView() {
    value = 1 + rng.nextInt(UPPER_BOUND);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        updateTallyDisplay();
        numberView.setText(Integer.toString(value));
        fizzToggle.setChecked(false);
        buzzToggle.setChecked(false);
      }
    });
  }
  private void updateTallyDisplay() {
    activeCorrectView.setText (getString(R.string.active_correct, activeCorrect));
    passiveCorrectView.setText(getString(R.string.passive_correct, passiveCorrect));
    incorrectView.setText(getString(R.string.incorrect, incorrect));
  }
  private void updateTally() {
    boolean isFizz = (value % 3 == 0);
    boolean isBuzz = (value % 5 == 0);
    boolean fizzCorrect = (isFizz == fizzToggle.isChecked());
    boolean buzzCorrect = (isBuzz == buzzToggle.isChecked());
    if (!fizzCorrect || !buzzCorrect) {
      incorrect++;
    } else if(isFizz || isBuzz) {
      activeCorrect++;
    } else {
      passiveCorrect++;
    }
  }
  private class Timer extends Thread {

    public void run() {

      try {
        updateView();
        Thread.sleep(TIMEOUT_INTERVAL);
      } catch (InterruptedException e) {
        //DO NOTHING!
      } finally {
        updateTally();
        startTimer();

      }
      }
  }
}
