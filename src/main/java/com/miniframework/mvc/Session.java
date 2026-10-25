package com.miniframework.mvc;

import java.util.Objects;

import jakarta.servlet.http.HttpSession;

/**
 * Framework wrapper around the servlet session.
 */
public final class Session {
    private final HttpSession httpSession;

    public Session(HttpSession httpSession) {
        this.httpSession = Objects.requireNonNull(httpSession, "httpSession");
    }

    public Object get(String key) {
        return httpSession.getAttribute(key);
    }

    public void set(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    public void remove(String key) {
        httpSession.removeAttribute(key);
    }

    public void invalidate() {
        httpSession.invalidate();
    }
}
