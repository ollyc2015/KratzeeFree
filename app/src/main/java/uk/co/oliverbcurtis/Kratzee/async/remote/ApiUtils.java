package uk.co.oliverbcurtis.Kratzee.async.remote;

public class ApiUtils {

    public static final String BASE_URL = "http://oliverbcurtis.co.uk/ToTheDatabase/";

    public static OperationAPI getApiService() {

        return RetrofitClient.getClient(BASE_URL).create(OperationAPI.class);
    }
}
