/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.darkboundrpg;
import player.*;
import player.spells.*;
import game.*;
import enemy.*;

import java.io.IOException;
import java.lang.Math;
import java.util.*;

/**
 *
 * @author User
 */
public class DarkBoundRPG {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Choice choice = new Choice();

        System.out.println("Choose a Class: ");
        Archetype playerCharacter = Archetype.createCharacter(choice.getChoice());

        playerCharacter.printInfo();
        System.out.println("\n+------------+");
        System.out.println("Earn 5000 XP!");
        System.out.println("+------------+\n");
        playerCharacter.gainExperience(5000);
        playerCharacter.printInfo();
        System.out.println("\n+------------+");
        System.out.println("Earn 120000 XP!");
        System.out.println("+------------+\n");
        playerCharacter.gainExperience(120000);
        playerCharacter.printInfo();
    }
}
