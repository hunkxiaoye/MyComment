package com.comment;

import com.comment.common.Elasticsearch.meta.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import java.time.LocalDateTime;
@Setter
@Getter
public class modelComment extends BaseModel<Integer> {
    @Field
    private int userid;
    @Field
    private String commentinfo;
    //    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    @Field
    private LocalDateTime createtime;
    @Field
    private int newsid;

}
