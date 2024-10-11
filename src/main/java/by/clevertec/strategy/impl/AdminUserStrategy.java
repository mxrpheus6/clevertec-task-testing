package by.clevertec.strategy.impl;

import by.clevertec.strategy.UserStrategy;

public class AdminUserStrategy implements UserStrategy {

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Banning users and managing system settings xD");
    }

}
