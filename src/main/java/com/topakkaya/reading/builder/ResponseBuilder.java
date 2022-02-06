package com.topakkaya.reading.builder;

import com.topakkaya.reading.enums.ReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseBuilder {
    private final HttpStatus responseStatus;
    private final String content = "Content";
    private final String errorMessage = "error";
    private final Map<String, Object> result = new HashMap<>();

    public ResponseBuilder(final HttpStatus responseStatus, final ReturnType returnType) {
        this.responseStatus = responseStatus;
        this.result.put("returnCode", returnType.getResultCode());
        this.result.put("resultMessage", returnType.getResultMessage());
        this.result.put("isPaginated", false);
    }

    public ResponseBuilder withData(final Object data) {
        this.result.put(content, data);
        return this;
    }

    public ResponseBuilder withDataList(final List<Object> dataList) {
        this.result.put(content, dataList);
        return this;
    }

    public ResponseEntity<Map<String, Object>> build() {
        return new ResponseEntity<>(this.result, this.responseStatus);
    }

    public ResponseBuilder withError(final String error) {
        this.result.put(errorMessage, error);
        return this;
    }

    public ResponseBuilder withPagination(final Object object) {
        this.result.put("isPaginated", true);
        this.result.put(content, object);
        return this;
    }
}
