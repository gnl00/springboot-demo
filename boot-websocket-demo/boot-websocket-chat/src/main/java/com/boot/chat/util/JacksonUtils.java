package com.boot.chat.util;

import com.boot.chat.bean.WSMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * JacksonUtils
 *
 * @author lgn
 * @since 2022/3/17 10:42
 */

@Slf4j
public class JacksonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        log.info("执行 JacksonUtils 初始化操作");
        if (Objects.nonNull(objectMapper)) {
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
    }

    /**
     * Object to json
     */
    public static String writeObjectAsString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static WSMessage readValue(String jsonStr) throws JsonProcessingException {
        WSMessage msgObj = objectMapper.readValue(jsonStr, WSMessage.class);
        return msgObj;
    }

}
