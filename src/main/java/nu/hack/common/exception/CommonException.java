package nu.hack.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final int status;
    private final String message;

    public CommonException(Integer status, String message) {
        this.message = message;
        this.status = status;
    }
}
