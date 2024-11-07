package com.torre.backend.product.utils;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class CodeGenerator {

  private final char[] chars = new char[]{
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };
  private final Random random = new Random();

  public String generateCode() {
    StringBuilder builder = new StringBuilder();
    int codeLength = 16;
    for (int i = 0; i < codeLength; i++) {
      if (i % 4 == 0 && i > 1) {
        builder.append("-");
      }
      builder.append(chars[(random.nextInt(35))]);
    }
    return builder.toString();
  }
}
