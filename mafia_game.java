import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class mafia_game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Mafia");
        System.out.println("Enter Number of players:");
        int n = in.nextInt();
        while (n<6){
            System.out.println("Number of players should be greater than or equal to 6");
            n = in.nextInt();
        }
        System.out.println("Choose a character");
        System.out.println("1) Mafia");
        System.out.println("2) Detective");
        System.out.println("3) Healer");
        System.out.println("4) Commoner");
        System.out.println("5) Assign Randomly");
        int charac = in.nextInt();
        new initiate(n, charac);
    }
}

class initiate{
    private int n;
    private int charac;
    private int t_mafia;
    private int t_detective;
    private int t_healers;
    private int t_commoners;
    ArrayList<user> players;

    initiate(int n1, int n2){
        n = n1;
        charac = n2;
        t_mafia = n/5;
        t_detective = n/5;
        fix_healers(n);
        t_commoners = n-t_mafia - t_detective - t_healers;
        players = new ArrayList<user>(n);
        create();
        play();
    }

    void play(){
        Collections.shuffle(players, new Random());
        for (int i=0;i<players.size();i++){
            if(charac ==1){
                if ((new mafia()).equals(players.get(i))){
                    players.get(i).setCharac(1);
                    System.out.println("You are Player "+players.get(i).getId());

//                    for(int j=0;j<n/5;j++) {
//                        System.out.println("You are Mafia. Other mafias are:" +);
//                    }
//                    System.out.println("You are a mafia");
                    int round=1;
                    while (test()){
                        print(charac, round, players.get(i));
//                        System.out.println("Round "+round+":");
                        (new mafia()).play(players.get(i), players);
                        round++;
//                        players.get(i).play(players);
                    }
                    break;
                }
            }else if(charac ==2){
                if ((new detective()).equals(players.get(i))){
                    players.get(i).setCharac(2);
                    System.out.println("You are Player "+players.get(i).getId());
//                    System.out.println("You are a detective");
                    int round=1;
                    while (test()){
                        print(charac, round, players.get(i));
//                        System.out.println("Round "+round+":");
                        (new mafia()).play(players.get(i), players);
                        round++;
                    }
                    break;
                }
            }else if (charac==3){
                if ((new healer()).equals(players.get(i))){
                    players.get(i).setCharac(3);
                    System.out.println("You are Player "+players.get(i).getId());
//                    System.out.println("You are a healer");
                    int round=1;
                    while (test()){
//                        System.out.println("Round "+round+":");
                        print(charac, round, players.get(i));
                        (new mafia()).play(players.get(i), players);
                        round++;
                    }
                    break;
                }
            }else if (charac==4){
                if ((new commoner()).equals(players.get(i))){
                    players.get(i).setCharac(4);
                    System.out.println("You are Player "+players.get(i).getId());
//                    System.out.println("You are a commoner");
                    int round=1;
                    while (test()){
                        print(charac, round, players.get(i));
//                        System.out.println("Round "+round+":");
                        (new mafia()).play(players.get(i), players);
                        round++;
                    }
                    break;
                }
            }else if(charac==5){
                Random rand = new Random();
                charac =0;
                while(charac==0){
                    charac = rand.nextInt(5);
                }
//                System.out.println(charac);
//                System.out.println("I am running");
                play();
            }
        }
    }

    void print(int c, int round, user master){
        ArrayList<mafia> temp1 = new ArrayList<>();
        ArrayList<detective> temp2 = new ArrayList<>();
        ArrayList<healer> temp3 = new ArrayList<>();
        ArrayList<commoner> temp4 = new ArrayList<>();
        for(int i=0;i<players.size();i++){
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new mafia()) && players.get(i).getId() !=master.getId()) {
//                System.out.print("Player" + players.get(i).getId() + " ");
                temp1.add((mafia)players.get(i));
            }
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new detective()) && players.get(i).getId() !=master.getId()) {
//                System.out.print("Player" + players.get(i).getId() + " ");
                temp2.add((detective)players.get(i));
            }
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new healer()) && players.get(i).getId() !=master.getId()) {
//                System.out.print("Player" + players.get(i).getId() + " ");
                temp3.add((healer)players.get(i));
            }
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new commoner()) && players.get(i).getId() !=master.getId()) {
//                System.out.print("Player" + players.get(i).id + " ");
                temp4.add((commoner)players.get(i));
            }
        }

        if(round==1) {
//            user temp;
            if (c == 1) {
                details<mafia> temp = new details<>(temp1, new mafia());
                System.out.println("You are a mafia");
                temp.printdet();
//                temp = new mafia();
//                System.out.print("You are a Mafia. Other mafias are:");
            } else if (c == 2) {
                details<detective> temp = new details<>(temp2, new detective());
                System.out.println("You are a detective");
                temp.printdet();
//                temp = new detective();
//                System.out.print("You are a Detective. Other detectives are:");
            } else if (c == 3) {
                details<healer> temp = new details<>(temp3, new healer());
                System.out.println("You are a healer");
                temp.printdet();
//                temp = new healer();
//                System.out.print("You are a Healer. Other healers are:");
            } else {
                details<commoner> temp = new details<>(temp4, new commoner());
                System.out.println("You are a commoner");
                temp.printdet();
//                temp = new commoner();
//                System.out.print("You are a Commoner. Other commoners are:");
            }
//            int alive = 0;
//            for (int i = 0; i < players.size(); i++) {
//                if (players.get(i).status == 1 && temp.equals(players.get(i)) && players.get(i).id !=master.id) {
//                    System.out.print("Player" + players.get(i).id + " ");
//                }
//                if (players.get(i).status == 1) alive++;
            }
//            System.out.println();
//        }
        System.out.println("Round "+round+":");
        int alive=0;
        for(int i=0;i<players.size();i++){
            if(players.get(i).getStatus()==1){
                System.out.print("Player"+players.get(i).getId() +" ");
                alive++;
            }
        }
        System.out.println();
        System.out.println(alive+" players are remaining:");
    }

    Boolean test(){
        int m = 0;
        int nm = 0;
        for(int i=0;i<players.size();i++) {
            if ((new mafia()).equals(players.get(i)) && players.get(i).getStatus() == 1) {
                m++;
            } else if(!((new mafia()).equals(players.get(i))) && players.get(i).getStatus() == 1){
                nm++;
            }
        }

        if(m==0) {
            System.out.println("Game Over");
            System.out.println("The Mafias have lost.");
            return false;
        }else if(m>=nm){
            System.out.println("Game Over");
            System.out.println("The Mafias have won");
            return false;
        }else {
            return true;
        }
    }

    void fix_healers(int n){
        if(n/10>1){
            t_healers = n/10;
        }else {
            t_healers = 1;
        }
    }

    void create(){
        int count=1;
        for(int i=0;i<t_mafia;i++){
            user m = new mafia(count);
            players.add(m);
            count++;
        }
        for(int i=0;i<t_detective;i++){
            user m = new detective(count);
            players.add(m);
            count++;
        }
        for(int i=0;i<t_healers;i++){
            user m = new healer(count);
            players.add(m);
            count++;
        }
        for(int i=0;i<t_commoners;i++){
            user m = new commoner(count);
            players.add(m);
            count++;
        }
    }
}