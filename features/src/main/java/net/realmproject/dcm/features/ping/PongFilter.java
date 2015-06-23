package net.realmproject.dcm.features.ping;


import net.realmproject.dcm.features.message.MessageTypeFilter;


public class PongFilter extends MessageTypeFilter {

    public PongFilter() {
        super(PongMessage.PONG_MESSAGE_TYPE);
    }
}
