package uk.co.oliverbcurtis.Kratzee.async.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;

public interface OperationAPI {

    //An object can be specified for use as an HTTP request body with the @Body annotation.
    @POST("index.php/")
    Call<ServerResponse> operation(@Body ServerRequest request);
    //The object will also be converted using a converter specified on the Retrofit instance.
    // If no converter is added, only RequestBody can be used.
}
