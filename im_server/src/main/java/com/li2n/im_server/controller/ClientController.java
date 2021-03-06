package com.li2n.im_server.controller;

import com.li2n.im_server.entity.User;
import com.li2n.im_server.service.IUserService;
import com.li2n.im_server.utils.RedisCache;
import com.li2n.im_server.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * 客户端控制器，有关客户端操作控制器
 * @author 一杯香梨
 * @version 1.0
 * @date 2022-2-19 下午 7:59
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Value("${im-redis-key.login.client}")
    private String clientLoginKey;

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisCache redisCache;

    @ApiOperation(value = "当前登录用户信息")
    @GetMapping("/login/info")
    public ResponseResult getUserInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        User user = redisCache.getCacheObject(clientLoginKey + username);
        user.setPassword(null);
        return ResponseResult.success(user);
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public ResponseResult regUser(@RequestBody User user, String code) {
        User data = userService.clientRegister(user, code);
        if (data != null) {
            return ResponseResult.success("注册成功", data);
        }
        return ResponseResult.error("注册失败", null);
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/update/password")
    public ResponseResult editPsw(@RequestBody PasswordVo model) {
        return userService.updatePassword(model);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/update/user")
    public ResponseResult updateUser(@RequestBody UserVo userVo) {
        Boolean updateResult = userService.updateUser(userVo, clientLoginKey);
        if (updateResult) {
            return ResponseResult.success("更新数据成功");
        } else {
            return ResponseResult.error("更新数据失败");
        }
    }

    @ApiOperation(value = "分页查询用户（不包含登录用户）")
    @GetMapping("/search/page")
    public PageResponseResult search(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "3") Integer size,
                                     Principal principal,
                                     QueryUserVo model) {
        return userService.selectUserListByPage(currentPage, size, principal, model, "client");
    }

    @Deprecated
    @ApiOperation(value = "普通查询用户")
    @GetMapping("/search/no-page")
    public List<User> search(Principal principal, QueryUserVo model) {
        return userService.selectUserList(principal, model);
    }

}
