package hr.fer.android.hw0036487720.homeworkapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036487720.model.Operation;

/**
 * Calculus Activity Class is activity used for simple arithmetic operations. Calculated result
 * is forwarded to {@link DisplayActivity}.
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * First number input.
     */
    @BindView(R.id.firstInput)
    EditText firstInput;

    /**
     * Second number input.
     */
    @BindView(R.id.secondInput)
    EditText secondInput;

    /**
     * Group of radio buttons.
     */
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    /**
     * Instance of {@link Operation} that represents sum.
     */
    private final static Operation SUM = new Operation("addition", (a, b) -> a + b);
    /**
     * Instance of {@link Operation} that represents subtraction.
     */
    private final static Operation SUBTRACTION = new Operation("subtraction", (a, b) -> a - b);
    /**
     * Instance of {@link Operation} that represents multiplication.
     */
    private final static Operation MULTIPLICATION = new Operation("multiplication",
            (a, b) -> a * b);
    /**
     * Instance of {@link Operation} that represents division.
     */
    private final static Operation DIVISION = new Operation("division", (a, b) -> a / b);
    /**
     * The constant DISPLAY_REQUEST_CODE.
     */
    public static final int DISPLAY_REQUEST_CODE = 200;

    /**
     * Current operation chosen by the user.
     */
    Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculus);
        ButterKnife.bind(this);
    }


    /**
     * Method called when user clicks the button calculate. Upon the click, arithmetic operation
     * is calculated and result is forwarded to {@link DisplayActivity}.
     */
    @OnClick(R.id.calculate)
    void calculateClick() {

        String first = firstInput.getText().toString();
        String second = secondInput.getText().toString();
        double firstNumber;
        double secondNumber;

        try {
            firstNumber = Double.parseDouble(first);
        } catch (NumberFormatException e) {
            Toast.makeText(CalculusActivity.this, "Enter first number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            secondNumber = Double.parseDouble(second);
        } catch (NumberFormatException e) {
            Toast.makeText(CalculusActivity.this, "Enter second number", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DisplayActivity.class);
        Bundle extras = new Bundle();

        Double result;
        try {
            operation = findOperation();

            if (operation == null) {
                Toast.makeText(CalculusActivity.this, "Choose arithmetic operation",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (operation == DIVISION && secondNumber == 0) {
                Toast.makeText(CalculusActivity.this, "Division by zero is not possible",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            result = operation.getOperation().apply(firstNumber, secondNumber);

            String resultMessage = String.format("Result of the operation %s is: %s",
                    operation.getOperationName(), result);

            extras.putString("resultMessage", resultMessage);

        } catch (Exception exc) {
            String errorMessage = String.format(
                    "During the operation %s using inputs %s and %s following exception happened: " + "%s",
                    operation.getOperationName(), firstNumber, secondNumber, exc.getMessage());

            extras.putString("errorMessage", errorMessage);
        }

        intent.putExtras(extras);
        startActivityForResult(intent, DISPLAY_REQUEST_CODE);
    }

    /**
     * Method used for determining which operation is chosen by the user.
     *
     * @return the operation chosen by the user
     */
    Operation findOperation() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.add:
                return SUM;
            case R.id.sub:
                return SUBTRACTION;
            case R.id.multiply:
                return MULTIPLICATION;
            case R.id.divide:
                return DIVISION;
            default:
                return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DISPLAY_REQUEST_CODE && resultCode == DisplayActivity.OK_PRESSED) {
            firstInput.getText().clear();
            secondInput.getText().clear();
            radioGroup.clearCheck();
        }
    }
}
