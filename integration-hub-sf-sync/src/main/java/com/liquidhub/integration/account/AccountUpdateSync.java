package com.liquidhub.integration.account;

import com.liquidhub.integration.MessageProcessorImpl;
import com.liquidhub.integration.beans.User;
import org.springframework.stereotype.Component;

@Component("AccountUpdateSync")
public class AccountUpdateSync extends MessageProcessorImpl {
    @Override
    public boolean processMessage(User user) {
        return false;
    }
}
