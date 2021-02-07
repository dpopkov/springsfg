package learn.sprb2g.restclient;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
public class RestTemplateExamples {

    private static final String API_ROOT = "https://api.predic8.de:443/shop/";

    @Test
    void getCategories() {
        String apiUrl = API_ROOT + "categories/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response:");
        System.out.println(jsonNode.toString());
    }

    @Test
    void getCustomers() {
        String apiUrl = API_ROOT + "customers/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response:");
        System.out.println(jsonNode.toString());
    }

    @Test
    void createCustomer() {
        String apiUrl = API_ROOT + "customers/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Buck");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    void updateCustomer() {
        String apiUrl = API_ROOT + "customers/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Michael");
        postMap.put("lastname", "Weston");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        Map<String, Object> putMap = new HashMap<>();
        putMap.put("firstname", "Michael 2");
        putMap.put("lastname", "Weston 2");
        restTemplate.put(apiUrl + id, putMap);

        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        assertNotNull(updatedNode);
        System.out.println(updatedNode.toString());
    }

    @Test
    public void updateCustomerUsingPatchSunHttp() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        assertThrows(ResourceAccessException.class, () -> {
            //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
            JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
            assertNotNull(updatedNode);
            System.out.println(updatedNode.toString());
        });
    }

    @Test
    public void updateCustomerUsingPatch() {
        String apiUrl = API_ROOT + "/customers/";
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
        assertNotNull(updatedNode);
        System.out.println(updatedNode.toString());
    }

    @Test
    public void deleteCustomer() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Les");
        postMap.put("lastname", "Claypool");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        assertNotNull(jsonNode);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl + id); //expects 200 status
        System.out.println("Customer deleted");

        assertThrows(HttpClientErrorException.class, () -> {
            //should go boom on 404
            restTemplate.getForObject(apiUrl + id, JsonNode.class);
        });
    }
}
