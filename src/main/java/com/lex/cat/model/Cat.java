package com.lex.cat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lex.cat.model.type.CatColor;

import javax.persistence.*;

@Entity
@Table(name = "cats")
public class Cat {

    @Id
    private String name;

    @Column(name = "color")
    private CatColor color;

    @Column(name = "tail_length")
    @JsonProperty("tail_length")
    private Integer tailLength;

    @Column(name = "whiskers_length")
    @JsonProperty("whiskers_length")
    private Integer whiskersLength;

    public Cat() {
    }

    public Cat(String name, CatColor color, Integer tailLength, Integer whiskersLength) {
        this.name = name;
        this.color = color;
        this.tailLength = tailLength;
        this.whiskersLength = whiskersLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatColor getColor() {
        return color;
    }

    public void setColor(CatColor color) {
        this.color = color;
    }

    public Integer getTailLength() {
        return tailLength;
    }

    public void setTailLength(Integer tailLength) {
        this.tailLength = tailLength;
    }

    public Integer getWhiskersLength() {
        return whiskersLength;
    }

    public void setWhiskersLength(Integer whiskersLength) {
        this.whiskersLength = whiskersLength;
    }
}
