package org.eclipse.crossmeter.platform.communicationchannel.zendesk.model;

/**
 * @author stephenc
 * @since 05/04/2013 08:56
 */
public enum Status {
    NEW,
    OPEN,
    PENDING,
    HOLD,
    SOLVED,
    CLOSED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
