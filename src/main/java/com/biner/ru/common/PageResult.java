package com.biner.ru.common;

import java.io.Serializable;
import java.util.List;


/**
 * 封装分页请求结果
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**当前页*/
    private int pageNum;
    /**每页的数量*/
    private int pageSize;
    
    
    /**当前页的数量*/
    private int size;
    /**当前页面第一个元素在数据库中的行号*/
    private int startRow;
    /**总记录数*/
    private long total;
    /**总页数*/
    private int pages;
    /**结果集*/
    private List<T> rows;
   
    public PageResult() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageResult{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", startRow=").append(startRow);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", rows=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}
