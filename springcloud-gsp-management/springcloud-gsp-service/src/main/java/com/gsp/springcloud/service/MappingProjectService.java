package com.gsp.springcloud.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsp.springcloud.base.BaseService;
import com.gsp.springcloud.mapper.MappingProjectMapper;
import com.gsp.springcloud.mapper.ResourceMapper;
import com.gsp.springcloud.model.MappingProject;
import com.gsp.springcloud.model.Resource;
import com.gsp.springcloud.properties.FtpProperties;
import com.gsp.springcloud.utils.DateUtils;
import com.gsp.springcloud.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.gsp.springcloud.status.AddStatus.*;
import static com.gsp.springcloud.status.SelectStatus.*;
import static com.gsp.springcloud.status.UpdateStatus.*;

/**
 * @author XRF
 * @date 2020/7/14 20:46
 * 测绘项目的业务层,继承通用service便于编码
 */
@Service
public class MappingProjectService extends BaseService<MappingProject> {
    @Autowired
    private MappingProjectMapper mappingProjectMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private FtpProperties ftpProperties;

    /**
     * @author XRF
     * @date 2020/7/15 18:54
     * 分页查询所有测绘项目信息
     * @description
     */
    public PageInfo selectAlls(HashMap hashMap) {
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo") + ""), Integer.parseInt(hashMap.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(mappingProjectMapper.selectAlls());
        if (null != pageInfo && !"".equals(pageInfo)) {
            return pageInfo;
        } else {
            return null;
        }
    }

    /**
     * @author XRF
     * @date 2020/7/16 16:21
     * 点击项目审核查询项目信息
     * @description
     */
    public PageInfo auditSelectAllsMp(HashMap hashMap) {
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo") + ""), Integer.parseInt(hashMap.get("pageSize") + ""));
        PageInfo<MappingProject> pageInfo = new PageInfo(mappingProjectMapper.auditSelectAllsMp());
        if (null != pageInfo && !"".equals(pageInfo)) {
            return pageInfo;
        } else {
            return null;
        }
    }

    /**
     * @author XRF
     * @date 2020/7/16 16:45
     * 点击成果汇交查看成果汇交已经处于一个提交状态的项目信息---分页
     * @description
     */
    public PageInfo resSelectAllsMp(HashMap hashMap) {
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo") + ""), Integer.parseInt(hashMap.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(mappingProjectMapper.resSelectAllsMp());
        if (null != pageInfo && !"".equals(pageInfo)) {
            return pageInfo;
        } else {
            return null;
        }
    }

    /**
     * 根据项目名称模糊查询---》分页
     *
     * @param hashMap
     * @return
     */
    public PageInfo projectListSelect(HashMap hashMap) {
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo") + ""), Integer.parseInt(hashMap.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(mappingProjectMapper.projectListSelect(hashMap));
        if (null != pageInfo && !"".equals(pageInfo)) {
            return pageInfo;
        } else {
            return null;
        }
    }

    /**
     * @author XRF
     * @date 2020/7/15 19:20
     * 根据测绘项目的编号查询该项目下的所有审核记录信息--->分页
     * @description
     */
    public PageInfo selectAuditByMpId(HashMap hashMap) {
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo") + ""), Integer.parseInt(hashMap.get("pageSize") + ""));
        PageInfo pageInfo = new PageInfo(mappingProjectMapper.selectAuditByMpId(hashMap));
        if (null != pageInfo && !"".equals(pageInfo)) {
            return pageInfo;
        } else {
            return null;
        }
    }

    /**
     * 通过项目id查询项目详细信息（项目信息操作中点击查看使用）不分页
     *
     * @param id
     * @return
     */
    public HashMap<String, Object> selectProjectById(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (null != id && !("").equals(id)) {
            //当传进来的id不为空时，进行查询
            List<MappingProject> restdata = mappingProjectMapper.selectProjectById(id);
            //当查出来的有结果时
            if (restdata.size() > 0) {
                resultMap.put("code", SELECT_DATA_BY_ID_SUCCESS.getCode());
                resultMap.put("msg", SELECT_DATA_BY_ID_SUCCESS.getMsg());
                resultMap.put("data", restdata);
                return resultMap;
            }
        }
        //否则的话就是查询失败
        resultMap.put("code", SELECT_DATA_BY_ID_FAILED.getCode());
        resultMap.put("msg", SELECT_DATA_BY_ID_FAILED.getMsg());
        return resultMap;
    }

    /**
     * @author XRF
     * @date 2020/7/16 17:12
     * 根据项目编号查看项目的详细信息，以及该项目下的附件
     * 并且查看该项目的汇交结果信息以及该汇交成果下的附件
     * @description
     */
    public HashMap<String, Object> selectAllMpAndResult(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (null != id && !("").equals(id)) {
            //当传进来的id不为空时，进行查询
            List<HashMap> restdata = mappingProjectMapper.selectAllMpAndResult(id);
            //当查出来的有结果时
            if (restdata.size() > 0) {
                resultMap.put("code", SELECT_DATA_BY_ID_SUCCESS.getCode());
                resultMap.put("msg", SELECT_DATA_BY_ID_SUCCESS.getMsg());
                resultMap.put("data", restdata);
                return resultMap;
            }
        }
        //否则的话就是查询失败
        resultMap.put("code", SELECT_DATA_BY_ID_FAILED.getCode());
        resultMap.put("msg", SELECT_DATA_BY_ID_FAILED.getMsg());
        return resultMap;
    }

    /**
     * @author XRF
     * @date 2020/7/17 18:59
     * 更新测绘项目的信息
     * @description
     */
    public Map<String, Object> updateMProject(Map map) {
        HashMap<String, Object> resultMap = new HashMap<>();
        MappingProject mappingProject = new MappingProject();
        if (null != map.get("id")) {
            System.out.println(map.get("id"));
            mappingProject.setId(Long.parseLong(map.get("id") + ""));
        }
        if ("audit_status".equals(decKey(map)) && null != map.get("audit_status")) {
            mappingProject.setAuditStatus((Integer) map.get("audit_status"));
        } else if ("results_status".equals(decKey(map)) && null != map.get("results_status")) {
            mappingProject.setAuditStatus((Integer) map.get("results_status"));
        }
        //根据测绘项目的id修改测绘项目的数据，穿进去audit_status
        //todo 执行更新测绘项目表抛出异常
        Integer updateResult = super.update(mappingProject);
        //根据查到的返回值进行判断，大于0,有数据，返回数据，小于0 没有数据返回错误信息
        if (updateResult > 0) {
            resultMap.put("code", UPDATE_DATA_SUCCESS.getCode());
            resultMap.put("msg", UPDATE_DATA_SUCCESS.getMsg());
            resultMap.put("data", updateResult);
        } else {
            resultMap.put("code", UPDATE_DATA_FAILED);
        }
        return resultMap;
    }

    /**
     * @author XRF
     * 新增测绘项目
     * @date 2020/7/18 11:31
     * @description
     */
    public Map<String, Object> addMProject(MappingProject mappingProject, MultipartFile[] files, UploadService uploadService) {
        HashMap<String, Object> resultMap = new HashMap<>();
        //todo 后续完成项目的添加操作
        //生成一个项目id，设置进去
        long id = Long.parseLong(FileNameUtils.getFileName());
        mappingProject.setId(id);
        //设置创建设置时间
        mappingProject.setCreateTime(DateUtils.formatDate(new Date()));
        //执行添加操作
        int insert = mappingProjectMapper.insert(mappingProject);
        if (insert > 0) {//添加成功
            Boolean result = false;
            for (MultipartFile file : files) {
                //添加资源表
                Resource resource = new Resource();
                //设置资源ID
                String resourceId = FileNameUtils.getFileName();
                //获取今天日期格式化后的数据，用来当做路径
                String filePath = com.gsp.springcloud.utils.DateUtils.formatDate(new Date(), "yyyy/MM/dd");
                //获取原始文件的名称
                String oldFilename = file.getOriginalFilename();
                System.out.println(oldFilename);
                //截取文件后缀
                String extName = oldFilename.substring(oldFilename.lastIndexOf("."));
                //生成新的文件名称
                String newFileName = resourceId + "" + extName;
                //设置resource对象的值
                resource.setName(file.getOriginalFilename()).setSize(Long.valueOf(file.getSize())).setPath(ftpProperties.getHttpPath() + "/" + filePath + "/" + newFileName)
                        .setType(file.getContentType()).setExtName(extName).setRefBizType("身份证").setRefBizId(Long.valueOf(id))
                        .setCreateTime(DateUtils.formatDate(new Date())).setId(Long.valueOf(resourceId));
                //数据库添加resource的数据
                int r = resourceMapper.insert(resource);
                if (r > 0) {
                    //添加成功后上传文件
                    result = uploadService.upload(file, filePath, newFileName);
                }
            }
            if (result) {
                resultMap.put("code", ADD_DATA_SUCCESS.getCode());
                resultMap.put("msg", ADD_DATA_SUCCESS.getMsg());
                return resultMap;
            }
        }
        //添加失败
        resultMap.put("code",ADD_DATA_FAILED.getCode());
        resultMap.put("msg",ADD_DATA_FAILED.getMsg());
        return resultMap;
    }


    /**
     * @author XRF
     * @date 2020/7/18 10:53
     * 判断map中key是否和传进来的参数相同
     * @description
     */
    public static String decKey(Map map) {
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        int i = 1;
        while (i <= 1 && iterator.hasNext()) {
            i++;
            String next = iterator.next();
            if ("audit_status".equals(next)) {
                return next;
            } else if ("results_status".equals(next)) {
                return next;
            }
        }
        return null;
    }

}
