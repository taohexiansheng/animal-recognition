import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AnimalDemo {
    public static void main(String[] args) {
        // ===== 需要你替换的两项 =====
        // 1. 你刚获取的 access_token（从控制台或 TokenTest 的运行结果里复制）
        String accessToken = "24.3aaf5ca3ac47577493781adfecf507b3.2592000.1776061464.282335-122363379";

        // 2. 一张真实的动物图片 URL（从百度图片搜索复制）
        String imageUrl = "https://wx4.sinaimg.cn/mw690/7870589dly1i1jtfh7tvsj20xq18zng6.jpg";
        // ===========================

        String result = recognizeAnimal(accessToken, imageUrl);
        System.out.println(result);
    }

    public static String recognizeAnimal(String accessToken, String imageUrl) {
        try {
            // 动物识别接口地址
            String apiUrl = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal?access_token=" + accessToken;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // 对图片URL进行编码
            String body = "url=" + URLEncoder.encode(imageUrl, "UTF-8");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            conn.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "错误：" + e.getMessage();
        }
    }
}
