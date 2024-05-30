package com.majorproject.re_listing.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {

    private String jwtToken;
    private String username;


    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    public static class JwtResponseBuilder {
        private String jwtToken;
        private String username;

        private JwtResponseBuilder() {
        }

        public JwtResponseBuilder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public JwtResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JwtResponse build() {
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.jwtToken = this.jwtToken;
            jwtResponse.username = this.username;
            return jwtResponse;
        }
    }
}
