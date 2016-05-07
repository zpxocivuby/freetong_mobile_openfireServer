package org.ansj.library;

import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.domain.WoodInterface;
import org.nlpcn.commons.lang.tire.library.Library;

;

/**
 * 动态实体词典
 * 
 * @author taskmgr
 */
public class DynamicEntityLibrary {

	public static final ThreadLocal<Forest> currentForest = new ThreadLocal<Forest>();

	public static final String DEFAULT_NATURE = "dynamicEntity";

	public static final Integer DEFAULT_FREQ = 1000;

	public static final String DEFAULT_FREQ_STR = "1000";

	public static Forest getForest() {
		Forest forest = (Forest) currentForest.get();
		if (forest == null) {
			forest = new Forest();
			currentForest.set(forest);
		}
		return forest;
	}

	/**
	 * 关键词增加
	 * 
	 * @param keyWord
	 *            所要增加的关键词
	 * @param nature
	 *            关键词的词性
	 * @param freq
	 *            关键词的词频
	 */
	public static void insertWord(String keyword, String nature, int freq) {
		String[] paramers = new String[2];
		paramers[0] = nature;
		paramers[1] = String.valueOf(freq);
		Value value = new Value(keyword, paramers);
		Library.insertWord(getForest(), value);
	}

	/**
	 * 删除关键词
	 */
	public static void removeWord(String word) {
		Library.removeWord(getForest(), word);
	}

	public static String[] getParams(String word) {
		WoodInterface temp = getForest();
		for (int i = 0; i < word.length(); i++) {
			temp = temp.get(word.charAt(i));
			if (temp == null) {
				return null;
			}
		}
		if (temp.getStatus() > 1) {
			return temp.getParams();
		} else {
			return null;
		}
	}

	public static boolean contains(String word) {
		return getParams(word) != null;
	}
	
	/**
	 * 将动态词典清空
	 */
	public static void clear() {
		getForest().clear();
	}

}
