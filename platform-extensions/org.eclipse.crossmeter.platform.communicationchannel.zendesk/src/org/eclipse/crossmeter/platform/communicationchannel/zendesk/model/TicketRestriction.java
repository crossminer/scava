package org.eclipse.crossmeter.platform.communicationchannel.zendesk.model;

public enum TicketRestriction {
    ORGANIZATION("organization"),
    GROUPS("groups"),
    ASSIGNED("assigned"),
    REQUESTED("requested");

    private final String name;

    private TicketRestriction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
