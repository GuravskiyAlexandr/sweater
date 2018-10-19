package com.example.sweater.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tmp {
    public static void main(String[] args) {
    //     List<Role> roles = Arrays.asList( Role.values());
         Set<String> set = new HashSet<>();
        for (Role r : Role.values()) {
           set.add(String.valueOf(r));
        }

        System.out.println(set +"  ");
    }
}
