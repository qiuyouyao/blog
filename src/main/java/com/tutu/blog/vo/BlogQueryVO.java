package com.tutu.blog.vo;

/**
 * @author tutu 2020/5/1 21:04
 */
public class BlogQueryVO {
    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQueryVO() {
    }

    public BlogQueryVO(Long typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "BlogQueryVO{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                '}';
    }
}
