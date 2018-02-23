package com.yukselproje.okurular.incompany.RestApi;


public class BaseManager {

    protected RestApi getRestApiClient(){
        RestApiClient restApiClient = new RestApiClient(BaseUrl.Data_url);
        return  restApiClient.getRestApi();
    }
}
