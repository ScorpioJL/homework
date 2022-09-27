package com.jasonli.homework.controller;

import com.jasonli.homework.entity.ResponseResult;
import com.jasonli.homework.entity.Roles;
import org.springframework.web.bind.annotation.*;

import static com.jasonli.homework.HomeworkApplication.rolesList;

@RestController
public class RolesController {
    @PostMapping("api/v1/roles")
    public ResponseResult createRole(@RequestBody Roles role) {
        ResponseResult result = new ResponseResult();
        Roles targetRole = null;

        for (var r:rolesList) {
            if(r.getRoleName().equals(role.getRoleName())) {
                targetRole = r;
                break;
            }
        }

        if(targetRole != null) {
            result.setStatus("Fail");
            result.setMsg("Role " + role.getRoleName() + " already existed");
        } else {
            rolesList.add(role);
            result.setStatus("Success");
            result.setMsg("Create successfully");
        }

        return result;
    }

    @DeleteMapping("api/v1/roles/{roleName}")
    public ResponseResult deleteRole(@PathVariable String roleName) {
        ResponseResult result = new ResponseResult();
        Roles targetRole = null;
        int index = 0;

        for (var r:rolesList) {
            if(r.getRoleName().equals(roleName)) {
                targetRole = r;
                index = rolesList.indexOf(r);
                break;
            }
        }

        if(targetRole != null) {
            rolesList.remove(index);
            result.setStatus("Success");
            result.setMsg("Delete successfully");
        } else {
            result.setStatus("Fail");
            result.setMsg("Role " + roleName + " not existed");
        }

        return result;
    }
}
