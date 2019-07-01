package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.MetaProfession;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author chenmin
 * @since 2018/11/20
 */
@Data
public class QueryUserProfessionResponse implements Serializable {
    private static final long serialVersionUID = -4283470191027882073L;

    private Map<MetaProfession, List<MetaProfession>> professionMap;
}
