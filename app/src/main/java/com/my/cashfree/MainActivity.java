package com.my.cashfree;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.core.api.base.CFPayment;
import com.cashfree.pg.core.api.exception.CFException;
import com.my.cashfree.Retrofit.ApiClient;
import com.my.cashfree.Retrofit.ApiInterface;
import com.my.cashfree.Retrofit.PostData;
import com.my.cashfree.Retrofit.TokenModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
    }

    public void onPay(View view) throws CFException {
        //Toast.makeText(this, "Payment in progress", Toast.LENGTH_SHORT).show();
        String orderId="order"+System.currentTimeMillis();
        apiInterface.generateToken(new PostData(orderId,200,"INR")).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                Log.e("onResponse",""+response.body());
                if (response.isSuccessful()){
                    Log.e("status",response.body().getStatus());
                    Log.e("msg",response.body().getMessage());
                    Log.e("tocken",response.body().getCftoken());
                   // String token="L29JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.89QfiMzM3QjN2YWYiFmYyYjI6ICdsF2cfJCL2IzN2kTO4UjNxojIwhXZiwiIS5USiojI5NmblJnc1NkclRmcvJCLwAjM6ICduV3btFkclRmcvJCLiIDMwIXZkJ3biojIklkclRmcvJye.qCXhez-A1qp8wysmZ_1dhn_9x26-tdjrM9t3KW2q_eXrwecNeAoZGHk9Df8K9SZa_I";
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(MainActivity.this, getInputParams(orderId), response.body().getCftoken(), "TEST", "#784BD2", "#FFFFFF", false);
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.e("onFailure",""+t.getLocalizedMessage());
            }
        });
//        String token="L29JCN4MzUIJiOicGbhJCLiQ1VKJiOiAXe0Jye.89QfiMzM3QjN2YWYiFmYyYjI6ICdsF2cfJCL2IzN2kTO4UjNxojIwhXZiwiIS5USiojI5NmblJnc1NkclRmcvJCLwAjM6ICduV3btFkclRmcvJCLiIDMwIXZkJ3biojIklkclRmcvJye.qCXhez-A1qp8wysmZ_1dhn_9x26-tdjrM9t3KW2q_eXrwecNeAoZGHk9Df8K9SZa_I";
//        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
//        cfPaymentService.setOrientation(0);
//        cfPaymentService.doPayment(MainActivity.this, getInputParams(orderId), token, "TEST", "#784BD2", "#FFFFFF", false);
    }

    private Map<String, String> getInputParams(String orderId) {

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        String appId = "182343ebf0ed796bf337561533343281";
        //String orderId = "order002";
        String orderAmount = "200";
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9900012345";
        String customerEmail = "test@gmail.com";

        Map<String, String> params = new HashMap<>();
        params.put(CFPaymentService.PARAM_APP_ID, appId);
        params.put(CFPaymentService.PARAM_ORDER_ID, orderId);
        params.put(CFPaymentService.PARAM_ORDER_AMOUNT, orderAmount);
        params.put(CFPaymentService.PARAM_CUSTOMER_NAME, customerName);
        params.put(CFPaymentService.PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(CFPaymentService.PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(CFPaymentService.PARAM_ORDER_CURRENCY, "INR");
        return params;
    }

    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " +  CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            String msg="";
            if (bundle != null)
                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                       msg=msg+"\n"+key + " : " + bundle.getString(key);
                    }
                }
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Payment Success");
                builder.setMessage(msg);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        }
    }

}