package com.chenqixian.cloud.common.core.web.util;

import cn.hutool.json.JSONUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static void responseWriter(HttpServletResponse response, String contentType, int status, Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONUtil.toJsonStr(value).getBytes());
    }

    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Object value) {
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", contentType);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(ResultUtils.ok(value)).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
