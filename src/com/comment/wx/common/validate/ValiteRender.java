package com.comment.wx.common.validate;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import com.comment.wx.common.kit.GenVerifiCodeUtils;
import com.comment.wx.data.bean.VerifiCodeData;
import com.jfinal.kit.LogKit;
import com.jfinal.render.Render;

public class ValiteRender extends Render{

	@Override
	public void render() {

		ServletOutputStream sos = null;
		try {
			VerifiCodeData genImgAndData = GenVerifiCodeUtils.genImgAndData();
			BufferedImage bufferedImage = genImgAndData.getImage();
			List<Rectangle> rectangles = genImgAndData.getRectangles();
			String[] words = genImgAndData.getWords();
			request.getSession().removeAttribute("words");
			request.getSession().removeAttribute("rectangles");
			request.getSession().setAttribute("words", words);
			request.getSession().setAttribute("rectangles", rectangles);
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setDateHeader("Expires", 0);
		    response.setContentType("image/jpg");
			sos =response.getOutputStream();
			ImageIO.write(bufferedImage, "jpg", sos);
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (sos != null) {
		        try
		        {
		          sos.close();
		        }
		        catch (IOException e)
		        {
		          LogKit.logNothing(e);
		        }
		      }
		}
	
		
	}

}
