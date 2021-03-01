package main.java.reasoner;

import org.tweetyproject.arg.dung.semantics.Extension;
import org.tweetyproject.arg.dung.syntax.WeightedDungTheory;

import java.util.*;


/**
 * reasoner for best_2 score
 *
 * find extensions which beat the most extensions
 */
public class SelectBest2Reasoner extends AbstractSelectBestReasoner {
    /**
     * standard constructors
     */
    public SelectBest2Reasoner() {
        super();
    }
    public SelectBest2Reasoner(String aggrType) {
        super(aggrType);
    }

    public Map<Extension, Double> getScores(WeightedDungTheory theory, Collection<Extension> exts) {
        Map<Extension, Double> extsScores = new HashMap<>();
        int maxScore = 0;
        for (Extension ext1 : exts) {
            int beaten = 0;
            for (Extension ext2: exts) {
                if (ext1.equals(ext2)) {
                    continue;
                }
                double score1 = this.compareExtensions(ext1, ext2, theory);
                double score2 = this.compareExtensions(ext2, ext1, theory);
                if (score1 > score2) {
                    beaten++;
                }
            }
            extsScores.put(ext1, (double) beaten);
            if (beaten > maxScore) {
                maxScore = beaten;
            }
        }
        return extsScores;
    }
}
