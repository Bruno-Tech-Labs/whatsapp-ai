package com.brunotech.api.response;

public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ApiError error;

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.error = null;
    }

    public ApiResponse(ApiError error) {
        this.success = false;
        this.data = null;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }
}
