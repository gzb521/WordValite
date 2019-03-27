package com.comment.wx.common.validate;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.comment.wx.common.kit.GenVerifiCodeUtils;
import com.comment.wx.data.bean.ClickPoint;
import com.comment.wx.data.bean.VerifiCodeData;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class verifiCode extends Controller{
	
	/**
	 * 跳转到界面
	 */
	public void index(){
		render("validate.html");
	}
	/**
	 * 生成带字体的验证码图片
	 */
	public void VerifiCode(){
		render(new ValiteRender());
	}
	
	/**
	 * 检测所选的汉字是否正确
	 */
	@SuppressWarnings("unchecked")
	public void CheckVerifiCode(){
		try {
			//x,y;x1,y1;
			String points = getPara("points");
			System.out.println("校验points:" + points);
			
			
			String[] pointArr = points.split(";");
			List<ClickPoint> pointList = new ArrayList<>();
			for (String pointStr : pointArr) {
				String[] zArr = pointStr.split(",");
				pointList.add(new ClickPoint(Integer.parseInt(zArr[0]), Integer.parseInt(zArr[1])));
			}
			
			List<Rectangle> rectangles = (List<Rectangle>) getSessionAttr("rectangles");
			boolean rs = GenVerifiCodeUtils.checkClickPoint(pointList, rectangles);
			String rsStr = "0";
			if (rs) {
				rsStr = "1";
			}
			renderJson(rsStr);			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 返回要点的汉字有哪些
	 */
	public void GetVerifiWords(){
		try {
			Ret ret = new Ret();
			String[] words = (String[]) getSessionAttr("words");
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < words.length; i++) {
				if (i == 0) {
					sb.append(words[i]);
				} else {
					sb.append("，");
					sb.append(words[i]);
				}
			}
			String verifiStr = sb.toString();
			System.out.println("verifiStr:" + verifiStr);
			ret.setOk().set("result", verifiStr);
			renderJson(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
