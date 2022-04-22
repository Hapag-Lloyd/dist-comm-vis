package com.hlag.tools.commvis.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.ws.rs.HttpMethod;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class HttpEndpoint implements IEndpoint {
    private String classname;
    private String methodName;

    private String type;
}
