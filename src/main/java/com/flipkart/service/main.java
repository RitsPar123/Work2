package com.flipkart.service;


import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class main extends DB {
    public static Doctor[] arr;
    static Scanner sc = new Scanner(System.in);












    public static void main(String args[]) throws InterruptedException, IOException {
        for(int i=0;i<48;i++)
        {
            for(int j=0;j<20;j++)
            {
                scharray[i][j]=dummyUser;
            }
        }

        arr = new Doctor[5];
        System.out.println("hii friends");

        arr[0] = new Doctor(1, "doctor1_immediate", "immediate");
        DB.doctorDetails.put(arr[0].id,"");
        updatedoctorMessage(arr[0].id,"doctor "+arr[0].name+ " marked available in the system");
        arr[1] = new Doctor(2, "doctor2_immediate", "immediate");
        DB.doctorDetails.put(arr[1].id,"");
        updatedoctorMessage(arr[1].id,"doctor "+arr[1].name+ " marked available in the system");
        arr[2] = new Doctor(3, "doctor3_hv", "hv");
        DB.doctorDetails.put(arr[2].id,"");
        updatedoctorMessage(arr[2].id,"doctor "+arr[2].name+ " marked available in the system");
        arr[3] = new Doctor(4, "doctor4_slot", "slot");
        DB.doctorDetails.put(arr[3].id,"");
        updatedoctorMessage(arr[3].id,"doctor "+arr[3].name+ " marked available in the system");
        arr[4] = new Doctor(5, "doctor5_buffer", "buffer");
        DB.doctorDetails.put(arr[4].id,"");
        updatedoctorMessage(arr[4].id,"doctor "+arr[4].name+ " marked available in the system");



        Date date=new Date();
        System.out.println(date);



        for(int i=0;i<=2;i++)
        {
            User U=new User(i,"a"+i,"123"+i,i/5*1000+45);


            System.out.println("your order is processing"+U.UserId);
            date=new Date();
            int hr= date.getHours();
            if(i==10)
            {
                DB.orderDetails.put(U.UserId,"");
                DB.updateMessage(U.UserId," order_id "+U.UserId+" has been placed ");
                threadClass.addSlot(U,hr);

            }
            else {
                if(hr<8||hr>=22)      //will change it later to hr>22||hr<8
                {
                    // System.out.println("User will be given option of link for 15 mins\n");
                    U.inwtschedq=true;
                    DB.wtschedq.addFirst(U);
                }
                else {
                    if (U.price > 1000) {
                        DB.orderDetails.put(U.UserId,"");
                        DB.updateMessage(U.UserId," order_id "+U.UserId+" has been placed ");
                        DB.hvq.addFirst(U);

                    } else {
                        DB.orderDetails.put(U.UserId,"");
                        DB.updateMessage(U.UserId," order_id "+U.UserId+" has been placed ");
                        DB.rtoq1.addFirst(U);
                    }
                }
            }
        }




        threadClass obj=new threadClass();
//        obj.t1.start();
        obj.t2.start();
//        obj.t3.start();
//        obj.t5.start();
//        obj.t1.join();
        obj.t2.join();
//        obj.t3.join();
//        obj.t5.join();










//        date=new Date();
//        System.out.println(date.getHours());

       /* FileWriter fileWritter = new FileWriter("doctordetatils.txt",true);
        Iterator hmIterator = DB.doctorDetails.entrySet().iterator();
        while (hmIterator.hasNext()) {

            Map.Entry mapElement
                    = (Map.Entry)hmIterator.next();
            //fileWritter.append((String)mapElement.getKey()+"\n");
            fileWritter.append((String)mapElement.getValue()+"\n");

        }
        fileWritter.close();


        FileWriter fileWritter1 = new FileWriter("orderdetetails.txt",true);
        Iterator hmIterator1 = DB.orderDetails.entrySet().iterator();



        while (hmIterator1.hasNext()) {

            Map.Entry mapElement
                    = (Map.Entry)hmIterator1.next();
            //fileWritter1.append((String)mapElement.getKey()+"\n");
            fileWritter1.append((String)mapElement.getValue()+"\n");
        }
        fileWritter1.close();*/
        System.out.println(DB.doctorDetails.get(1));


















//        obj.t2.start();


//


    }

}
