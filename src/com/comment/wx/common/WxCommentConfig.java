package com.comment.wx.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.comment.wx.common.model._MappingKit;
import com.comment.wx.routes.CommentRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.mysql.jdbc.Connection;

public class WxCommentConfig extends JFinalConfig {
	
	// 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
	private static Prop p = PropKit.use("comment_health_config_dev.txt")
			.appendIfExists("wx_health_config_pro.txt");

	private WallFilter wallFilter;

	public static void main(String[] args) {
		JFinal.start("WebRoot", 83, "/", 5);
	}

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setJsonFactory(MixedJsonFactory.me());
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new CommentRoutes());
	}

	@Override
	public void configEngine(Engine me) {
		me.setDevMode(true);
		//配置全局路径变量
		me.addSharedMethod(new StrKit());
		me.addSharedObject("webRoot", JFinal.me().getContextPath());
	}

	/**
	 * 抽取成独立的方法，便于 _Generator 中重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = getDruidPlugin();
		wallFilter = new WallFilter(); // 加强数据库安全
		wallFilter.setDbType("mysql");
		druidPlugin.addFilter(wallFilter);
		druidPlugin.addFilter(new StatFilter()); // 添加 StatFilter 才会有统计数据
		me.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
		_MappingKit.mapping(arp);
		me.add(arp);
		arp.setShowSql(p.getBoolean("devMode", false));
		arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
		me.add(new EhCachePlugin());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
//		me.add(new LoginSessionInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
			
	}

	@Override
	public void afterJFinalStart() {
	}

	@Override
	public void beforeJFinalStop() {
	}
	
	

}
