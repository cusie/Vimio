package top.vimio.utils.markdown.ext.cover;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;

/**
 * @Description: A cover node containing text and other inline nodes nodes as children.
 * @Author: Vimio
 * @Date: 2024/8/28 14:08
 */
public class Cover extends CustomNode implements Delimited {
	private static final String DELIMITER = "%%";

	@Override
	public String getOpeningDelimiter() {
		return DELIMITER;
	}

	@Override
	public String getClosingDelimiter() {
		return DELIMITER;
	}
}
