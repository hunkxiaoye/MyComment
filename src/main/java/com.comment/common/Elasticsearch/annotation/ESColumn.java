package com.comment.common.Elasticsearch.annotation;



import com.comment.common.Elasticsearch.enums.Analyzed;
import com.comment.common.Elasticsearch.enums.Indexed;
import com.comment.common.Elasticsearch.enums.Store;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Mtime on 2016/10/17.
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface ESColumn {


    Store store() default Store.STORE;

    Analyzed analyzed() default Analyzed.NOT_ANALYZED;

    Indexed indexed() default Indexed.INDEXED;
















}
