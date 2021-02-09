package com.smart.wccs.model;

public final class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface UserView extends IdName {}

    public interface AdminView extends IdName {}

    public interface FullView extends IdName {}
}