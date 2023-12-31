//package com.flink.flinkrest;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.StatusLine;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.*;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.stream.Collectors;
//
//public class Jobname {
//        static String jarId = "c00a8e45-7180-4eed-af14-f30f2102fb86_tock1.12.4-1.0-SNAPSHOT.jar";
//        static String jobName = "数据归并";
//        static String urlPrefix = "http://10.10.41.251:8081";
//
//        public static void main(String[] args) throws IOException, InterruptedException {
//            String jobID = getJobID(urlPrefix, jobName);
//            System.out.println(jobID);
//            String jobState = getJobState(urlPrefix, jobID);
//            System.out.println(jobState);
//            if ("RUNNING".equals(jobState)) {
//                Thread.sleep(3000);
//                int stop = stop(urlPrefix, jobID);
//                if (stop == 202){
//                    System.out.println("关闭程序,并执行重启");
//                }else{
//                    System.out.println("未能关闭进程");
//                    return;
//                }
//            }
//            //执行新的进程
//            start();
//            //System.out.println(start);
////            try {
////                    getJobState(urlPrefix, jobID);
////            } catch (IOException e) {
////                e.printStackTrace();
////            } finally {
////                System.out.println("pipeline stop");
////            }
//
//
//        }
//
//    private static void start() throws IOException {
//
//            //http://10.10.41.251:8081/v1/jars/c00a8e45-7180-4eed-af14-f30f2102fb86_tock1.12.4-1.0-SNAPSHOT.jar/run?entry-class=flink.Tockjson5&program-args=--min 15
//
//        //String flinkWebUrl = FlinkWebUrlUtil.getRealFlinkUrl(applicationName);
//        String flinkWebUrl = "http://10.10.41.251:8081/v1/jars/b80f0972-43ff-4eae-9e77-f4534d5c8ed2_tock1.12.4-1.0-SNAPSHOT.jar/run";
//        System.out.println(flinkWebUrl);
//        HttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(flinkWebUrl);
//        //System.out.println(flinkWebUrl + "/jars/" + jarId + "/run");
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("entryClass","flink.Tockjson5");
//        jsonObj.put("programArgs","--second 15");
//        StringEntity entity = new StringEntity(jsonObj.toString(), ContentType.APPLICATION_JSON);
//        httpPost.setEntity(entity);
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        System.out.println(httpResponse.getStatusLine().getStatusCode());
//        HttpEntity response = httpResponse.getEntity();
//        System.out.println(response);
//        String result = new BufferedReader(new InputStreamReader(response.getContent()))
//                .lines().collect(Collectors.joining("\n"));
//        System.out.println(result);
//    }
//
//    /*若任务的状态为running，则调用api结束任务*/
//        public static int stop(String urlPrefix, String jid) throws IOException {
//            CloseableHttpResponse stopJob = send(new HttpPatch(urlPrefix + "/jobs/" + jid));
//            StatusLine statusLine = stopJob.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            /*202代表执行成功，关闭pipeline*/
//            if (statusCode != 202) {
//                throw new RuntimeException("当前任务未能正确关闭");
//            }
//            return statusCode;
//
//        }
//
//        /*判断当前任务的状态   "state": "RUNNING" */
//        public static String getJobState(String urlPrefix, String jid) throws IOException {
//            CloseableHttpResponse getJobState = send(new HttpGet(urlPrefix + "/jobs/" + jid));
//            String getJobStateRes = transform(getJobState);
//            JSONObject jsonObject = JSON.parseObject(getJobStateRes);
//            String state = jsonObject.getString("state");
//            if (!"RUNNING".equals(state)) {
//                throw new RuntimeException("当前任务处于停止或故障状态");
//            }
//            return state;
//        }
//
//        /*根据jobname获取jobid*/
//        public static String getJobID(String urlPrefix, String jobName) throws IOException {
//            CloseableHttpResponse getJobID = send(new HttpGet(urlPrefix + "/jobs/overview"));
//            String getJobIDRes = transform(getJobID);
//            JSONObject res = JSON.parseObject(getJobIDRes);
//            JSONArray jobs = res.getJSONArray("jobs");
//            String jid = null;
//            for (int i = 0; i < jobs.size(); i++) {
//                JSONObject jsonObject = (JSONObject) jobs.get(i);
//                String name = (String) jsonObject.get("name");
//                if (name.equals(jobName)) {
//                    jid = (String) jsonObject.get("jid");
//                    break;
//                }
//            }
//            if (jid == null || jid.equals("")) {
//                throw new RuntimeException("pipeline在flink中没有对应的任务");
//            }
//            return jid;
//        }
//
//        public static CloseableHttpResponse send(HttpRequestBase httpRequestBase) throws IOException {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            CloseableHttpResponse response = httpclient.execute(httpRequestBase);
//            return response;
//        }
//
//        public static String transform(CloseableHttpResponse response) throws IOException {
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity);
//            return result;
//        }
//
//
//    }
