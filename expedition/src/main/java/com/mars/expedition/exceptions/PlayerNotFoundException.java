package com.mars.expedition.exceptions;

public class PlayerNotFoundException extends RuntimeException {
  public PlayerNotFoundException(String message) {
    super(message);
  }
}
