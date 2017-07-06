package hr.fer.android.hw0036487720.homeworkapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Display Activity is an activity where is presented calculated result, or the message of the
 * exception that happened during calculation. This activity also offers two buttons, "OK" and
 * "Send report" that are used for returning to previous activity ({@link CalculusActivity} and
 * sending e-mail report, respectfully.
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * The constant EMAIL_ADDRESS.
     */
    public static final String EMAIL_ADDRESS = "ana@baotic.org";
    /**
     * The constant EMAIL_SUBJECT.
     */
    public static final String EMAIL_SUBJECT = "0036487720: dz report";

    /**
     * {@link TextView} where result or exception message is printed.
     */
    @BindView(R.id.resultText)
    TextView messageView;

    /**
     * The constant OK_PRESSED.
     */
    public static final int OK_PRESSED = 100;

    /**
     * The Error happened.
     */
    boolean errorHappened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        errorHappened = extras.getString("errorMessage") != null;

        messageView.setText(errorHappened ?
                extras.getString("errorMessage") : extras.getString("resultMessage"));


    }


    /**
     * Method called when user presses OK button. It returns him to the previous activity, which
     * is {@link CalculusActivity}.
     */
    @OnClick(R.id.okButton)
    void onOK() {
        setResult(OK_PRESSED);
        finish();
    }

    /**
     * Method called when user presses "Send report" button. It creates e-mail, with
     * EMAIL_ADDRESS as recipient address, EMAIL_SUBJECT as subject and result or error mesage as
     * text that will be sent.
     */
    @OnClick(R.id.sendReport)
    void onSendReport() {
        Intent emailIntent = new Intent();
        Bundle extras = getIntent().getExtras();

        String emailMessage = errorHappened ? extras.getString(
                "errorMessage") : extras.getString("resultMessage");

        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
