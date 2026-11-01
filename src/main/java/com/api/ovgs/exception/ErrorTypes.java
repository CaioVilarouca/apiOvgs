package com.api.ovgs.exception;

public final class ErrorTypes {

    private static final String BASE_URL = "https://localhost/errors";

    public static final String UNAUTHORIZED = BASE_URL + "/unauthorized";

    public static final String INTERNAL_ERROR = BASE_URL + "/internal-error";

    public static final String NOT_FOUND = BASE_URL + "/not-found";

    public static final String CONFLICT = BASE_URL + "/conflict";
}