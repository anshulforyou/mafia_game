import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

abstract class user{
    private int HP;
    private int id;
    private int status; // Here if user is alive then status would be 1 else 2 for mafia's target, 4 for detectives selection and 0 if the player is dead.
    private int charac;

    int getHP(){
        return HP;
    }
    void setHP(int temp){
        HP = temp;
    }
    int getId(){return id;}
    void setId(int temp){id = temp;}

    int getStatus(){return status;}
    void setStatus(int temp){status=temp;}

    int getCharac(){return charac;}
    void setCharac(int temp){charac=temp;}

    user(int hp1, int nm){
        HP = hp1;
        id = nm;
        charac=0;
        status =1;
    }
    user(){}

    @Override
    public String toString() {
        return "Player"+id;
    }

    abstract void role(user u, ArrayList<user> players, user target);

    abstract void play(user master, ArrayList<user> players);
}

class mafia extends user implements Comparable<mafia>{
    mafia(){}
    mafia(int nm1){
        super(2500, nm1);
    }

    public int compareTo(mafia m){
        return this.getHP() - m.getHP();
    }

     public boolean equals(Object obj){
        if(obj.getClass() == this.getClass())return true;
        else return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    void play(user master, ArrayList<user> players){
        if(master.getCharac()==1){
            Scanner in = new Scanner(System.in);
            System.out.println("Please choose a target:");
            boolean flag = true;
            int target =-1;
            user u = new mafia();
            while (flag) {
                if(target!=-1){
                    System.out.println("Invalid player ID");
                }
                target = in.nextInt();
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getId() == target && players.get(i).getStatus() == 1 && !((new mafia()).equals(players.get(i)))) {
                        flag= false;
                        u = players.get(i);
                        break;
                    }
                }
            }
            role(master, players, u);
        }else {
            Random rand = new Random();
            int target = 0;
            boolean flag = true;
            user u = new mafia();
            while (flag) {
                target = rand.nextInt(players.size());
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getId() == target && players.get(i).getStatus() == 1 &&  !((new mafia()).equals(players.get(i)))) {
                        flag= false;
                        u = players.get(i);
                        break;
                    }
                }
            }
            role(master, players, u);
        }
    }

    @Override
    void role(user master, ArrayList<user> players, user target) {
        ArrayList<mafia> mafia = new ArrayList<>();
        int m=0;
        for(int i=0;i<players.size();i++){
            if(players.get(i).getStatus()==1 && (new mafia()).equals(players.get(i))){
                mafia.add((mafia)players.get(i));
                m++;
            }
        }
        sortmafia(mafia);
        System.out.println("Mafias have chosen their target");
//        people.get(pl1);
        int total=0;
        for(int i=0;i<mafia.size();i++){
            total+=mafia.get(i).getHP();
        }

        if(total>=target.getHP()){
//            int rat = people.get(target).HP/m;
            int x = target.getHP();
            int y = m;
            for(int i = 0;i<mafia.size();i++){
                int temp1 = mafia.get(i).getHP() - x/y;
                if(temp1>0){
//                    total = total - x/y;
                    mafia.get(i).setHP(temp1);
                }else {
//                    total -= mafia.get(i).HP;
                    x -= mafia.get(i).getHP();
                    y--;
                    mafia.get(i).setHP(0);
                }
            }
            target.setHP(0);
            target.setStatus(2);
        }else {
            int x = target.getHP();
            int y = m;
            for(int i = 0;i<mafia.size();i++){
                int temp1 = mafia.get(i).getHP() - x/y;
                if(temp1>0){
//                    total = total - x/y;
                    mafia.get(i).setHP(temp1);
                }else {
//                    total -= mafia.get(i).HP;
                    x -= mafia.get(i).getHP();
                    y--;
                    mafia.get(i).setHP(0);
                }
            }
            target.setHP(target.getHP()-total);
        }
        (new detective()).play(master, players);
    }

    ArrayList sortmafia(ArrayList<mafia> list){
        Collections.sort(list);
        return list;
    }
}

class detective extends user{
    detective(){}

    public boolean equals(Object obj){
        if(obj.getClass() == this.getClass())return true;
        else return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    detective(int nm){
        super(800, nm);
    }

    int check(ArrayList<user> players){
        int count =0;
        for(int i=0;i<players.size();i++){
            if((new detective()).equals(players.get(i)) && (players.get(i).getStatus()==1 || players.get(i).getStatus()==2))count++;
        }
        return count;
    }

    @Override
    void play( user master, ArrayList<user> players){
        if(check(players)>0) {
            if (master.getCharac() == 2) {
                Scanner in = new Scanner(System.in);
                System.out.println("Please choose a user to test:");
                int choose = -1;
                boolean flag = true;
                user u = new mafia();
                while (flag) {
                    if(choose!=-1){
                        System.out.println("Invalid player ID");
                    }
                    choose = in.nextInt();
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getId() == choose && players.get(i).getStatus() == 1) {
                            flag = false;
                            u = players.get(i);
                            break;
                        }
                    }
                }
                role(master, players, u);
            } else {
                Random rand = new Random();
//                int choose = rand.nextInt(players.size());
                boolean flag = true;
                user u = new mafia();
                while (flag) {
                    int choose = rand.nextInt(players.size());
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getId() == choose && players.get(i).getStatus() == 1) {
                            flag = false;
                            u = players.get(i);
                            break;
                        }
                    }
                }
                role(master, players, u);
            }
        }else {
            (new healer()).play(master, players);
        }
    }

    @Override
    void role(user master, ArrayList<user> players, user choose) {
        System.out.println("Detective has chosen someone to identify");
        if((new mafia()).equals(choose)){
//        if(people.get(choose).getClass() == (new mafia()).getClass()){
            for(int i=0;i<players.size();i++){
                if(players.get(i).getId() == choose.getId()){
                    players.get(i).setStatus(4);
//                    System.out.println("Mafia found out by detective");
                    System.out.println("Player "+players.get(i).getId()+" is a mafia");
                }
            }
        }
        else {
//            System.out.println("Player "+choose.id +" is not a mafia");
        }
        (new healer()).play(master, players);
    }
}

class healer extends user{
    healer(){};
    healer(int nm){
        super(800, nm);
    }

    public boolean equals(Object obj){
        if(obj.getClass() == this.getClass())return true;
        else return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    int check(ArrayList<user> players){
        int count =0;
        for(int i=0;i<players.size();i++){
            if((new healer()).equals(players.get(i)) && players.get(i).getStatus()==1)count++;
        }
        return count;
    }

    @Override
    void play( user master, ArrayList<user> players) {
        if(check(players)>0) {
            if (master.getCharac() == 3) {
                Scanner in = new Scanner(System.in);
                System.out.println("Please choose a player to heal");
                boolean flag = true;
                user u = new mafia();
                int choose=-1;
                while (flag) {
                    if(choose!=-1){
                        System.out.println("Invalid player ID");
                    }
                    choose = in.nextInt();
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getId() == choose && (players.get(i).getStatus() == 1 || players.get(i).getStatus() == 2)) {
                            u = players.get(i);
                            flag = false;
                        }
                    }
                }
                role(master, players, u);
            } else {
                Random rand = new Random();
                boolean flag = true;
                user u = new mafia();
                while (flag) {
                    int choose = rand.nextInt(players.size());
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getId() == choose && (players.get(i).getStatus() == 1 || players.get(i).getStatus() == 2)) {
                            u = players.get(i);
                            flag = false;
                        }
                    }
                }
                role(master, players, u);
            }
        }else {
            for(int i=0;i<players.size();i++){
                if(players.get(i).getStatus() == 2){
                    players.get(i).setStatus(0);
                    System.out.println("Player "+players.get(i).getId()+" has died");
                }
            }
            (new commoner()).play(master, players);
        }
    }

    @Override
    void role(user master, ArrayList<user> players, user choose) {

        System.out.println("Healer has chosen someone to heal");
        if(choose.getStatus() == 2){
            for(int i=0;i<players.size();i++){
                if(players.get(i).getId() == choose.getId()){
                    players.get(i).setHP(500);
                    players.get(i).setStatus(1);
                    System.out.println("Healer saved someone");
                }
            }
        }else {
            for(int i=0;i<players.size();i++){
                if(players.get(i).getStatus() == 2){
                    players.get(i).setStatus(0);
                    System.out.println("Player "+players.get(i).getId()+" has died");
                }
                if(players.get(i).getId() == choose.getId() && players.get(i).getStatus() ==1){
//                    players.get(i).HP += 500;
                    players.get(i).setHP(players.get(i).getHP()+500);
                }
            }
//            System.out.println();
        }
        (new commoner()).play(master, players);
    }
}

class commoner extends user{
    commoner(){};
    commoner(int nm){
        super(1000, nm);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean equals(Object obj){
        if(obj.getClass() == this.getClass())return true;
        else return false;
    }

    @Override
    void play( user master, ArrayList<user> players){
        boolean flag = true;
        for (int i=0;i< players.size();i++){
            if(players.get(i).getStatus()==4){
                flag = false;
                players.get(i).setStatus(0);
                System.out.println("Player "+players.get(i).getId()+" is killed because he was a mafia");
            }
        }
        if(master.getStatus()==0){
            flag=false;
            role(master, players, new mafia());
        }
        if(flag == true) {
            System.out.println("Please choose your vote");
            Scanner in = new Scanner(System.in);
            int vote = -1;
            boolean flag2 = true;
            user u = new mafia();
            while(flag2) {
                if(vote!=-1){
                    System.out.println("Invalid player ID");
                }
                vote = in.nextInt();
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getId() == vote && players.get(i).getStatus() == 1) {
                        flag2 = false;
                        u = players.get(i);
                    }
                }
            }
            role(master, players, u);
        }
    }

    @Override
    void role(user master, ArrayList<user> players, user choose){

        ArrayList<mafia> temp1 = new ArrayList<>();
        ArrayList<detective> temp2 = new ArrayList<>();
        ArrayList<healer> temp3 = new ArrayList<>();
        ArrayList<commoner> temp4 = new ArrayList<>();

        int votes[] = new int[players.size()];
        for(int i=0;i<players.size();i++) {
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new mafia())) temp1.add((mafia)players.get(i));
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new detective())) temp2.add((detective)players.get(i));
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new healer())) temp3.add((healer)players.get(i));
            if (players.get(i).getStatus() == 1 && players.get(i).equals(new commoner())) temp4.add((commoner)players.get(i));
            if(players.get(i).getStatus()!=1) votes[i] =-1;
        }

        details<mafia> temp11 = new details<>(temp1);
        votes = temp11.givevotes(votes);
        details<detective> temp22 = new details<>(temp2);
        votes = temp22.givevotes(votes);
        details<healer> temp33 = new details<>(temp3);
        votes = temp33.givevotes(votes);
        details<commoner> temp44 = new details<>(temp4);
        votes = temp44.givevotes(votes);

        votes[choose.getId()]++;

        int target=0;
        int temp =0;
        for(int i=0;i<votes.length;i++){
            if(votes[i]>temp){
                temp = votes[i];
                target =i;
            }
        }
        players.get(target).setStatus(0);
        System.out.println("Player "+ players.get(target).getId()+" is killed by vote out");
    }
}

class details<T>{
    T obj;
    ArrayList<T> temp;
    details(ArrayList<T> a, T b){
        temp = a;
        obj = b;
    }
    details(ArrayList<T> a){
        temp =a;
    }
    int[] givevotes(int cal[]){
        Random rand = new Random();
        for(int i=0;i<temp.size();i++){
            int vote = rand.nextInt(cal.length);
            boolean flag = true;
            while(flag) {
                if(cal[vote]!=-1) {
                    flag = false;
                    cal[vote]++;
                }else vote = rand.nextInt(cal.length);
            }
        }
        return cal;
    }
    void printdet(){
        if(temp.size()>0) {
            System.out.print("Other players of your type are: ");
            for (int i = 0; i < temp.size(); i++) {
                System.out.print(temp.get(i).toString() + " ");
            }
            System.out.println();
        }else {
            System.out.println("There are no other players of your type");
        }
    }
}