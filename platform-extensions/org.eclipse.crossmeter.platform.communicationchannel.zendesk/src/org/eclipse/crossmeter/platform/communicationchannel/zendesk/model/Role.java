package org.eclipse.crossmeter.platform.communicationchannel.zendesk.model;

public enum Role {
    END_USER("end-user"),
    AGENT("agent"),
    ADMIN("admin");

    private final String name;

    private Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

