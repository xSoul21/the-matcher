package com.thematcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonComparator {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean compareJson(String json1, String json2) throws Exception {
        JsonNode tree1 = objectMapper.readTree(json1);
        JsonNode tree2 = objectMapper.readTree(json2);

        return compareNodes(tree1, tree2);
    }

    private static boolean compareNodes(JsonNode node1, JsonNode node2) {
        if (node1.isArray() && node2.isArray()) {
            if (node1.size() != node2.size()) {
                return false;
            }

            for (int i = 0; i < node1.size(); i++) {
                boolean foundMatch = false;
                for (int j = 0; j < node2.size(); j++) {
                    if (compareNodes(node1.get(i), node2.get(j))) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    return false;
                }
            }
            return true;
        } else {
            return node1.equals(node2);
        }
    }

    public static void main(String[] args) throws Exception {
        String json1 = "[{\"nome\":\"pippo\"},{\"nome\":\"paperino\"},{\"nome\":\"topolino\"}]";
        String json2 = "[{\"nome\":\"pippo\"},{\"nome\":\"topolino\"},{\"nome\":\"paperino\"}]";

        if (compareJson(json1, json2)) {
            System.out.println("The JSON arrays are equal (ignoring order).");
        } else {
            System.out.println("The JSON arrays are not equal (ignoring order).");
        }
    }
}
