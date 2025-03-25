package jp.kazutech.springtemplate.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserAuthority {
    CONTACT_POST,;

    public String getAuthority() {
        return this.name();
    }
}
