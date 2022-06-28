package com.my.cashfree.Retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/order")
    Call<List<TokenModel>>getTodos();


    @POST ("v2/cftoken/order")
    @Headers({
//            "Accept: application/json",
            "x-client-id: 182343ebf0ed796bf337561533343281",
            "x-client-secret: 5f04f7320111b1eea55cc4627cfbfee13527a5bb"
    })
    Call<TokenModel> generateToken(@Body PostData model);

//    @FormUrlEncoded
//    Call<TokenModel> generateToken(@Field("orderId") String order_id,
//                              @Field("orderAmount") int amt,
//                              @Field("orderCurrency") String currency);//@Body LoginBody loginBody

}
