package com.comment.wx.routes;


import com.comment.wx.common.validate.verifiCode;
import com.jfinal.config.Routes;

public class CommentRoutes extends Routes{

	@Override
	public void config() {
		add("/verifiCodeDemo", verifiCode.class,"/web_html");
		
	}
}