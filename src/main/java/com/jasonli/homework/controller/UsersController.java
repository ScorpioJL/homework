package com.jasonli.homework.controller;

import com.jasonli.homework.entity.ResponseResult;
import com.jasonli.homework.entity.Roles;
import com.jasonli.homework.entity.Users;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.jasonli.homework.HomeworkApplication.rolesList;
import static com.jasonli.homework.HomeworkApplication.usersList;

@RestController
public class UsersController {
    @PostMapping("api/v1/users")
    public ResponseResult createUser(@RequestBody Users user) throws NoSuchAlgorithmException {
        ResponseResult result = new ResponseResult();

        // Encrypt the userPassword using MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(user.getUserPassword().getBytes(StandardCharsets.UTF_8));
        String encryptStr = new BigInteger(1, bytes).toString(16);
        user.setUserPassword(encryptStr);

        Users targetUser = null;
        for (var u:usersList) {
            if(u.getUserName().equals(user.getUserName())) {
                targetUser = u;
                break;
            }
        }

        if(targetUser != null) {
            result.setStatus("Fail");
            result.setMsg("User " + user.getUserName() + " already existed");
        } else {
            usersList.add(user);
            result.setStatus("Success");
            result.setMsg("Create successfully");
        }

        return result;
    }

    @DeleteMapping("api/v1/users/{userName}")
    public ResponseResult deleteUser(@PathVariable String userName) {
        ResponseResult result = new ResponseResult();
        Users targetUser = null;
        int index = 0;

        for (var u:usersList) {
            if(u.getUserName().equals(userName)) {
                index = usersList.indexOf(u);
                targetUser = u;
                break;
            }
        }

        if(targetUser != null) {
            usersList.remove(index);
            result.setStatus("Success");
            result.setMsg("Delete successfully");
        } else {
            result.setStatus("Fail");
            result.setMsg("User " + userName + " not existed");
        }

        return result;
    }

    @PutMapping("api/v1/users/{userName}/{roleName}")
    public ResponseResult addRoleToUser(@PathVariable String userName,
                                        @PathVariable String roleName) {
        ResponseResult result = new ResponseResult();
        String returnStr = "";
        Users targetUser = null;
        ArrayList<Roles> tempRoleList = new ArrayList<Roles>();

        int index = 0;
        for (var u:usersList) {
            if(u.getUserName().equals(userName)) {
                index = usersList.indexOf(u);
                targetUser = u;
                break;
            }
        }

        if(targetUser != null) {
            var userRolesList = usersList.get(index).getUserRoles();
            ArrayList<String> roleNameList = new ArrayList<String>();
            for (var r:rolesList) {
                roleNameList.add(r.getRoleName());
            }

            if(userRolesList != null) {
                if(!userRolesList.contains(roleName)) {
                    if(roleNameList.contains(roleName)) {
                        userRolesList.add(new Roles(roleName));
                        usersList.get(index).setUserRoles(userRolesList);
                        result.setStatus("Success");
                        result.setMsg("Role:" + roleName + " add to user successfully");
                    } else {
                        result.setStatus("Fail");
                        result.setMsg(roleName + " not existed in RoleList");
                    }
                }
            } else {
                if(roleNameList.contains(roleName)) {
                    tempRoleList.add(new Roles(roleName));
                } else {
                    result.setStatus("Fail");
                    result.setMsg(roleName + " not existed in RoleList");
                }

                userRolesList = tempRoleList;
                usersList.get(index).setUserRoles(userRolesList);
                result.setStatus("Success");
                result.setMsg("Role:" + roleName + " add to user successfully");
            }
        } else {
            result.setStatus("Fail");
            result.setMsg("User " + userName + " not existed");
        }

        return result;
    }
}
