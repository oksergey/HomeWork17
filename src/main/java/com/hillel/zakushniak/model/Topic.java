package com.hillel.zakushniak.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    private int id;
    private String name;

    @Override
    public String toString() {
        return "\n"+"Topic{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}' ;
    }
}
