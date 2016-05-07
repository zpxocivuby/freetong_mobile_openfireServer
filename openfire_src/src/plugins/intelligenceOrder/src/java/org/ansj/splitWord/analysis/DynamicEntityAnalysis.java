package org.ansj.splitWord.analysis;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.library.DynamicEntityLibrary;
import org.ansj.library.NatureLibrary;
import org.ansj.recognition.DynamicEntityRecognition;
import org.ansj.recognition.NatureRecognition;
import org.ansj.recognition.NumRecognition;
import org.ansj.splitWord.Analysis;
import org.ansj.util.AnsjReader;
import org.ansj.util.Graph;
import org.ansj.util.NameFix;
import org.nlpcn.commons.lang.tire.domain.Forest;

/**
 * 动态实体分词
 * 
 * @author taskmgr
 * 
 */
public class DynamicEntityAnalysis extends Analysis {

	@Override
	protected List<Term> getResult(final Graph graph) {
		// TODO Auto-generated method stub
		Merger merger = new Merger() {
			@Override
			public List<Term> merger() {
				graph.walkPath();

				// 数字发现
				NumRecognition.recognition(graph.terms);

				// 词性标注
				List<Term> result = getResult();
				new NatureRecognition(result).recognition();

				// 动态词典的识别
				new DynamicEntityRecognition(graph.terms, forests)
						.recognition();
				graph.rmLittlePath();
				graph.walkPathByScore();

				// 修复人名左右连接
				NameFix.nameAmbiguity(graph.terms);

				// 优化后重新获得最优路径
				result = getResult();

				setRealName(graph, result);

				// 以动态词典标注为准
				for (Term term : result) {
					String[] params = DynamicEntityLibrary.getParams(term
							.getName());
					if (params != null) {
						term.setNature(NatureLibrary.getNature(params[0]));
					}
				}

				return result;
			}

			private List<Term> getResult() {
				List<Term> result = new ArrayList<Term>();
				int length = graph.terms.length - 1;
				for (int i = 0; i < length; i++) {
					if (graph.terms[i] != null) {
						result.add(graph.terms[i]);
					}
				}
				return result;
			}
		};
		return merger.merger();
	}

	private DynamicEntityAnalysis() {
	}

	/**
	 * 动态实体词典
	 * 
	 * @param forest
	 */
	public DynamicEntityAnalysis(Forest... forests) {
		if (forests == null) {
			forests = new Forest[] { DynamicEntityLibrary.getForest() };
		}
		this.forests = forests;
	}

	public DynamicEntityAnalysis(Reader reader, Forest... forests) {
		this.forests = forests;
		super.resetContent(new AnsjReader(reader));
	}

	public static List<Term> parse(String str) {
		return new DynamicEntityAnalysis().parseStr(str);
	}

	public static List<Term> parse(String str, Forest... forests) {
		return new DynamicEntityAnalysis(forests).parseStr(str);
	}
}
