package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sunyard.controller.BaseController;
import com.sunyard.dao.TbUserMapper;
import com.sunyard.entity.TbUser;
import com.sunyard.entity.TbUserExample;
import com.sunyard.model.TbUserhb;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��31�� ����9:29:02 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-mybatis.xml" })
public class ListTest extends BaseController{

	@Autowired
	TbUserMapper mapper;
	
	@Test
	public void testList(){
//		List<TbUserhb> list = hqlOperate.getByHql("from TbUserhb",TbUserhb.class);
////		TbUserExample example = new TbUserExample();
////		List<TbUser> list = mapper.selectByExample(example);
//		
//		System.out.println("=============:"+list.get(0).getEmailAddress());
//		
//		for(int i=0;i<list.size();i++){
//			String address = list.get(0).getEmailAddress();
//			log.info("*********address:**********"+address);
//		}
		
		Integer a =0;
		Integer b =0;
		System.out.println(a==b);
		
	}
	
}
