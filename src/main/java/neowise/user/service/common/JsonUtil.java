package neowise.user.service.common;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static JsonNode getChildNode(JsonNode node, String key) {
        try {
            JsonNode childNode = node.get(key);
            return childNode;
        } catch (Exception e) {
            String errMsg = String.format("Failed to get child node %s in json = {%s}", key, node.toPrettyString());
            logger.error(errMsg, e);
            return null;
        }
    }

    public static <T> T parseJson(String jsonStr, Class<T> outputType) {
        try {
            ObjectMapper om = new ObjectMapper();
            T result = om.readValue(jsonStr, outputType);
            return result;
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON string", e);
            return null;
        }
    }

    public static <T> T parseJson(JsonNode node, Class<T> outputType) {
        try {
            ObjectMapper om = new ObjectMapper();
            T result = om.treeToValue(node, outputType);
            return result;
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON string", e);
            return null;
        }
    }

    public static String buildJson(Map<String, ?> data) {
        try {
            ObjectMapper om = new ObjectMapper();
            String result = om.writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            logger.error("Failed to build json. ", e);
            return null;
        }
    }

    public static JsonNode buildJsonNode(Map<String, ?> data) {
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode result = om.valueToTree(data);
            return result;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to build json node. ", e);
            return null;
        }
    }

    public static <T> T parseJsonWithEscapeBackslash(String jsonStr, Class<T> outputType) {
        try {
            ObjectMapper om = JsonMapper.builder().enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                    .build();
            T result = om.readValue(jsonStr, outputType);
            return result;
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON string with escape backslashes", e);
            return null;
        }
    }

}
