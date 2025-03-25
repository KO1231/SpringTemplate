package jp.kazutech.springtemplate.db.mapper;

import jp.kazutech.springtemplate.db.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User getUserByName(@Param("name") String name);
}
