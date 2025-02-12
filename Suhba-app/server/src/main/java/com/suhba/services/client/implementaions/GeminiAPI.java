package com.suhba.services.client.implementaions;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;


import okhttp3.*;

public class GeminiAPI {
    private static String key = "AIzaSyBvqAM8BGP2hOu1z6dqjVDvnalvCyU-NRM";
    private static String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";
    private static OkHttpClient client = new OkHttpClient();





    public static String getBotResponse(String userMessage) {
        try {
            JSONObject content = new JSONObject();
            content.put("role", "user");
            content.put("parts", new JSONArray().put(new JSONObject().put("text", userMessage)));

            JSONObject requestBody = new JSONObject();
            requestBody.put("contents", new JSONArray().put(content));

            Request request = new Request.Builder()
                    .url(url+key)
                    .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getJSONArray("candidates").getJSONObject(0)
                        .getJSONObject("content").getJSONArray("parts")
                        .getJSONObject(0).getString("text");
            } else {
                System.err.println("Error response: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I'm Sleeping ......";
    }


}
