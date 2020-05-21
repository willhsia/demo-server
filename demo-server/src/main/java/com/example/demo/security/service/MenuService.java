package com.example.demo.security.service;

import com.example.demo.security.bean.Menu;
import com.example.demo.security.mapper.MenuMapper;
import com.example.demo.security.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
//@Transactional
//@CacheConfig(cacheNames = "menus_cache")
public class MenuService {
//    @Autowired
    @Resource
    MenuMapper menuMapper;

//    @Cacheable(key = "#root.methodName")
    public List<Menu> getAllMenu(){
        return menuMapper.getAllMenu();
    }

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(HrUtils.getCurrentHr().getId());
    }

    public List<Menu> menuTree() {
        return menuMapper.menuTree();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }
}
