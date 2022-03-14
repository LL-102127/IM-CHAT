package com.li2n.im_server.controller;


import com.li2n.im_server.pojo.model.RespBeanModel;
import com.li2n.im_server.service.IUserInfoService;
import com.li2n.im_server.utils.QiniuUpload;
import com.qiniu.common.Zone;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 公共接口 前端控制器
 * </p>
 *
 * @author Li2N
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private IUserInfoService userService;

    @ApiOperation(value = "检查用户名是否已经存在")
    @GetMapping("/check/username/{username}")
    public Boolean check(@PathVariable String username) {
        return userService.selectOne(username) != null;
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload/file")
    public RespBeanModel uploadQiniu(MultipartFile file, @RequestParam("type") String type) {
        String folder = "im_" + type + "/";
        String saveFile = QiniuUpload.updateFile(file, Zone.zone2(), QiniuUpload.getUploadCredential(), file.getOriginalFilename(), folder);
        String url = QiniuUpload.publicFile(saveFile);
        return RespBeanModel.success("上传成功", url);
    }

}
