package hr.fer.android.hw0036487720.networking.model;


import hr.fer.android.hw0036487720.model.FormResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Interface used for usage of Retrofit library.
 */
public interface RetrofitService {


    /**
     * Fetches all the data from provided url.
     *
     * @param url url to the page that contains JSON object
     * @return the data
     */
    @GET()
    Call<FormResponse> getObject(@Url String url);
}
