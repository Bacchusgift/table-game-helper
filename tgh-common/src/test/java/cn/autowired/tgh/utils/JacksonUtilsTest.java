package cn.autowired.tgh.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/20 21:42
 * @version: 1.0.0
 */
public class JacksonUtilsTest {
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void mapToJson() {
        LinkedList linkedList = new LinkedList();
        Map map = new HashMap();
        map.put("name","q");
        map.put("sex","man");
        linkedList.push(map);
        try {
            String s = mapToJson(linkedList);
            System.out.println();
        } catch (IOException e) {
            System.out.println("zhuanhuanshibai");
        }
    }

    public static String bean2Json(Object obj) throws IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonStr, objClass);
    }

    public static String mapToJson(LinkedList<Map> map)throws IOException {
        return mapper.writeValueAsString(map);
    }

    public static Map jsonToMap(String jsonStr) throws IOException {
        return mapper.readValue(jsonStr,Map.class);
    }
}