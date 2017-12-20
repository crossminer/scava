package model;

import play.data.validation.Constraints.*; 

public class Invitation {

    @Required
    public String email;

}