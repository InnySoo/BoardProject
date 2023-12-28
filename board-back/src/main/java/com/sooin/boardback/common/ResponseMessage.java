package com.sooin.boardback.common;

public interface ResponseMessage {

  // HTTP Status 200
  String SUCCESS = "Success.";

  // HTTP Status 400
  String VALIDATION_FAILED = "Validation Failed.";
  String DUPLICATED_EMAIL = "Duplicated Email.";
  String DUPLICATED_NICKNAME = "Duplicated Nickname.";
  String DUPLICATED_TEL_NUMBER = "Duplicated Tel Number.";
  String NOT_EXISTED_USER = "Not Existed User.";
  String NOT_EXISTED_BOARD = "Not Existed Board.";

  // HTTP Status 401
  String SIGN_IN_FAIL = "Login Information Mismatch.";
  String AUTHORIZATION_FAIL = "Authorization Failed.";

  // HTTP Status 403
  String NO_PERMISSION = "Do Not Have Permission.";

  // HTTP Status 500
  String DATABASE_ERROR = "Database Error.";
  
}
