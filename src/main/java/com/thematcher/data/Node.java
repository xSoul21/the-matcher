package com.thematcher.data;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private NodoValore data;

    private Node parent;

    private Integer level;

    private List<Node>  children;


    public void setTipoNodo(TipoNodo tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    private TipoNodo tipoNodo;

    public enum TipoNodo{
        PRIMITIVO,
        ARRAY,
        OBJECT,
        ROOT
    }

    @Override
    public String toString() {
        if(tipoNodo==TipoNodo.ARRAY) {
            //stampa tutti gli elementi children
            return this.getData().getName() + " : [" + this.children.toString().replace("[","").replace("]","") + "]";
        }else if (tipoNodo==TipoNodo.ROOT){
            return "{"+this.children.toString().replaceFirst("\\[","")+"}";
        }else{
            // potresti essere object o primitivo
            if(this.getData().name!=null && this.getData().getValue()!=null){
                //primitivo
                return this.getData().name+" : "+this.getData().getValue();
            }else if(this.getData().name!=null){
                //oggetto
                return this.getData().name+" : {"+this.getChildren().toString().replace("[","").replace("]","") +"}";
            }else{
                return this.getData().getValue().toString();
            }
        }
    }

    public Node(NodoValore data, Node parent, List<Node> children, Integer level) {
        this.data = data;
        this.parent = parent;
        this.children = children;
        this.level=level;
    }

    /**
     *
     * @param data
     * @param parent
     * @param level
     */
    public Node(NodoValore data, Node parent,Integer level) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.level = level;
    }

    public void addChild(Node nodo){
        this.children.add(nodo);
    }

    public NodoValore getData() {
        return data;
    }

    public Node getParent() {
        return parent;
    }


    public Integer getLevel() {
        return level;
    }

    public List<Node> getChildren() {
        return children;
    }

    public static class NodoValore{
        String name;

        Object value;

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public NodoValore(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public void setName(String name){
            this.name=name;
        }
        public void setValue(Object value){
            this.value=value;
        }
    }
}
