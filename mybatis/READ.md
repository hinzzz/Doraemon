select * from tbl_employee where id=${id} and last_name=#{lastName}
Preparing: select * from tbl_employee where id=2 and last_name=?
区别：
#{}:是以预编译的形式，将参数设置到sql语句中；PreparedStatement；防止sql注入
${}:取出的值直接拼装在sql语句中；会有安全问题；
大多情况下，我们去参数的值都应该去使用#{}；

		原生jdbc不支持占位符的地方我们就可以使用${}进行取值
		比如分表、排序。。。；按照年份分表拆分
			select * from ${year}_salary where xxx;
			select * from tbl_employee order by ${f_name} ${order}

#{}:更丰富的用法：
	规定参数的一些规则：
	javaType、 jdbcType、 mode（存储过程）、 numericScale、
	resultMap、 typeHandler、 jdbcTypeName、 expression（未来准备支持的功能）；

	jdbcType通常需要在某种特定的条件下被设置：
		在我们数据为null的时候，有些数据库可能不能识别mybatis对null的默认处理。比如Oracle（报错）；
		
		JdbcType OTHER：无效的类型；因为mybatis对所有的null都映射的是原生Jdbc的OTHER类型，oracle不能正确处理;
		
		由于全局配置中：jdbcTypeForNull=OTHER；oracle不支持；两种办法
		1、#{email,jdbcType=OTHER};
		2、jdbcTypeForNull=NULL
			<setting name="jdbcTypeForNull" value="NULL"/>


JavaType和ofType都是用来指定对象类型的，但是JavaType是用来指定pojo中属性的类型，而ofType指定的是 映射到list集合属性中pojo的类型 