package com.example.demo.sprinkle;

import java.util.Set;

public interface TokenGenerator {
    String generate(Set<String> existing);
}
