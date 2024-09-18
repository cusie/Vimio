package top.vimio.config.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Telegram配置
 * @Author: Vimio
 * @Date: 2024/9/4 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "tg.bot")
public class TelegramProperties {
	/**
	 * Telegram bot的api，默认是https://api.telegram.org/bot
	 * 如果使用自己的反代，可以修改它
	 */
	private String api;
	/**
	 * bot的token，可以从 @BotFather 处获取
	 */
	private String token;
	/**
	 * 自己账号和bot的聊天会话id
	 */
	private String chatId;
	/**
	 * 是否使用代理
	 */
	private Boolean useProxy;
	/**
	 * 是否使用反向代理
	 */
	private Boolean useReverseProxy;
	/**
	 * 反向代理URL
	 */
	private String reverseProxyUrl;
}
