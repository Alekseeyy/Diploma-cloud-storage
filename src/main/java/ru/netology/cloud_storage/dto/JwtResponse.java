package ru.netology.cloud_storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final String TOKEN_PROPERTY_NAME = "auth-token";

    private static final long serialVersionUID = -8091879091924046844L;

    @JsonProperty(TOKEN_PROPERTY_NAME)
    private String authToken;
}
