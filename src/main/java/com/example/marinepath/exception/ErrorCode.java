package com.example.marinepath.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //    ACCOUNTS | CODE: 1XXX
//    Accounts | Auth
    UNCATEGORIZED_EXCEPTION(1001, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "User name must be at least 3 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1005, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1006, "Invalid token", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_VERIFY(1007, "This account has not been verified", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1008, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    OLD_PASSWORD_INCORRECT(1009, "Old password incorrect", HttpStatus.BAD_REQUEST),
    PASSWORD_REPEAT_INCORRECT(1010, "Password repeat do not match", HttpStatus.BAD_REQUEST),
    NOT_LOGIN(1011, "You need to login", HttpStatus.BAD_REQUEST),
    USERNAME_PASSWORD_NOT_CORRECT(1012, "Username or password is not correct", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1013,"Account not found", HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND(1013,"Email not found, please register account.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1014, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INTERNAL_ERROR(1015, "Internal Error", HttpStatus.INTERNAL_SERVER_ERROR),
    SUCCESS(200, "Success",HttpStatus.OK),

    //    Accounts | Emails | CODE: 11XX
    INVALID_EMAIL(1100, "Invalid email", HttpStatus.BAD_REQUEST),
    EMAIL_WAIT_VERIFY(1101, "This email has been registered and is not verified, please verify and login", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1102, "This email has been registered, please log in!", HttpStatus.BAD_REQUEST),
    ACCOUNT_DELETED(1103,"This Account has been deleted.", HttpStatus.GONE),

    // Company | CODE: 12XX
    COMPANY_NOT_FOUND(1201,"Company not found", HttpStatus.NOT_FOUND),

    // Container | CODE: 13XX
    CONTAINER_NOT_FOUND(1301,"Container not found", HttpStatus.NOT_FOUND),

    // Container Receipt | CODE: 14XX
    CONTAINER_RECEIPT_NOT_FOUND(1401,"Container Receipt not found", HttpStatus.NOT_FOUND),
    CONTAINER_RECEIPT_DELETED(1402,"This Container Receipt has been deleted.", HttpStatus.GONE),
    // Customer | CODE: 15XX
    CUSTOMER_NOT_FOUND(1501,"Customer not found", HttpStatus.NOT_FOUND),

    // Incident Report| CODE: 16XX
    INCIDENT_REPORT_NOT_FOUND(1601,"Incident Report not found", HttpStatus.NOT_FOUND),

    // Order | CODE: 17XX
    ORDER_NOT_FOUND(1701,"Order not found", HttpStatus.NOT_FOUND),
    ORDER_DELETED(1702,"This Order has been deleted.", HttpStatus.GONE),

    // Port | CODE: 18XX
    PORT_NOT_FOUND(1801,"Port not found", HttpStatus.NOT_FOUND),
    PORT_DELETED(1802,"This port has been deleted.", HttpStatus.GONE),

    // Port Document | CODE: 19XX
    PORT_DOCUMENT_NOT_FOUND(1901,"Port Document not found", HttpStatus.NOT_FOUND),

    // Ship | CODE: 20XX
    SHIP_NOT_FOUND(2001,"Ship not found", HttpStatus.NOT_FOUND),
    SHIP_DELETED(2102,"Ship is deleted", HttpStatus.NOT_FOUND),

    // Trip | CODE: 21XX
    TRIP_NOT_FOUND(2101,"Trip not found", HttpStatus.NOT_FOUND),
    TRIP_DELETED(2102,"This trip has been deleted.", HttpStatus.GONE),

    // Trip Segment | CODE: 22XX
    TRIP_SEGMENT_NOT_FOUND(2201,"Trip Segment not found", HttpStatus.NOT_FOUND),
    TRIP_SEGMENT_DELETED(2202,"This trip Segment has been deleted.", HttpStatus.GONE);

    private final Integer code;
    @Setter
    private String message;
    @Getter
    private final HttpStatus httpStatus;

    ErrorCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
