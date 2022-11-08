package com.fwkily.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fwkily.mapper.po.UserPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<UserPo> {


    int insert(UserPo entity);

    int deleteById(Serializable id);

    int deleteById(UserPo entity);

    int deleteByMap(@Param("cm") Map<String, Object> columnMap);

    int delete(@Param("ew") Wrapper<UserPo> queryWrapper);

    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    int updateById(@Param("et") UserPo entity);

    int update(@Param("et") UserPo entity, @Param("ew") Wrapper<UserPo> updateWrapper);

    UserPo selectById(Serializable id);

    List<UserPo> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    List<UserPo> selectByMap(@Param("cm") Map<String, Object> columnMap);

    <P extends IPage<UserPo>> P selectPage(P page, @Param("ew") Wrapper<UserPo> queryWrapper);


    @Select("<script>" +
            " select t.* from user t " +
            " where t.name in" +
            " <foreach collection='names' item='name' open='(' separator=',' close=')'>" +
            " #{name}" +
            " </foreach>" +
            " </script>")
    List<UserPo> selectByNames(@Param("names") List<String> names);

}
