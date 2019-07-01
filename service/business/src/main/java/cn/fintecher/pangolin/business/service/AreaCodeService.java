package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.entity.AreaCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 10:06 2017/7/28
 */
@Service("areaCodeService")
public class AreaCodeService {

    @Autowired
    AreaCodeQueryService areaCodeQueryService;


    /**
     * 查询所有的区域信息
     *
     * @return
     */
    public Iterable<AreaCode> queryAllAreaCode() {
        return areaCodeQueryService.queryAllAreaCode();
    }

    @Cacheable(value = "areaCodeSecond", key = "'pangolin:areaCodeSecond'", unless = "#result==null")
    public List<AreaCode> getAllAreaCode(){
        List<AreaCode> list = new ArrayList<>();
        Iterator<AreaCode> iterator = queryAllAreaCode().iterator();
        while (iterator.hasNext()){
            AreaCode areaCode = iterator.next();
            if( areaCode.getTreePath().split("/").length == 2){
                list.add(areaCode);
            }
        }
        return list;
    }
    /**
     * 通过名字查找区域信息
     *
     * @param areaName
     * @return
     */
    @Cacheable(value = "areaCode", key = "'pangolin:areaCode:'+#areaName", unless = "#result==null")
    public AreaCode queryAreaCodeByName(String areaName) {
        if (Objects.isNull(areaName)) {
            return null;
        }
        Iterable<AreaCode> areaCodeIterable = queryAllAreaCode();
        //特殊处理 祁吉贵
        for (Iterator<AreaCode> it = areaCodeIterable.iterator(); it.hasNext(); ) {
            AreaCode areaCode = it.next();
            if ((areaName.contains(areaCode.getAreaName()) || areaCode.getAreaName().contains(areaName)) && areaCode.getTreePath().split("/").length == 2) {
                return areaCode;
            }
        }
        return null;
    }

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    public AreaCode queryAreaCodeById(Integer id) {
        return areaCodeQueryService.queryAreaCodeById(id);
    }
}
