package com.thematcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class MatchingService {

    public static void main(String[] args) throws JsonProcessingException {
        MatchingService test = new MatchingService();
        Map<String,Object> first = test.decodeJson("""
            {
            "customer": {
                "id": 44521,
                "age": 27,
                "element":[{"element2":[{"chiave1":"valore1","nome":true}],"element1":1}],
                "fullName": "Emily Jenkins",
                "test":[{"nome":"pippo"},{"nome":"paperino"},{"nome":"topolino"}]

            }
        }
                """);
        Map<String,Object> second = test.decodeJson("""
                {
                    "customer": {
                        "id": 44521,
                        "fullName": "Emily Jenkins",
                        "age": 27,
                        "element":[{"element1":1,"element2":[{"chiave1":"valore1","nome":true}]}],
                        "zucca":true,
                        "test":[{"nome":"pippo"},{"nome":"topolino"},{"nome":"paperino"}]
                    }
                }
                """);
        Map<String, Object> leftFlatMap = FlatMapUtil.flatten(first);
        Map<String, Object> rightFlatMap = FlatMapUtil.flatten(second);

        MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);

        System.out.println("Entries only on the left\n--------------------------");
        difference.entriesOnlyOnLeft()
                .forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\n\nEntries only on the right\n--------------------------");
        difference.entriesOnlyOnRight()
                .forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\n\nEntries differing\n--------------------------");
        difference.entriesDiffering()
                .forEach((key, value) -> System.out.println(key + ": " + value));


    }

    protected Map<String,Object> decodeJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type =
                new TypeReference<HashMap<String, Object>>() {};
        return  mapper.readValue(json, type);

    }
}
