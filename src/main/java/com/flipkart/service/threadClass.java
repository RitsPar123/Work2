package com.flipkart.service;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;


public class threadClass extends DB {
//    Scanner sc=new Scanner(System.in);
static Date date=new Date();
    static User dummyUser=new User(-1,"abc","1234",345);
    static Scanner sc=new Scanner(System.in);
    //t1 is used for asking orderplace
    Thread t1=new Thread(){
        public void run() {
            try {
                askingOrder();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    };









    public void askingOrder() throws InterruptedException, IOException {
        int i=0;
        while(i<=limit2){



            JOptionPaneTest.takinginput();
            ++i;
            sleep(5000);
        }
    }
    Thread t2=new Thread(){
        public void run(){
            try {
                sleep(10000);
//                System.out.println("Thread t2 started");
                doctorAvailable();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };
    static Thread t5=new Thread(){
        public void run() {
            try {
                sleep(15000);
                askingPreference();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    };


    public static void askingPreference() throws InterruptedException, IOException {
        int i=1;
        while(i<=limit)
        {
            while(wtschedq.size()!=0) {
                User U=wtschedq.removeFirst();
                updateMessage(U.UserId,"asking user for slot prefernces or immediate call back");
                //giving option
                String N= JOptionPaneTest.takinginput(U);

                if(Objects.equals(N, "schedule my call"))
                {
                    //ek aur popup show krna h jisme available slots btane honge

                    updateMessage(U.UserId,"User will be displayed available slot for selection");

                    String M= JOptionPaneTest.askSchedule(U);
                    updateMessage(U.UserId,"User selected option"+M);
                    String list[]=M.split(" ");
                    int hr=0;
                    if(Objects.equals(list[0], "next_day")) {
                        hr = hr + 24;
                        if (Objects.equals(list[2], "pm"))
                            hr = hr + 12;
                        hr=hr+parseInt(list[1]);

                    }
                    else
                    {
                        if (Objects.equals(list[1], "pm"))
                            hr = hr + 12;
                        hr=hr+parseInt(list[0]);
                    }
                    addSlot(U,hr);



                }
                else if(Objects.equals(N, "callback request"))
                {
                    updateMessage(U.UserId,"User requested immediate call back ,call will be placed within 5 minutes");
                    rtoq1.addFirst(U);
                }
                if(Objects.equals(N, "ignore"))
                {
                    updateMessage(U.UserId,"User did not respond within alloted time.slot will be automatically scheduled.");
                    automaticallySchedule(U);
                }

            }
            sleep(5000);
            ++i;


        }

    }

    public static void automaticallySchedule(User U) throws IOException {
        Vector<Integer> A=showAvailableSlot();
        int hr= A.get(0);   //will change it later
        addSlot(U,hr);


    }


    public static Vector<Integer> showAvailableSlot(){
        date=new Date();
        int currenthr=date.getHours();
        Vector<Integer> v=new Vector<Integer>();
        for(int i=currenthr;i<22;i++)
        {
            if(countUserPerhour[i]<18)
            {


                if(i>12)
                {   int j=i-12;
                    System.out.println(j+"pm - "+(j+1)+" pm\n");
                }
                else if(i==12)
                {
                    System.out.println(i+"noon - "+(i+1)+" pm\n");
                }
                else if(i==11)
                {
                    System.out.println(i+"am - "+(i+1)+" noon\n");
                }
                else
                {
                    System.out.println(i+"am - "+(i+1)+" am\n");
                }
                v.add(i);
            }
        }
        for(int i=32;i<46;i++)
        {   System.out.println("next day ");

            if(countUserPerhour[i]<18)
            {

               i=i-24;
                if(i>12)
                {   int j=i-12;
                    System.out.println(j+"pm - "+(j+1)+" pm\n");
                }
                else if(i==12)
                {
                    System.out.println(i+"noon - "+(i+1-12)+" pm\n");
                }
                else if(i==11)
                {
                    System.out.println(i+"am - "+(i+1)+" noon\n");
                }
                else
                {
                    System.out.println(i+"am - "+(i+1)+" am\n");
                }
                i=i+24;

                v.add(i);
            }

        }

        return v;
    }
    public void doctorAvailable() throws InterruptedException {
        int j=0;
        while(j<=limit){
            for(int i=0;i<5;i++)
            {
                if(!main.arr[i].busy && !Objects.equals(main.arr[i].role, "buffer"))
                {
//                    System.out.println("doctor "+i+" is not busy");

                    //doctor ko busy krdu
                    //ek thread bna do->function useravailable()->doctocall().

                    main.arr[i].busy=true;

                    tryingdemo object = new tryingdemo(main.arr[i]);
                    object.start();

                   /*if(Objects.equals(main.arr[i].role, "immediate"))
                    {
//                        System.out.println("immediate wala doc");
                        User b= userAvailable("immediate");
                        if(b!=null)
                        {
//                            System.out.println(b.UserId + " is this");
                            main.arr[i].busy=true;
                            tryingdemo object = new tryingdemo(main.arr[i],b);
                            object.start();
                        }


                    }
                    else if(Objects.equals(main.arr[i].role, "hv"))
                    {
                        User b= userAvailable("hv");
                        if(b!=null){
                            main.arr[i].busy=true;
                            tryingdemo object = new tryingdemo(main.arr[i],b);
                            object.start();
                        }

                    }
                    else {
                        User b= userAvailable("slot");
                        if(b!=null)
                        {
                            main.arr[i].busy=true;
                            tryingdemo object = new tryingdemo(main.arr[i],b);
                            object.start();
                        }

                    }*/
                }

            }
            ++j;
//            System.out.println("Thread t2 is running");
//            sleep(20000);
            sleep(11000);
        }

    }
    public static User userAvailable(String role) {
        if (role == "hv") {
            if (DB.hvq.size() != 0) {
                User a = DB.hvq.removeFirst();
                return a;
            } else if (DB.rtoq1.size() != 0) {
                User a = DB.rtoq1.removeFirst();
                return a;
            } else if (DB.rtoq2.size() != 0) {
                User a = DB.rtoq2.removeFirst();
                return a;
            } else {
                int currentHr = date.getHours();
                for (int i = 0; i < 18; i++) {
                    if (DB.scharray[currentHr][i].UserId != -1) {
                        User a = DB.scharray[currentHr][i];
                        DB.scharray[currentHr][i] = dummyUser;
                        return a;
                    }
                }
            }
        } else if (Objects.equals(role, "immediate")) {
            if (DB.rtoq1.size() != 0) {
                return DB.rtoq1.removeFirst();
            } else if (DB.rtoq2.size() != 0) {
                return DB.rtoq2.removeFirst();
            } else if (DB.hvq.size() != 0) {
                return DB.hvq.removeFirst();
            } else {
                int currentHr = date.getHours();
                for (int i = 0; i < 18; i++) {
                    if (DB.scharray[currentHr][i].UserId != -1) {
                        User a = DB.scharray[currentHr][i];
                        DB.scharray[currentHr][i] = dummyUser;
                        return a;
                    }
                }
            }
        } else if (Objects.equals(role, "slot")) {
            int currentHr = date.getHours();
            for (int i = 0; i < 18; i++) {
                if (DB.scharray[currentHr][i].UserId != -1) {
                    User a = DB.scharray[currentHr][i];
                    DB.scharray[currentHr][i] = dummyUser;
                    return a;
                }
            }
            if (DB.rtoq1.size() != 0) {
                return DB.rtoq1.removeFirst();
            } else if (DB.rtoq2.size() != 0) {
                return DB.rtoq2.removeFirst();
            } else if (DB.hvq.size() != 0) {
                return DB.hvq.removeFirst();
            }


        }
        return null;
    }
    public static void DoctorCall(Doctor k, User U) throws InterruptedException, IOException {
        Date date=new Date();
//        k.busy=true;

        U.callattempt++;
        updateMessage(U.UserId," call attempt no: "+U.callattempt);
        String M=call(k,U);
//        System.out.println("write for "+k.id+" and "+U.UserId);



        switch (M)
        {
            case "unanswer":
                updatedoctorMessage(k.id,"patient with name: "+U.name+" and order id "+U.UserId+" did not pickup the call ");
                updateMessage(U.UserId,"call placed by doctor "+k.name+" was unaswered");
                date=new Date();
                long timeMilli = date.getTime();
                U.intime=timeMilli;
                U.missedcall++;
                updateMessage(U.UserId,"User has missed "+U.missedcall+" no. of calls for the order ");
                if(U.missedcall>=2) {
                    System.out.println("order is cancelled of user " + U.name + " having user id " + U.UserId + "\n");
                    updateMessage(U.UserId,"as user has missed more than 2 calls we are cancelling the order and communicating same to the user ");
                    return;
                }

                System.out.println("User "+U.UserId+" will be given option of link and callBack for 15 mins\n");
                updateMessage(U.UserId,"User will be given an option to respond within 15 minutes else call will be scheduled for the User automatically");
                wtschedq.addFirst(U);
                //yaha pe mujhe window popup krni h
                /*String N=JOptionPaneTest.takinginput(U);
                if(N=="schedule my call")
                {
                    //ek aur popup show krna h jisme available slots btane honge
                }
                else if(N=="call request")
                {

                }

                if(N=="ignore")
                {
                    wtschedq.addFirst(U);
                    U.inwtschedq=true;
                }*/

                break ;
            case "answerresolved":
                System.out.println("order further processing");
                updatedoctorMessage(k.id,"doctor has successfully processed order "+U.UserId);
                updateMessage(U.UserId,"order has been successfully processed by doctor "+k.name);
                break;
            case "answerunresolvedcallbusy":

                timeMilli = date.getTime();
                U.intime=timeMilli;

                U.inwtschedq=true;
                System.out.println("User "+U.UserId+"  will be given option of link and callBack for 15 mins\n");
                updatedoctorMessage(k.id,"patient with name: "+U.name+" and order id "+U.UserId+" informe doctor that he/she is busy ");
                updateMessage(U.UserId,"call placed by doctor "+k.name+" but the user was busy");
                updateMessage(U.UserId,"User will be given an option to respond within 15 minutes else call will be scheduled for the User automatically");
                wtschedq.addFirst(U);
//                popup will  be shown here,if he ignores then automatic
                //when we do automaticallschedule make sure that we schedule call after 1 hour

                break;
            case "answerunresolvedcalldrop":

                updatedoctorMessage(k.id,"patient with name: "+U.name+" and order id "+U.UserId+" faced connectivity issue ");
                updateMessage(U.UserId,"call placed by doctor "+k.name+" but the user faced connectivity issue");
                if(U.callattempt>=3)
                {
                    System.out.println("order is cancelled of user " + U.name + " having user id " + U.UserId + "\n");
                    updateMessage(U.UserId,"the order was not processed after 3 calls hence order has been cancelled. Communicating the same to the user ");
                    return;
                }
                updatedoctorMessage(k.id,"reattempting the call to "+U.name+" and order id "+U.UserId);
                updateMessage(U.UserId,"doctor "+k.name+" reattempting to connect to user ");
                DoctorCall(k,U);


                break;
            default:
                break;
        }



        k.noofcall++;
        if(k.noofcall>8)
        {

            k.time= date.getTime();
        }
//        k.busy=false;
        updatedoctorMessage(k.id,"marking doctor available for next call");
        System.out.println(k.id + " is free");
    }


    public static String call(Doctor k, User U) throws InterruptedException, IOException {

        FileWriter fileWritter = new FileWriter(k.id+".txt",true);
        updatedoctorMessage(k.id,"marking doctor busy");
        updatedoctorMessage(k.id,"doctor will be processing orderid: "+U.UserId);
        updateMessage(U.UserId,"orderid will be processed by doctor: "+k.name);




        System.out.println(date.getTime()+" "+k.id+" calls "+U.name+" having userid "+U.UserId+"\n");


        String M= JOptionPaneTest.takinginput(k.id,U.UserId);
        sleep(10000);

        Date date=new Date();
//        System.out.println(date);
        fileWritter.append(System.getProperty( "line.separator" )+k.name+" having doctor id " +k.id+" calls "+U.name+" having user id "+U.UserId+" at "+date+" and gives status "+M);
        fileWritter.close();
        System.out.println("status for "+k.id +" and "+U.UserId+" is "+M);
        //System.out.println(k.id+" calls "+U.name+" having userid "+U.UserId+"call completed\n");

        //return "Unanswer";
        //return "answer,unresolved,calldrop";
        //return "answer,unresolved,callbusy";

        return M;

    }


    Thread t3=new Thread(){
        public void run(){
            rtoq1expire();
            rtoq2expire();
            hvqexpire();
            try {
                wtschedqexpire();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
    public void hvqexpire() {
        System.out.println("running hvqexpire");
        if (hvq.size() == 0)
            return;
        else {
            User m = hvq.getLast();
            long diff = (date.getTime() - m.intime);
            long diffMinutes = (diff / (60 * 1000)) % 60;
            if (diffMinutes >= 5) {
                hvq.removeLast();
                rtoq1.addFirst(m);

                m.intime = date.getTime();
                hvqexpire();
            } else {
            }


        }
    }
    public void rtoq1expire() {
        System.out.println("running rtoq1expire");
        if (rtoq1.size() == 0) {
        }
        else {
            User m = rtoq1.getLast();
            long diff = (date.getTime() - m.intime);
            long diffMinutes = (diff / (60 * 1000)) % 60;
            if (diffMinutes >= 5) {
                rtoq1.removeLast();
                rtoq2.addFirst(m);
                m.inrtoq2 = true;
                m.intime = date.getTime();
                rtoq1expire();
            } else {
            }


        }
    }
    public void rtoq2expire() {
        System.out.println("running rtoq2expire");
        if (rtoq2.size() == 0) {
        }
        else {
            User m = rtoq2.getLast();
            if (!m.inrtoq2) {
                rtoq2.removeLast();
                rtoq2expire();
            } else {
                long diff = (date.getTime() - m.intime);
                long diffMinutes = (diff / (60 * 1000)) % 60;
                if (diffMinutes >= 25) {
                    rtoq2.removeLast();
                    System.out.println("User will be given option of link and callBack for 15 mins\n");
                    wtschedq.addFirst(m);
                    m.inwtschedq = true;
                    m.inrtoq2 = false;
                    m.intime = date.getTime();
                    rtoq2expire();
                }
            }


        }
    }
    public void wtschedqexpire() throws IOException {
        System.out.println("running wtschedexpire");
        if (wtschedq.size() == 0) {
        }
        else {
            User m = wtschedq.getLast();
            if (!m.inwtschedq) {
                wtschedq.removeLast();
                wtschedqexpire();
            } else {
                long diff = (date.getTime() - m.intime);
                long diffMinutes = (diff / (60 * 1000)) % 60;
                if (diffMinutes >= 15) {
                    wtschedq.removeLast();
                    System.out.println("we will automatically schedule the user\n");
                    int hr = firstslotavailable();
                    scheduleSlot(m, hr);
                    m.inwtschedq = false;


                    wtschedqexpire();

                }
            }
        }

    }
    public int firstslotavailable()
    {
        int currenthr=date.getHours();
        for(int i=currenthr;i<22;i++)
        {
            if(countUserPerhour[i]<18)
            {


                return i;
            }
        }
        for(int i=32;i<48;i++)
        {
            if(countUserPerhour[i]<18)
            {


                return i;
            }
        }
        return 32;
    }
    public void scheduleSlot(User U, int hr) throws IOException {
        addSlot(U,hr);
    }
    public static void addSlot(User U, int hr) throws IOException {
        countUserPerhour[hr]++;
        for(int i=0;i<18;i++)
        {
            if(scharray[hr][i].UserId==-1)
            {

                scharray[hr][i]=U;
                updateMessage(U.UserId,"slot "+hr+"successfully booked");
                return;

            }
        }
    }



    public static void deleteSlot(User U, int hr)
    {
        countUserPerhour[hr]--;
        for(int i=0;i<18;i++)
        {
            if(scharray[hr][i].UserId==U.UserId)
            {
                User dummyUser=new User(-1,"abc","123",9);
                scharray[hr][i]=dummyUser;

            }
        }
    }





    synchronized public static Doctor firstDoctor(String role)
    {
        for(int i=0;i<5;i++)
        {
            if(main.arr[i].busy==false&& main.arr[i].role==role){
                main.arr[i].busy=true;
                System.out.println(main.arr[i].id + " is busy");
                return main.arr[i];
            }


        }
        return null;     //will change it later
    }


}
