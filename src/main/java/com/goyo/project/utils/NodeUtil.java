package com.goyo.project.utils;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.goyo.project.model.Organization;
/**
 * 获取子节点
 */
public class NodeUtil {
     
    private List<Organization> returnList = new ArrayList<Organization>();
     
    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public String getChildNodes(List<Organization> list, Integer typeId) {
        if(list == null && typeId == null) return "";
        for (Iterator<Organization> iterator = list.iterator(); iterator.hasNext();) {
        	Organization node = (Organization) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (typeId==node.getId()) {//父id条件
                recursionFn(list, node);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (node.getParentId()==0) {
                recursionFn(list, node);
            }*/
        }
        return returnList.toString();
    }
     
    private void recursionFn(List<Organization> list, Organization node) {
        List<Organization> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
        	returnList.add(node);
            Iterator<Organization> it = childList.iterator();
            while (it.hasNext()) {
//            	returnList.add(node);
            	Organization n = (Organization) it.next();
                recursionFn(list, n);
            }
        } else {
            returnList.add(node);
        }
    }
     
    // 得到子节点列表
    private List<Organization> getChildList(List<Organization> list, Organization node) {
        List<Organization> nodeList = new ArrayList<Organization>();
        Iterator<Organization> it = list.iterator();
        while (it.hasNext()) {
        	Organization n = (Organization) it.next();
            if (n.getParentId() == node.getId()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
 
    // 判断是否有子节点
    private boolean hasChild(List<Organization> list, Organization node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }
}