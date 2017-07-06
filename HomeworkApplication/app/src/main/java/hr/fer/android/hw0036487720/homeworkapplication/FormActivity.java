package hr.fer.android.hw0036487720.homeworkapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036487720.model.FormResponse;
import hr.fer.android.hw0036487720.networking.model.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Form Activity is an activity that offers user to load data from some URL, and then that data
 * is presented to the user.
 */
public class FormActivity extends AppCompatActivity {

    /**
     * The constant BASE_URL
     */
    public static final String BASE_URL = "http://m.uploadedit.com";
    /**
     * The Service.
     */
    RetrofitService service;

    /**
     * The Relative path.
     */
    @BindView(R.id.relativePath)
    EditText relativePath;

    /**
     * The First name.
     */
    @BindView(R.id.firstName)
    TextView firstName;

    /**
     * The Last name.
     */
    @BindView(R.id.lastName)
    TextView lastName;

    /**
     * The Phone no.
     */
    @BindView(R.id.phoneNo)
    TextView phoneNo;

    /**
     * The Email sknf.
     */
    @BindView(R.id.mail)
    TextView email_sknf;

    /**
     * The Spouse.
     */
    @BindView(R.id.spouse)
    TextView spouse;

    /**
     * The Age.
     */
    @BindView(R.id.age)
    TextView age;

    /**
     * The Image.
     */
    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(RetrofitService.class);


    }

    /**
     * Method called when button "LOAD" is pressed. Our instance of {@link RetrofitService} gets
     * the data from users url and prints it to the screen.
     */
    @OnClick(R.id.load)
    void onClick() {
        String url = relativePath.getText().toString();


        service.getObject(url).enqueue(new Callback<FormResponse>() {
            @Override
            public void onResponse(Call<FormResponse> call, Response<FormResponse> response) {

                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    FormResponse newResponse = gson.fromJson(json, FormResponse.class);


                    if (newResponse.getAvatarLocation() == null) {
                        image.setVisibility(View.INVISIBLE);
                    } else {
                        Glide.with(FormActivity.this).load(newResponse.getAvatarLocation()).into(
                                image);
                    }

                    updateTextViews(newResponse);
                } catch (Exception exc) {
                    Toast.makeText(FormActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<FormResponse> call, Throwable t) {
                Toast.makeText(FormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });


    }

    /**
     * Message used for updating {@link TextView} instances.
     *
     * @param newResponse instance of {@link FormResponse}
     */
    private void updateTextViews(FormResponse newResponse) {
        firstName.setText(String.valueOf("FIRST NAME: " + newResponse.getFirstName()));
        lastName.setText(String.valueOf("LAST NAME: " + newResponse.getLastName()));
        phoneNo.setText(String.valueOf("PHONE NO: " + newResponse.getPhoneNo()));
        email_sknf.setText(String.valueOf("EMAIL: " + newResponse.getEmail_sknf()));
        spouse.setText(String.valueOf("SPOUSE: " + newResponse.getSpouse()));
        age.setText(String.valueOf("AGE: " + newResponse.getAge()));
    }
}
