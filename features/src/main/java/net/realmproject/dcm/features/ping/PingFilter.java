package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.features.message.MessageTypeFilter;


public class PingFilter extends MessageTypeFilter {

    public PingFilter() {
        super(PingMessage.PING_MESSAGE_TYPE);
    }

}
