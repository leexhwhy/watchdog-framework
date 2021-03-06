package cn.licoy.wdog.core.controller.system;

import cn.licoy.wdog.common.bean.RequestResult;
import cn.licoy.wdog.common.bean.StatusEnum;
import cn.licoy.wdog.common.annotation.SysLogs;
import cn.licoy.wdog.core.dto.system.user.FindUserDTO;
import cn.licoy.wdog.core.dto.system.user.ResetPasswordDTO;
import cn.licoy.wdog.core.dto.system.user.UserAddDTO;
import cn.licoy.wdog.core.dto.system.user.UserUpdateDTO;
import cn.licoy.wdog.core.service.system.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Licoy
 * @version 2018/4/18/14:15
 */
@RestController
@RequestMapping(value = {"/system/user"})
@Api(tags = {"用户管理"})
public class UserController {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    @ApiOperation(value = "分页获取用户数据")
    @SysLogs("分页获取用户数据")
    //@Cacheable(value = "system:user:get",keyGenerator = "keyGenerator")
    public RequestResult get(@RequestBody @Validated @ApiParam(value = "用户获取过滤条件") FindUserDTO findUserDTO){
        return userService.getAllUserBySplitPage(findUserDTO);
    }

    @RequestMapping(value = {"/get/id/{id}"}, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID获取用户信息")
    @SysLogs("根据ID获取用户信息")
    public RequestResult getById(@PathVariable("id") @ApiParam(value = "用户ID") String id){
        return RequestResult.e(StatusEnum.OK,userService.findUserById(id,true));
    }

    @RequestMapping(value = {"/lock/{id}"}, method = RequestMethod.POST)
    @ApiOperation(value = "锁定用户")
    @SysLogs("锁定用户")
    public RequestResult lock(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        return userService.statusChange(id, 0);
    }

    @RequestMapping(value = {"/unlock/{id}"}, method = RequestMethod.POST)
    @ApiOperation(value = "解锁用户")
    @SysLogs("解锁用户")
    public RequestResult unlock(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        return userService.statusChange(id, 1);
    }

    @RequestMapping(value = {"/remove/{id}"}, method = RequestMethod.POST)
    @ApiOperation(value = "删除用户")
    @SysLogs("删除用户")
    public RequestResult remove(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        return userService.removeUser(id);
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    @SysLogs("添加用户")
    public RequestResult add(@RequestBody @Validated @ApiParam(value = "用户数据") UserAddDTO addDTO){
        userService.add(addDTO);
        return RequestResult.e(StatusEnum.OK);
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.POST)
    @ApiOperation(value = "更新用户")
    @SysLogs("更新用户")
    public RequestResult update(@PathVariable("id") @ApiParam(value = "用户标识ID") String id,
                             @RequestBody @Validated @ApiParam(value = "用户数据") UserUpdateDTO updateDTO){
        userService.update(id,updateDTO);
        return RequestResult.e(StatusEnum.OK);
    }

    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    @SysLogs("重置密码")
    public RequestResult resetPassword(@RequestBody
                                           @Validated @ApiParam(value = "用户及密码数据") ResetPasswordDTO dto){
        userService.resetPassword(dto);
        return RequestResult.e(StatusEnum.OK);
    }

}
