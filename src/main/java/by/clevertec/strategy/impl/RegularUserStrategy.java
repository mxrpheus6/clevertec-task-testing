package by.clevertec.strategy.impl;

import by.clevertec.strategy.UserStrategy;

public class RegularUserStrategy implements UserStrategy {

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Browsing catalog and purchasing items...");
    }

}
