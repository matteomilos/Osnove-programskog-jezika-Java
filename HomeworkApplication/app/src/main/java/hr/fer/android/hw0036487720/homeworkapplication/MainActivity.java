package hr.fer.android.hw0036487720.homeworkapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Main Activity is the first activity on the app start. It offers two choices to the user, Math,
 * which opens {@link CalculusActivity}, or Statistics, which opens {@link FormActivity}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Statistics.
     */
    @BindView(R.id.statistics)
    Button statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }


    /**
     * Method called after button "MATH" is pressed. It opens {@link CalculusActivity}.
     */
    @OnClick(R.id.math)
    void onMathClick() {
        startActivity(new Intent(this, CalculusActivity.class));
    }

    /**
     * Method called after button "STATISTICS" is pressed. It opens {@link FormActivity}.
     */
    @OnClick(R.id.statistics)
    void onStatisticsClick() {
        startActivity(new Intent(this, FormActivity.class));
    }
}
