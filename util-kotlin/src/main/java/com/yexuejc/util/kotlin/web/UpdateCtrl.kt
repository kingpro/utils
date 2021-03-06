package com.yexuejc.util.kotlin.web

import com.yexuejc.util.base.constant.RespsConstant
import com.yexuejc.util.base.http.Resps
import com.yexuejc.util.base.util.FileUtil
import com.yexuejc.util.base.util.StrUtil
import com.yexuejc.util.kotlin.service.IFileSrv
import com.yexuejc.util.kotlin.utils.isNotEmpty
import com.yexuejc.util.kotlin.web.vo.UploadFileModel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.servlet.ModelAndView
import java.io.File
import javax.servlet.http.HttpServletRequest


/**
 * @PackageName: com.yexuejc.util.kotlin.web
 * @Description:
 * @author: maxf
 * @date: 2018/1/8 18:44
 */
@RestController
@RequestMapping("/file")
class UpdateCtrl {
    internal var logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    var fileSrv: IFileSrv? = null

    private val DATA_N = "N"
    private val DATA_Y = "Y"


    @Value(value = "\${upload.root.path}")
    val rootPath: String? = null


    @RequestMapping("/upload/goto")
    fun goto(): ModelAndView {
        var mv = ModelAndView("/upload");
        return mv;
    }

    /**
     * 同步
     */
    @RequestMapping(value = "/upload", produces = ["multipart/form-data;charset=UTF-8"])
    fun upload(@RequestParam("file") file: MultipartFile, model: UploadFileModel,
               request: HttpServletRequest): ModelAndView {
        var mv = ModelAndView("succ")
        var resps = checkUploadFileModel(model)
        if (resps.code != RespsConstant.CODE_SUCCESS) {
            mv.addObject("code", resps.code)
            mv.addObject("msg", resps.msg)
            return mv
        }
        var filePath: String? = ""
        if (file.isNotEmpty()) {
            FileUtil.judeDirExists(File(rootPath))
            filePath = rootPath + "/v_" + model.version + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename()
            // 转存文件
            logger.info("文件保存路径：{}", filePath)
            file.transferTo(File(filePath))
        } else {
            mv.addObject("code", RespsConstant.CODE_FAIL)
            mv.addObject("msg", "文件为空")
            return mv
        }
        model.fileUrl = filePath
        model.fileSize = file.size
        logger.info("文件信息：{}", model.toString())
        fileSrv?.save(model)?.let {
            if (it > 0) {
                mv.addObject("code", RespsConstant.CODE_SUCCESS)
                mv.addObject("msg", "上传成功")
                mv.addObject("data", filePath)
            } else {
                mv.addObject("code", RespsConstant.CODE_FAIL)
                mv.addObject("msg", "上传成功，数据创建失败。文件路径：" + filePath)
            }
        }
        return mv
    }

    /**
     * 异步上传
     */
    @RequestMapping(value = ["/upload/json"])
    fun uploadAjax(model: UploadFileModel, request: HttpServletRequest): Any {
        var checkResps = checkUploadFileModel(model)
        if (checkResps.code != RespsConstant.CODE_SUCCESS) {
            return checkResps
        }
        // 判断文件是否为空
        val multipartRequest = request as MultipartHttpServletRequest
        // 获取上传文件名
        val file = multipartRequest.getFile("file")

        var filePath: String?
        if (file != null && !file.isEmpty) {
            FileUtil.judeDirExists(File(rootPath))
            filePath = rootPath + "/v_" + model.version + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename()
            // 转存文件
            logger.info("文件保存路径：{}", filePath)
            file.transferTo(File(filePath))
        } else {
            return Resps.fail("文件为空")
        }
        model.fileUrl = filePath
        model.fileSize = file.size
        logger.info("文件信息：{}", model.toString())
        fileSrv?.save(model)?.let {
            if (it > 0) {
                return Resps<String>().setSucc(filePath, "上传成功")
            } else {
                return Resps<String>().setSucc(filePath, "上传成功，数据创建失败")
            }
        }
        return Resps.error(RespsConstant.MSG_ERROT_HTTP)
    }

    /**
     * checkFileModel
     */
    private fun checkUploadFileModel(model: UploadFileModel): Resps<Any> {
        model.id = StrUtil.genUUID()
        if (StrUtil.isEmpty(model.version)) {
            return Resps.fail("版本号为空")

        }
        if (StrUtil.isEmpty(model.code)) {
            return Resps.fail("支持最低版本号为空")
        }
        if (DATA_N.equals(model.ismust) && StrUtil.isEmpty(model.minVersion)) {
            return Resps.fail("支持最低版本号为空")
        }
        return Resps.success("保存成功")
    }


}
