package main.java.reasoner;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.WeightedDungTheory;

import java.util.*;


/**
 * reasoner for best_4 score
 *
 * find extensions with maximal minimal attack score
 */
public class SelectBest4Reasoner extends AbstractSelectBestReasoner {
    /**
     * standard constructors
     */
    public SelectBest4Reasoner() {
        super();
    }
    public SelectBest4Reasoner(String aggrType) {
        super(aggrType);
    }

    @Override
    public Map<Extension, Double> getScores(WeightedDungTheory theory, Collection<Extension> exts) {
        Map<Extension, Double> extsMins = new HashMap<>();
        double maxScore = 0;
        for (Extension ext1 : exts) {
            double minScoreExt = Double.MAX_VALUE;
            for (Extension ext2: exts) {
                if (ext1.equals(ext2)) {
                    continue;
                }
                double score1 = this.compareExtensions(ext1, ext2, theory);
                System.out.print(ext1 + "->" + ext2 + ": ");
                System.out.println(score1);
                if (score1 < minScoreExt) {
                    minScoreExt = score1;
                }
            }
            extsMins.put(ext1, minScoreExt);
            if (minScoreExt > maxScore) {
                maxScore = minScoreExt;
            }
        }
        return extsMins;
    }
}
