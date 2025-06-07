package org.cm.doan2.common;


import lombok.Data;

@Data
public class SessionUser {
    private String email;
    private String role;
    private long id;
}
