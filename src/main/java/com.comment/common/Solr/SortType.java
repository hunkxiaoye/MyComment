package com.comment.common.Solr;

public enum SortType {
    ASC("ASC"), DESC("DESC");


    private String value;

    private SortType(String value) {
        this.value = value;
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    public String value() {
        return this.value;
    }

}
