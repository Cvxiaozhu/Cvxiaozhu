package com.zhu.blog.service;


import com.zhu.blog.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author GH
 */

public interface UploadService {

    /**
     * 图片上传
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
