package com.thematcher;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class MatchingService {

    public static void main(String[] args) throws IOException {
        MatchingService test = new MatchingService();
        Map<String, Object> first = test.decodeJson("""
                    {
                    "customer": {
                        "id": 44521,
                        "age": 27,
                        "element":[{"element2":[{"chiave1":"valore1","nome":true}],"element1":1}],
                        "fullName": "Emily Jenkins",
                        "test":[{"cognome":"pippo","nome":"pippo"},{"nome":"paperino"},{"nome":"topolino"}],
                        "testNumerico":[1,2,3],
                        "arr":["A","C","B"]

                    }
                }
                        """);
        String json = """
                {
                    "customer": {
                        "id": 44521,
                        "fullName": "Emily Jenkins",
                        "age": 27,
                        "element":[{"element1":1,"element2":[{"chiave1":"valore1","nome":true}]}],
                        "test":[{"nome":"pippo","cognome":"pippo"},{"nome":"topolino"},{"nome":"paperino"}],
                        "testNumerico":[3,2,1],
                         "arr":["A","B","C"]
                    }
                }
                """;
        first = test.decodeJson("""
                {"element": [
                			{
                				"element2": [
                					{
                						"nome": true,
                						"chiave1": "valore3"
                					}
                				],
                				"element1": 1
                			},
                			{
                				"element2": [
                					{
                						"chiave1": "valore1",
                						"nome": true
                					}
                				],
                				"element1": 1
                			},
                			{
                				"element2": [
                					{
                						"chiave1": "valore2",
                						"nome": true
                					}
                				],
                				"element1": 1
                			}
                		]}
                """);
        json = """
                 {"element": [
                	{
                		"element2": [
                			{
                				"chiave1": "valore2",
                				"nome": true
                			}
                		],
                		"element1": 1
                	},
                	{
                		"element2": [
                			{
                				"chiave1": "valore3",
                				"nome": true
                			}
                		],
                		"element1": 1
                	},
                	{
                		"element2": [
                			{
                				"chiave1": "valore1",
                				"nome": true
                			}
                		],
                		"element1": 1
                	}
                ]}
                 """;
        Map<String, Object> second = test.decodeJson(json);
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

    protected Map<String, Object> decodeJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> type =
                new TypeReference<HashMap<String, Object>>() {
                };
        return mapper.readValue(json, type);

    }


}
