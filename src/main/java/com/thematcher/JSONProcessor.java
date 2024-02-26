package com.thematcher;

import com.thematcher.data.Node;
import jakarta.json.*;
import jakarta.json.stream.JsonParser;
import org.codehaus.plexus.interpolation.PrefixAwareRecursionInterceptor;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Iterator;

public class JSONProcessor {

    public static void main(String[] args){
        JSONProcessor jp = new JSONProcessor();
        String left = """
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
                        """;
        String right = """
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
        //final JsonParser leftParser = Json.createParser(new StringReader(left));
        final JsonParser leftParser = Json.createParser(new StringReader("{\"array\":[1,\"elementoarray\",2,true],\"left\":\"b\",\"c\":\"d\",\"obj\":{\"field1\":true}}"));

        final JsonParser rightParser = Json.createParser(new StringReader(right));
        //System.out.println(parser(leftParser,null));

        //System.out.println(manageVisit(leftParser));
        System.out.println(manageVisit(rightParser));
    }

    public static Node manageVisit(JsonParser parser){
        Node root = new Node(new Node.NodoValore(null,null),null,0);
        root.setTipoNodo(Node.TipoNodo.ROOT);
        Node currentParent = root;
        Node currentNode = null;
        while (parser.hasNext()) {
            final JsonParser.Event event = parser.next();
            switch (event) {
                case START_OBJECT :
                    currentNode = visitObject(parser,currentParent);
                    currentParent=currentNode;
                    break;
                case START_ARRAY:
                    currentNode = visitArray(parser,currentParent);
                    currentParent= currentNode;
                    break;
            }
        }
        return root;
    }


    private static Node visitObject(JsonParser parser,Node parent){
        JsonParser.Event event = parser.currentEvent();
        Node currentNode = null;
        Node currentParent = parent;
        while(event != JsonParser.Event.END_OBJECT && parser.hasNext()){
         event= parser.next();
         switch(event){
             case KEY_NAME :
                 currentNode=createNode(parent,parser.getString());
                 currentParent=currentNode;
                 break;
             case VALUE_STRING:
                 String string = parser.getString();
                 currentNode .getData().setValue(string);
                 break;
             case VALUE_NULL:
                 currentNode .getData().setValue(null);
                 break;
             case VALUE_NUMBER:
                 BigDecimal number = parser.getBigDecimal();
                 currentNode.getData().setValue( number);
                 break;
             case VALUE_TRUE:
                 currentNode.getData().setValue(true);
                 break;
             case VALUE_FALSE:
                 currentNode.getData().setValue(false);
                 break;
             case START_OBJECT:
                 //parser.next();
                 currentNode = visitObject(parser,currentParent);
                 currentParent=currentNode;
                 break;
             case START_ARRAY:
                 currentNode = visitArray(parser,currentParent );
                 break;
             default:
                 parser.next();
                 break;

         }
        }
        return parent;
    }

    private static Node visitArray(JsonParser parser,Node parent){
        JsonParser.Event event = parser.currentEvent();
        Node currentNode = null;
        Node currentParent = parent;
        while(event != JsonParser.Event.END_ARRAY && parser.hasNext()){
            event= parser.next();
            parent.setTipoNodo(Node.TipoNodo.ARRAY);
            switch(event){
                case VALUE_STRING:
                    String string = parser.getString();
                    currentNode = createNodeArray(currentParent,string);
                    break;
                case VALUE_NUMBER:
                    BigDecimal number = parser.getBigDecimal();
                    currentNode = createNodeArray(currentParent,number);
                    break;
                case VALUE_TRUE:
                    currentNode = createNodeArray(currentParent,true);
                    break;
                case VALUE_FALSE:
                    currentNode = createNodeArray(currentParent,false);
                    break;
                case START_OBJECT:
                     currentNode=visitObject(parser,currentParent);
                     currentParent=currentNode;
                    break;
                case START_ARRAY:
                    currentNode = visitArray(parser,currentParent);
                    break;
            }
        }
        return parent;
    }

    private static Node createNodeArray(Node parent,Object value){
        Node result =  new Node(new Node.NodoValore(null,value) ,parent,parent.getLevel()+1);
        parent.addChild(result);

        return result;
    }

    private static Node createNode(Node parent,String name){
        Node result =  new Node(new Node.NodoValore(name,null) ,parent,parent.getLevel()+1);
        parent.addChild(result);
        return result;
    }


}
