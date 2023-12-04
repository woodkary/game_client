package com.kary.hahaha3.utils;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO 发送到主机是404
@UtilityClass
public class OpenAIAPI {
    /**
     * 聊天端点
     */
    String chatEndpoint = "https://openkey.cloud";
    /**
     * api密匙
     */
    String apiKey = "sk-pXvDRdTXUZB5dGY3B1E2F5267e0d4f86A5710cFcB813D422";

    /**
     * 发送消息
     *
     * @param txt 内容
     * @return {@link String}
     */
    public String chat(List<String> username_list, String txt) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "gpt-3.5-turbo-1106");
        List<Map<String, String>> dataList = new ArrayList<>();
        StringBuilder prompt = new StringBuilder("###Usernames in database:\n");
        for (String username : username_list) {
            prompt.append("# " + username + "\n");
        }
        prompt.append("### user's input:\n# " + txt);
        prompt.append(
                "\n\nFor the Usernames in database and user's input,\nPerform the following actions:\n1 - Remember that all usernamea are immutable.\n2 - Remember that all the username in database should be ouput.\n3 - Rank all usernames in database based on the username and the user's input, with usernames that the user is more likely to be looking for placed ahead.\n4 - Make sure you the usernames.\n5 - Output a list object according to the 4 actions above, the format should be like:\n###\"username_1\", \"username_2\", ... ###\n");
        dataList.add(new HashMap<>() {
            {
                put("role", "system");
                put("content",
                        "You are a search engine that performs fuzzy searches of usernames based on user input.");
            }
        });

        dataList.add(new HashMap<>() {
            {
                put("role", "user");
                put("content", prompt.toString());
            }
        });
        paramMap.put("messages", dataList);
        JSONObject message = null;
        try {
            WebClient webClient = WebClient.create(chatEndpoint);

            String body = webClient.post()
                    .uri("/v1")
                    .header("Authorization", apiKey)
                    .header("Content-Type", "application/json")
                    .body(BodyInserters.fromValue(paramMap))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // 阻塞等待响应


            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            message = result.getJSONObject("message");
        } catch (HttpException e) {
            return "出现了异常";
        } catch (ConvertException e) {
            return "出现了异常";
        }
        return message.getStr("content");
    }

    /*public static void main(String[] args) {
        List<String> username_list = new List<String>();
        // 获取数据库用户名
        username_list.append("Tony").append("Scott").append("Mary").append("Lucy");

        String txt = new String("L");
        System.out.println(chat(username_list, txt));
    }*/
}
