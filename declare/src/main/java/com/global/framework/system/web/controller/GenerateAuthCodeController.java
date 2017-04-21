package com.global.framework.system.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.global.web.BaseController;


/**
 * 生成图片验证码
 * @author cqchenf@qq.com
 * @date 2011-8-20 下午9:23:42
 * @version v1.0
 */
@Controller
public class GenerateAuthCodeController extends BaseController {

	@RequestMapping("/generateCode")
	public String generateCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		int width=60, height=20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		//生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(new Color(230, 230, 230)/*getRandColor(200,250)*/);
		g.fillRect(0, 0, width, height);

		//设定字体
//		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		g.setFont(new Font("Verdana", Font.BOLD, 18));

		//画边框
		g.setColor(new Color(200,200,200));
		g.drawRect(0,0,width-1,height-1);

		// 随机产生100条干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < 30; i++) {
            if (i % 5 == 0) g.setColor(getRandColor(80, 150));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawLine(x, y, x + random.nextInt(12), y + random.nextInt(12));
        }

		// 取随机产生的认证码(4位数字)
		String sRand="";
		for (int i=0;i<4;i++){
		    String rand=String.valueOf(random.nextInt(10));
		    sRand+=rand;
		    // 将认证码显示到图象中
		    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		    g.drawString(rand,13*i+6,16);
		}

		// 将认证码存入SESSION
		System.out.println("sRand:"+sRand);
		request.getSession().setAttribute("validateCode",sRand);

		// 图象生效
		g.dispose();

		// 输出图象到页面
		try {
			ServletOutputStream out = response.getOutputStream();   
			ImageIO.write(image, "JPEG", out);
			out.flush();
			out.close();
		} catch (IOException e) {
			StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);


            e.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());

		}
		return null;   
	}   

	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc) {    
		Random random = new Random();   
		if (fc > 255)   
			fc = 255;   
		if (bc > 255)   
			bc = 255;   
		int r = fc + random.nextInt(bc - fc);   
		int g = fc + random.nextInt(bc - fc);   
		int b = fc + random.nextInt(bc - fc);   
		return new Color(r, g, b);   
	}   
}
