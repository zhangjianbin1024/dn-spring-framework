package com.dn.spring.mybatis.interceptor;

public class Page {
    
    public Integer beginPage;
    
    public Integer pageSize;
    
    public Integer totals;
    
    /** 
     * @Fields needPage 是否需要分页 
     */
    
    public boolean needPage;
    
    public boolean isNeedPage() {
        return needPage;
    }
    
    public void setNeedPage(boolean needPage) {
        this.needPage = needPage;
    }
    
    public Integer getBeginPage() {
        return beginPage;
    }
    
    public void setBeginPage(Integer beginPage) {
        this.beginPage = beginPage;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Integer getTotals() {
        return totals;
    }
    
    public void setTotals(Integer totals) {
        this.totals = totals;
    }
}
