package com.jasonli.homework;

import com.jasonli.homework.entity.Auth;
import com.jasonli.homework.entity.Roles;
import com.jasonli.homework.entity.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.time.*;
import java.util.Random;

@SpringBootApplication
public class HomeworkApplication {
    public static ArrayList<Users> usersList = new ArrayList<Users>();
    public static ArrayList<Roles> rolesList = new ArrayList<Roles>();

    public static ArrayList<Auth> authList = new ArrayList<Auth>();
    public static void main(String[] args) {
        initData();
        SpringApplication.run(HomeworkApplication.class, args);
    }

    public static void initData() {
        // Init userList
        System.out.println("==> UserList <==");
        Users user = new Users();
        user.setUserName("Jason");
        user.setUserPassword("15918791298");
        ArrayList<Roles> tempRoleList = new ArrayList<Roles>();
        Roles tempRole = new Roles();
        tempRole.setRoleName("Admin");
        tempRoleList.add(tempRole);
        Roles tempRole1 = new Roles();
        tempRole1.setRoleName("Tester");
        tempRoleList.add(tempRole1);
        user.setUserRoles(tempRoleList);
        usersList.add(user);
        for (var u:usersList) {
            System.out.println("====================");
            System.out.println(u.getUserName());
            System.out.println(u.getUserPassword());
            for (var tr:u.getUserRoles()) {
                System.out.println("==>");
                System.out.println(tr.getRoleName());
                System.out.println("==>");
            }
            System.out.println("====================");
        }

        System.out.println("                    ");

        // Init roleList
        System.out.println("==> RoleList <==");
        Roles role = new Roles();
        role.setRoleName("Admin");
        rolesList.add(role);
        Roles role1 = new Roles();
        role1.setRoleName("Tester");
        rolesList.add(role1);
        for (var r:rolesList) {
            System.out.println("====================");
            System.out.println(r.getRoleName());
            System.out.println("====================");
        }

        System.out.println("                    ");

        // Init authList
        System.out.println("==> AuthList <==");
        Auth auth = new Auth();
        auth.setUserName("Jason");
        auth.setUserPassword("c4895c1e0b1e8ca75aa7824f0f0962ca");
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String authKey = "";
        int length = 32;
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        authKey = sb.toString();
        auth.setAuthKey(authKey);
        auth.setCreatedDateTime(LocalDateTime.now());
        auth.setExpiredDateTime(auth.getCreatedDateTime().plusHours(2));
        authList.add(auth);
        for (var a:authList) {
            System.out.println("====================");
            System.out.println(a.getUserName());
            System.out.println(a.getUserPassword());
            System.out.println(a.getAuthKey());
            System.out.println(a.getCreatedDateTime().toLocalTime());
            System.out.println(a.getExpiredDateTime().toLocalTime());
            System.out.println("====================");
        }
    }
}
