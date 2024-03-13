package com.huachuan.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.cj.jdbc.Driver;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StructureDefinition;

public class ChatGPTClient {
   
    private static final String API_KEY = "";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
    public static String chatWithGPT(String message) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            JsonObject jsonInput = new JsonObject();
            jsonInput.addProperty("max_tokens", 2048);
            jsonInput.addProperty("model", "gpt-3.5-turbo");
            JsonObject jsonMessage1 = new JsonObject();
            jsonMessage1.addProperty("role","user");
            jsonMessage1.addProperty("content",message);
            JsonArray messages = new JsonArray();
            messages.add(jsonMessage1);

            jsonInput.add("messages", messages);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonInput.toString().getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            System.out.println(jsonResponse.toString());
            return jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
//        String message = "如下是医院his系统的表头信息：门诊号\t住院号\t性别\t出生日期\t年龄\t民族\t患者姓名\t国籍\t职业\t证件类型\t历史总门诊就诊次数\t主诉（门诊）\t现病史（门诊）\t是否MDT门诊\t既往史（门诊）\t就诊标识（医渡云计算）\t就诊类型\t入院(就诊)时间\t出诊医师\t病历发生时年龄 患者就诊年龄（年），假设你现在正在使用hapi fhir库，请你给出这些字段应该对应于哪一个hapi fhir资源以及其字段的映射关系,不要用文字描述，用json格式来表示就行";
//        String response = chatWithGPT(message);
//        System.out.println("ChatGPT回答: " + response);
        ClassLoader classLoader = Driver.class.getClassLoader();
        System.out.println(classLoader);


    }
}