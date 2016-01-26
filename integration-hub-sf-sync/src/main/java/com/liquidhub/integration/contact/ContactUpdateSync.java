package com.liquidhub.integration.contact;

import com.liquidhub.integration.MessageProcessorImpl;
import com.liquidhub.integration.beans.User;
import org.springframework.stereotype.Component;

@Component("ContactUpdateSync")

public class ContactUpdateSync extends MessageProcessorImpl {

    @Override
    public boolean processMessage(User user) {
        return false;
    }
}
